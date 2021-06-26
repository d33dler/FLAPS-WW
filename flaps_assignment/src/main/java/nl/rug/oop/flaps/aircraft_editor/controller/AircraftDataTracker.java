package nl.rug.oop.flaps.aircraft_editor.controller;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import nl.rug.oop.flaps.aircraft_editor.model.EditorCore;
import nl.rug.oop.flaps.aircraft_editor.model.Remapper;
import nl.rug.oop.flaps.aircraft_editor.model.listener_models.BlueprintSelectionModel;
import nl.rug.oop.flaps.aircraft_editor.view.maineditor.b_print.BlueprintDisplay;
import nl.rug.oop.flaps.aircraft_editor.view.maineditor.main_panels.DepartPanel;
import nl.rug.oop.flaps.aircraft_editor.view.maineditor.main_panels.InfoPanel;
import nl.rug.oop.flaps.simulation.model.aircraft.Aircraft;
import nl.rug.oop.flaps.simulation.model.aircraft.areas.Cabin;
import nl.rug.oop.flaps.simulation.model.aircraft.areas.CargoArea;
import nl.rug.oop.flaps.simulation.model.aircraft.areas.Compartment;
import nl.rug.oop.flaps.simulation.model.aircraft.areas.FuelTank;

import java.awt.geom.Point2D;

/**
 * AircraftDataTracker class - stores and updates all data related to the aircraft's real-time state.
 * All the data is displayed in the InfoPanel (sub component titled - AIRCRAFT DATA);
 */
@Setter
@Getter
public class AircraftDataTracker {
    private static final String listener_ID = EditorCore.generalListenerID;
    private EditorCore editorCore;
    private Aircraft aircraft;
    private Compartment compartment = null;
    private BlueprintSelectionModel selectionModel;
    private CommercialDataTracker commercialData;
    private BlueprintDisplay display;
    private DepartPanel departPanel;
    private Remapper remapper;
    private InfoPanel infoPanel;

    private double
            aircraftCapacity = 0,
            totalCargoLoadMass = 0,
            totalFuelLoadMass = 0,
            totalAircraftLoadWeight = 0,
            totalAircraftWeight = 0,
            selectedAreaLoad = 0,
            selectedAreaCapacity = 0,
            emptyWeight,
            maxTakeOffWeight,
            maxLandingWeight,
            travelDistance,
            defaultRange,
            aircraftRange,
            aircraftLength,
            cgTolerance,
            defaultCenterOfGravity_X,
            defaultCenterOfGravity_Y,
            centerOfGravity_X = 0.0,
            centerOfGravity_Y = 0.0;
    private static final int M_KM = 1000;

    public AircraftDataTracker(EditorCore editorCore, Aircraft aircraft) {
        this.aircraft = aircraft;
        this.editorCore = editorCore;
        this.selectionModel = editorCore.getBpSelectionModel();
        this.commercialData = new CommercialDataTracker(editorCore, this);
        this.remapper = editorCore.getRemapper();
        init();
    }

    /**
     * Set initial values according to the aircraft type parameters;
     */
    private void init() {
        this.travelDistance = editorCore.getOriginCoordinates()
                .distanceTo(editorCore.getDestination().getGeographicCoordinates());
        this.defaultRange = aircraft.getType().getRange();
        this.aircraftRange = computeAircraftRange(totalFuelLoadMass);
        this.emptyWeight =  aircraft.getType().getEmptyWeight();
        this.maxTakeOffWeight = aircraft.getType().getMaxTakeoffWeight();
        this.maxLandingWeight = aircraft.getType().getMaxLandingWeight();
        this.cgTolerance = aircraft.getType().getCgTolerance();
        this.aircraftLength = aircraft.getType().getLength();
        aircraft.getType().getCargoAreas()
                .forEach(area -> aircraftCapacity += area.getMaxWeight());
        aircraft.getType().getFuelTanks()
                .forEach(tank -> aircraftCapacity += tank.getCapacity());
        commercialData.refreshData();
        initCg();
    }

