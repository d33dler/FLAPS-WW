package nl.rug.oop.flaps.aircraft_editor.controller;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import nl.rug.oop.flaps.aircraft_editor.model.BlueprintSelectionModel;
import nl.rug.oop.flaps.aircraft_editor.model.EditorCore;
import nl.rug.oop.flaps.aircraft_editor.view.BlueprintDisplay;
import nl.rug.oop.flaps.simulation.model.aircraft.Aircraft;
import nl.rug.oop.flaps.simulation.model.aircraft.CargoArea;
import nl.rug.oop.flaps.simulation.model.aircraft.Compartment;
import nl.rug.oop.flaps.simulation.model.aircraft.FuelTank;

import java.awt.geom.Point2D;

@Setter
@Getter
public class AircraftDataTracker {
    private EditorCore editorCore;
    private Aircraft aircraft;
    private Compartment compartment = null;
    private BlueprintSelectionModel selectionModel;
    private CommercialDataTracker commercialData;
    private static final String listener_ID = EditorCore.generalListenerID;
    private BlueprintDisplay display;

    private float
            aircraftCapacity = 0,
            totalCargoLoadMass = 0,
            totalFuelLoadMass = 0,
            totalAircraftWeight = 0,
            selectedAreaLoad = 0,
            selectedAreaCapacity = 0;
    private double emptyWeight,
            maxTakeOffWeight,
            maxLandingWeight,
            travelDistance,
            defaultRange,
            aircraftRange,
            defaultCenterOfGravity_X,
            defaultCenterOfGravity_Y,
            centerOfGravity_X = 0.0,
            centerOfGravity_Y = 0.0;
    private static final int M_KM = 1000;

    public AircraftDataTracker(EditorCore editorCore, Aircraft aircraft) {
        this.aircraft = aircraft;
        this.editorCore = editorCore;
        this.selectionModel = editorCore.getSelectionModel();
        this.commercialData = new CommercialDataTracker(editorCore, this);
        init();
    }

    private void init() {
        this.travelDistance = editorCore.getOriginCoordinates()
                .distanceTo(editorCore.getDestination().getGeographicCoordinates());
        this.defaultRange = aircraft.getType().getRange();
        this.emptyWeight = totalAircraftWeight = (float) aircraft.getType().getEmptyWeight();
        this.maxTakeOffWeight = aircraft.getType().getMaxTakeoffWeight();
        this.maxLandingWeight = aircraft.getType().getMaxLandingWeight();
        aircraft.getType().getCargoAreas()
                .forEach(area -> aircraftCapacity += area.getMaxWeight());
        aircraft.getType().getFuelTanks()
                .forEach(tank -> aircraftCapacity += tank.getCapacity());
        remapCg(new Point2D.Double(aircraft.getType().getEmptyCgX(), aircraft.getType().getEmptyCgY()));
    }


    public boolean performCargoCheck(float amount) {
        float allCargo = totalAircraftWeight + amount;
        if (amount > 0
                && selectedAreaLoad + amount <= selectedAreaCapacity
                && allCargo <= maxTakeOffWeight
                && allCargo <= maxLandingWeight) {
            totalAircraftWeight = allCargo;
            selectedAreaLoad += amount;
            return true;
        }
        return false;
    }

    public boolean performFuelCheck(double oldLevel, double newLevel) {
        float updatedLoad = (float) (totalAircraftWeight - oldLevel + newLevel);
        double newTotalFuel = aircraft.getTotalFuel() - oldLevel + newLevel;
        if (updatedLoad <= aircraftCapacity) {
            totalAircraftWeight = updatedLoad;
            aircraftRange = rangeCheck(newTotalFuel);
            return true;
        }
        rangeCheck(newTotalFuel);
        return false;
    }

    public double rangeCheck(double level) {
        double range =
                ((level / aircraft.getType().getFuelConsumption())
                        * aircraft.getType().getCruiseSpeed()) * M_KM; //TODO check correctness
        if (range >= travelDistance) {
            //TODO
        }

        return range;
    }


    public void refreshData(Compartment area) {
        this.compartment = area;
        updateAllInfo();
    }

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
    }

    private void updateAreaLoad() {
        compartment.getAreaLoad(this);
    }

    private void updateAreaCapacity() {
        this.selectedAreaCapacity = compartment.requestCapacity();
    }

    private void updateTotalLoadMass() {
        centerOfGravity_X = 0.0;
        centerOfGravity_Y = 0.0;
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
    }

    private void computeCenterOfGravity(Compartment area, double weight) {
        centerOfGravity_X += weight * editorCore.getLocalCoords().get(area.hashCode()).x;
        centerOfGravity_Y += weight * editorCore.getLocalCoords().get(area.hashCode()).y;
    }

    private void updateCenterOfGravity() {
        centerOfGravity_X += emptyWeight * defaultCenterOfGravity_X;
        centerOfGravity_Y += emptyWeight * defaultCenterOfGravity_Y;
        centerOfGravity_X = (centerOfGravity_X) / totalAircraftWeight;
        centerOfGravity_Y = (centerOfGravity_Y) / totalAircraftWeight;
        aircraft.setCenterOfG(new Point2D.Double(centerOfGravity_X, centerOfGravity_Y));
    }

    private void remapCg(Point2D.Double cgPos) {
        Point2D.Double remappedCgPos = editorCore.remap(cgPos);
        aircraft.setCenterOfG(remappedCgPos);
        defaultCenterOfGravity_X = centerOfGravity_X = remappedCgPos.x;
        defaultCenterOfGravity_Y = centerOfGravity_Y = remappedCgPos.y;
    }

    public void updateFuelAreaMass() {
        this.selectedAreaLoad = Float.parseFloat
                (String.valueOf(aircraft.getFuelAmountForFuelTank((FuelTank) compartment)));
    }

    public void updateCargoAreaMass() {
        selectedAreaLoad = 0;
        aircraft.getCargoAreaContents((CargoArea) compartment).forEach(cargoFreight ->
                selectedAreaLoad += cargoFreight.getTotalWeight());
    }
}