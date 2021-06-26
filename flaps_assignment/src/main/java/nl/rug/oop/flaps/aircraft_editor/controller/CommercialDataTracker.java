package nl.rug.oop.flaps.aircraft_editor.controller;

import lombok.Getter;
import lombok.Setter;
import nl.rug.oop.flaps.aircraft_editor.model.EditorCore;
import nl.rug.oop.flaps.simulation.model.aircraft.Aircraft;
import nl.rug.oop.flaps.simulation.model.airport.Airport;
import nl.rug.oop.flaps.simulation.model.cargo.CargoType;

/**
 * CommercialDataTracker class - contains and updates all the data concerned with business aspects of the simulator;
 */
@Getter
@Setter
public class CommercialDataTracker {
    private EditorCore editorCore;
    private Aircraft aircraft;
    private AircraftDataTracker dataTracker;
    private Airport origin, destination;

    private double
            passengerCount = 0,
            estimatedFuelCosts = 0,
            cargoRevenue = 0,
            totalEstimatedProfit = 0,
            airportFuelPrice = 0;

    public CommercialDataTracker(EditorCore core, AircraftDataTracker tracker) {
        this.editorCore = core;
        this.aircraft = core.getAircraft();
        this.dataTracker = tracker;
        this.origin = editorCore.getSource();
        this.destination = editorCore.getDestination();
        init();
    }

    private void init() {
        this.airportFuelPrice = origin.getFuelPriceByType(aircraft.getType().getFuelType());
    }

    /**
     * Update all data commercial data;
     */
    protected void refreshData() {
        computeFuelCosts();
        computeCargoRevenue();
        computeTotalProfit();
    }

    /**
     * All methods below compute the mentioned values according to the formulas stipulated in the reader;
     */
    private void computeFuelCosts() {
        estimatedFuelCosts = airportFuelPrice * dataTracker.getTotalFuelLoadMass();
    }

    private void computeCargoRevenue() {
        cargoRevenue = 0;
        aircraft.getCargoAreaContents().values().forEach(area -> {
            area.forEach(freight -> {
                CargoType type = freight.getCargoType();
                cargoRevenue +=
                        destination.getCargoPriceByType(type)
                                * freight.getUnitCount();
            });
        });
    }

    private void computeTotalProfit() {
        totalEstimatedProfit = cargoRevenue - estimatedFuelCosts;
    }
}