    /**
     * compute default center of gravity
     */
    private void initCg() {
        remapCg(new Point2D.Double(aircraft.getType().getEmptyCgX(), aircraft.getType().getEmptyCgY()));
        updateTotalLoadMass();
        if (aircraft.getCenterOfG() == null) {
            updateCenterOfGravity();
        } else {
            centerOfGravity_X = aircraft.getCenterOfG().x;
            centerOfGravity_Y = aircraft.getCenterOfG().y;
        }
    }

    /**
     *
     * @param amount weight of the cargo added to the cargo area by the user
     * @return true if the weight does not surpass any of the limiting parameters
     * (takeOff,Landing weight limits, area capacity..)
     */
    public boolean performWeightCheck(Compartment area, double amount) {
        double allCargo = totalAircraftLoadWeight + amount;
        double load = area.getLoadWeight();
        double capacity = area.requestCapacity();
        if (amount > 0
                && load + amount <= capacity
                && allCargo <= maxTakeOffWeight
                && allCargo <= maxLandingWeight) {
            totalAircraftLoadWeight = allCargo;
            totalAircraftWeight += amount;
            area.setLoadWeight(area.getLoadWeight()+amount);
            return true;
        }
        return false;
    }
    public boolean performPassengerCheck(Cabin area, double weight) {
        double count = commercialData.getPassengerCount();
        return count < area.getSeats() && performWeightCheck(area, weight);
    }

    /**
     *
     * @param oldLevel - fuel in kg in the tank area
     * @param newLevel - fuel in kg in the tank area
     * @return true if the new level does not surpass any of the limiting parameters
     * (aircraft total capacity)
     */
    public boolean performFuelCheck(double oldLevel, double newLevel) {
        double updatedLoad = totalAircraftLoadWeight - oldLevel + newLevel;
        double newTotalFuel = aircraft.getTotalFuel() - oldLevel + newLevel;
        if (updatedLoad <= aircraftCapacity) {
            totalAircraftLoadWeight = updatedLoad;
            aircraftRange = computeAircraftRange(newTotalFuel);
            return true;
        }
        return false;
    }

    /**
     * Sets the depart button enabled/disabled state depending on the fulfilled requirements
     */
    public void performDepartureValidationCheck() {
        departPanel.getDepartButton().setEnabled(aircraftRange >= travelDistance
                && validateDecalage()
                && commercialData.getDestination().canAcceptIncomingAircraft());
    }

    /**
     *
     * @return true if the computed decalage does not surpass the threshold tolerance of aircraft type;
     * We use the Pythagoras's theorem to compute the hypotenuse based on cathetuses(sides) obtained by calculating
     * the absolute value from subtraction from the default XY coordinate values of current Cg XY values and divide the results
     * to the custom pixels per meter (specific to the planes size).
     */
    private boolean validateDecalage() {
        double ppm = Remapper.Pixels_per_M;
        double cgDistance = cgTolerance * aircraftLength;
        double cathetus_1 = Math.abs(defaultCenterOfGravity_X - centerOfGravity_X) / ppm;
        double cathetus_2 = Math.abs(defaultCenterOfGravity_Y - centerOfGravity_Y) / ppm;
        double hypotenuse = Math.sqrt(Math.pow(cathetus_1, 2) + Math.pow(cathetus_2, 2));
        return hypotenuse <= cgDistance;
    }

    /**
     *
     * @param totalFuel in all the aircraft's tanks
     * @return range based on the formula of fuel consumption and cruise speed
     */
    private double computeAircraftRange(double totalFuel) {
        return ((totalFuel / aircraft.getType().getFuelConsumption())
                * aircraft.getType().getCruiseSpeed()) * M_KM;
    }

