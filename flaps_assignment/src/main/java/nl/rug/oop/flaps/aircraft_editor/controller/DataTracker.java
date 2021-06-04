package nl.rug.oop.flaps.aircraft_editor.controller;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import nl.rug.oop.flaps.aircraft_editor.controller.actions.BlueprintSelectionListener;
import nl.rug.oop.flaps.aircraft_editor.model.EditorCore;
import nl.rug.oop.flaps.simulation.model.aircraft.Aircraft;
import nl.rug.oop.flaps.simulation.model.aircraft.Compartment;

@Setter
@Getter
public class DataTracker implements BlueprintSelectionListener {
    private EditorCore editorCore;
    private Aircraft aircraft;
    private Compartment compartment = null;
    private static final String listener_ID = EditorCore.generalListenerID;

    private float
            aircraftCapacity = 0,
            totalCargoLoadMass = 0,
            totalFuelLoadMass = 0,
            totalAircraftLoad,
            nomCompartmentLoad = 0;
    private double defaultRange,
            emptyWeight,
            maxTakeOffWeight,
            maxLandingWeight,
            selectedAreaCapacity = 0,
            travelDistance,
            aircraftRange;
    private static final int M_KM = 1000;

    public DataTracker(EditorCore editorCore, Aircraft aircraft) {
        this.aircraft = aircraft;
        this.editorCore = editorCore;
        editorCore.getSelectionModel().addListener(listener_ID, this);
        init();
    }

    private void init() {
        this.travelDistance = editorCore.getOriginCoordinates()
                .distanceTo(editorCore.getDestination().getGeographicCoordinates());
        this.defaultRange = aircraft.getType().getRange();
        this.emptyWeight = totalAircraftLoad = (float) aircraft.getType().getEmptyWeight();
        this.maxTakeOffWeight = aircraft.getType().getMaxTakeoffWeight();
        this.maxLandingWeight = aircraft.getType().getMaxLandingWeight();
        aircraft.getType().getCargoAreas()
                .forEach(area -> aircraftCapacity += area.getMaxWeight());
        aircraft.getType().getFuelTanks()
                .forEach(tank -> aircraftCapacity += tank.getCapacity());

    }

    public boolean performCargoCheck(float amount) {
        float allCargo = totalAircraftLoad + amount;

        if (amount > 0
                && nomCompartmentLoad + amount <= selectedAreaCapacity
                && allCargo <= maxTakeOffWeight
                && allCargo <= maxLandingWeight) {
            totalAircraftLoad = allCargo;
            nomCompartmentLoad += amount;
            System.out.println("NOICE: " + allCargo + "; nomLoad:" + nomCompartmentLoad + "; aircraftLoad: " + totalAircraftLoad
                    + " range: " + aircraftRange + " ; travelDist: " + travelDistance);
            return true;
        }
        System.out.println("FECK: " + allCargo + "; nomLoad:" + nomCompartmentLoad + "; aircraftLoad: " + totalAircraftLoad
                + " range: " + aircraftRange + " ; travelDist: " + travelDistance);
        return false;
    }

    public boolean performFuelCheck(double oldLevel, double newLevel) {
        float updatedLoad = (float) (totalAircraftLoad - oldLevel + newLevel);
        double newTotalFuel = aircraft.getTotalFuel() - oldLevel + newLevel;
        if (updatedLoad <= aircraftCapacity) {
            totalAircraftLoad = updatedLoad;
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
            System.out.println("Travel distance is within aircraft range; Fuel Consum: " + aircraft.getFuelConsumption(travelDistance / M_KM)
                    + "; Totalfuel: " + level + "; Range: " + range);
        } else
            System.out.println("Travel distance is greater than aircraft range; FuelConsum: " + aircraft.getFuelConsumption(travelDistance / M_KM)
                    + "; Totalfuel: " + level + "; Range: " + range);

        return range;
    }

    @Override
    public void compartmentSelected(Compartment area) {
        BlueprintSelectionListener.super.compartmentSelected(area);
        this.compartment = area;
        updateCompartmentAttr();
    }

    @SneakyThrows
    private void updateCompartmentAttr() {
        selectedAreaCapacity = (double) compartment.getClass()
                .getMethod("requestCapacity")
                .invoke(compartment);
        System.out.println("Received capacity: " + selectedAreaCapacity);
        updateTotalLoadMass();
    }
    private void updateTotalLoadMass() {
        aircraft.getCargoAreaContents().values().forEach(area ->
                area.forEach(freight -> totalCargoLoadMass += freight.getTotalWeight()));
        aircraft.getFuelTankFillStatuses().values().forEach(tank ->
                totalFuelLoadMass += tank);
        //aircraft.getCargoAreaContents(compartment)
    }

    public float getAreaMass() {
        return nomCompartmentLoad;
    }

    public void setAreaMass(float nomCompartmentLoad) {
        this.nomCompartmentLoad = nomCompartmentLoad;
    }
}