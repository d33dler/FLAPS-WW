package nl.rug.oop.flaps.aircraft_editor.controller;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import nl.rug.oop.flaps.aircraft_editor.controller.actions.BlueprintSelectionListener;
import nl.rug.oop.flaps.aircraft_editor.model.EditorCore;
import nl.rug.oop.flaps.simulation.model.aircraft.Aircraft;
import nl.rug.oop.flaps.simulation.model.aircraft.Compartment;
import nl.rug.oop.flaps.simulation.model.aircraft.FuelTank;
import nl.rug.oop.flaps.simulation.model.map.coordinates.GeographicCoordinates;

@Setter
@Getter
public class MassTracker implements BlueprintSelectionListener {
    private EditorCore editorCore;
    private Aircraft aircraft;
    private Compartment compartment = null;
    private float
            aircraftCapacity = 0,
            totalCargoLoadMass = 0,
            totalFuelLoadMass = 0,
            totalAircraftLoad,
            nomCompartmentLoad = 0;
    private double defaultRange,
            emptyWeight,
            maxTakeOffWgt,
            maxLandingWgt,
            areaCapacity = 0,
            travelDistance,
            aircraftRange;

    public MassTracker(EditorCore editorCore, Aircraft aircraft) {
        this.aircraft = aircraft;
        this.editorCore = editorCore;
        editorCore.getSelectionModel().addListener(this);
        init();
    }

    private void init() {
        this.travelDistance = editorCore.getOriginCoordinates()
                .distanceTo(editorCore.getDestination().getGeographicCoordinates());
        this.defaultRange = aircraft.getType().getRange();
        this.emptyWeight = totalAircraftLoad = (float) aircraft.getType().getEmptyWeight();
        this.maxTakeOffWgt = aircraft.getType().getMaxTakeoffWeight();
        this.maxLandingWgt = aircraft.getType().getMaxLandingWeight();
        aircraft.getType().getCargoAreas()
                .forEach(area -> aircraftCapacity += area.getMaxWeight());
        aircraft.getType().getFuelTanks()
                .forEach(tank -> aircraftCapacity += tank.getCapacity());
        aircraft.getCargoAreaContents().values().forEach(area ->
                area.forEach(freight -> totalCargoLoadMass += freight.getTotalWeight()));
        aircraft.getFuelTankFillStatuses().values().forEach(tank ->
                totalFuelLoadMass += tank);
    }


    public boolean performCheck(float amount) {
        float allCargo = totalAircraftLoad + amount;
        aircraftRange =
                (aircraft.getTotalFuel()
                        / aircraft.getFuelConsumption(editorCore.getSource(), editorCore.getDestination()))
                        * aircraft.getType().getCruiseSpeed();
        if (amount > 0
                && nomCompartmentLoad + amount <= areaCapacity
                && allCargo <= maxTakeOffWgt
                && allCargo <= maxLandingWgt
                && aircraftRange >= travelDistance) {
            totalAircraftLoad = allCargo;
            nomCompartmentLoad += amount;
            System.out.println("NOICE: " + allCargo + "; nomLoad:" + nomCompartmentLoad + "; aircraftLoad: " + totalAircraftLoad);
            return true;
        }
        System.out.println("FECK: " + allCargo + "; nomLoad:" + nomCompartmentLoad + "; aircraftLoad: " + totalAircraftLoad);
        return false;
    }


    @Override
    public void compartmentSelected(Compartment area) {
        BlueprintSelectionListener.super.compartmentSelected(area);
        this.compartment = area;
        updateCompartmentAttr();
    }

    @SneakyThrows
    private void updateCompartmentAttr() {
        areaCapacity = (double) compartment.getClass()
                .getMethod("requestCapacity")
                .invoke(compartment);
        System.out.println("Received capacity: " + areaCapacity);
    }

    public float getAreaMass() {
        return nomCompartmentLoad;
    }

    public void setAreaMass(float nomCompartmentLoad) {
        this.nomCompartmentLoad = nomCompartmentLoad;
    }
}