    /**
     *
     * @param area selected area Updates all native data plus the selected area specific data;
     */
    public void refreshData(Compartment area) {
        this.compartment = area;
        updateAllInfo();
    }

    /**
     * Method overloading
     */
    public void refreshData() {
        updateAllInfo();
    }

    @SneakyThrows
    private void updateAllInfo() {
        updateAreaCapacity();
        updateAreaLoad();
        updateTotalLoadMass();
        updateCenterOfGravity();
        commercialData.refreshData();
        infoPanel.updateAllData();
    }

    private void updateAreaLoad() {
        compartment.getUpdateAreaLoad(this);
    }

    /**
     * Get custom capacity. Abstract Compartment class allows for easier data extraction by abstraction;
     */
    private void updateAreaCapacity() {
        this.selectedAreaCapacity = compartment.requestCapacity();
    }

    /**
     * Updates the total load mass of the aircraft
     * & separate loading material types total mass (cargo + fuel, cargo, fuel)
     * & w/ or without emptyWeight included;
     * In parallel, calculates the center of gravity;
     */
    private void updateTotalLoadMass() {
        centerOfGravity_X = centerOfGravity_Y = 0.0;
        totalCargoLoadMass = totalFuelLoadMass = 0;
        aircraft.getCargoAreaContents().forEach((area, freightSet) ->
                freightSet.forEach(freight -> {
                    totalCargoLoadMass += freight.getTotalWeight();
                    computeCenterOfGravity(area, freight.getTotalWeight());
                }));
        aircraft.getFuelTankFillStatuses().forEach((tank, amount) ->
        {
            totalFuelLoadMass += amount;
            computeCenterOfGravity(tank, amount);
        });
        this.totalAircraftLoadWeight = totalFuelLoadMass + totalCargoLoadMass;
        this.totalAircraftWeight =  emptyWeight + totalFuelLoadMass + totalCargoLoadMass;
    }

    /**
     *
     * @param area - currently examined area
     * @param weight - total weight of this particular area
     */
    private void computeCenterOfGravity(Compartment area, double weight) {
        centerOfGravity_X += weight * remapper.getLocalCoords().get(area.hashCode()).x;
        centerOfGravity_Y += weight * remapper.getLocalCoords().get(area.hashCode()).y;
    }

    /**
     * Last step in the computation of cG, addition of the empty weight contribution to the cG ;
     */
    private void updateCenterOfGravity() {
        centerOfGravity_X += emptyWeight * defaultCenterOfGravity_X;
        centerOfGravity_Y += emptyWeight * defaultCenterOfGravity_Y;
        centerOfGravity_X = (centerOfGravity_X) / totalAircraftWeight;
        centerOfGravity_Y = (centerOfGravity_Y) / totalAircraftWeight;
        aircraft.setCenterOfG(new Point2D.Double(centerOfGravity_X, centerOfGravity_Y));
    }

    /**
     *
     * @param cgPos default position of the center of gravity according to the airplane type data;
     *              Methods remapCg, remaps it similarly to the way indicators are remapped;
     *              (only necessary for the initial (default) cG)
     */
    private void remapCg(Point2D.Double cgPos) {
        Remapper remapper = editorCore.getRemapper();
        Point2D.Double remappedCgPos = remapper.remap(cgPos);
        defaultCenterOfGravity_X = remappedCgPos.x;
        defaultCenterOfGravity_Y = remappedCgPos.y;
    }

    /**
     * Methods below update the area load for the selected fuel tank or cargo area;
     */
    public void updateFuelAreaMass() {
        this.selectedAreaLoad = aircraft.getFuelAmountForFuelTank((FuelTank) compartment);
    }

    public void updateCargoAreaMass() {
        selectedAreaLoad = 0;
        aircraft.getCargoAreaContents((CargoArea) compartment).forEach(cargoFreight ->
                selectedAreaLoad += cargoFreight.getTotalWeight());
    }
}