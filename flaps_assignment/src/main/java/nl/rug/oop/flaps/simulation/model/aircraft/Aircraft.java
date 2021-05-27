package nl.rug.oop.flaps.simulation.model.aircraft;

import lombok.Getter;
import lombok.Setter;
import nl.rug.oop.flaps.simulation.model.airport.Airport;
import nl.rug.oop.flaps.simulation.model.cargo.CargoUnit;
import nl.rug.oop.flaps.simulation.model.world.World;

import java.util.*;

/**
 * Represents a single aircraft of a particular type.
 *
 * @author T.O.W.E.R.
 */
@Getter
public class Aircraft implements Comparable<Aircraft> {
    /**
     * A unique identifier for this aircraft, like a call-sign.
     */
    private final String identifier;

    /**
     * The type of this aircraft.
     */
    private final AircraftType type;

    /**
     * The world that this aircraft exists in.
     */
    private final World world;

    /**
     * Boolean switch to verify if an update (upscale) of aircraft areas XY coords is required;
     */
    @Setter
    private boolean updatedUnitXY = false;

    /**
     * A map that contains information for each fuel tank of how much fuel is in there
     * The key is the name of the fuel tank and the Double is the amount of fuel in kg
     */
    private final Map<FuelTank, Double> fuelTankFillStatuses;

    /**
     * A map that contains information for each cargo area what the cargo contents are
     * The key is the name of the cargo area
     */
    private final Map<CargoArea, Set<CargoUnit>> cargoAreaContents;


    public Aircraft(String identifier, AircraftType type, World world) {
        this.identifier = identifier;
        this.type = type;
        this.world = world;
        fuelTankFillStatuses = new HashMap<>();
        cargoAreaContents = new HashMap<>();
    }

    /**
     * Retrieves the amount of fuel that is consumed when flying between two airports in kg
     *
     * @param origin The airport the aircraft departs from
     * @param destination The airport the aircraft flies to
     * @return The amount of fuel in kg consumed in the journey
     */
    public double getFuelConsumption(Airport origin, Airport destination) {
        throw new UnsupportedOperationException("Getting the fuel consumption has not yet been implemented");
    }

    /**
     * Progressively removes fuel from tanks until consumedFuel kg has been removed
     *
     * @param consumedFuel The amount of fuel that should be removed in total
     */
    public void removeFuel(double consumedFuel) {
        for (FuelTank fuelTank : fuelTankFillStatuses.keySet()) {
            double fuelInTank = getFuelAmountForFuelTank(fuelTank);
            if (consumedFuel > fuelInTank) {
                setFuelAmountForFuelTank(fuelTank, 0);
                consumedFuel -= fuelInTank;
            } else {
                setFuelAmountForFuelTank(fuelTank, fuelInTank - consumedFuel);
                return;
            }
        }
    }

    /**
     * Retrieves the total amount of fuel (from all fuel tanks) in the aircraft in kg
     *
     * @return The total amount of fuel in the aircraft in kg
     */
    public double getTotalFuel() {
        return fuelTankFillStatuses.keySet().stream().mapToDouble(fuelTankFillStatuses::get).sum();
    }

    /**
     * Retrieves the contents of a cargo area
     *
     * @param cargoArea The cargo area the contents should be retrieved from
     *
     * @return A list of cargo units representing the contents of the cargo area
     */
    public Set<CargoUnit> getCargoAreaContents(CargoArea cargoArea) {
        return cargoAreaContents.getOrDefault(cargoArea, new HashSet<>());
    }

    /**
     * Sets the amount of fuel in kg for a specific fuel tank
     *
     * @param fuelTank The fuel tank
     * @param fuelAmount   The amount of fuel in kg this aircraft
     */
    public void setFuelAmountForFuelTank(FuelTank fuelTank, double fuelAmount) {
        fuelTankFillStatuses.put(fuelTank, fuelAmount);
    }

    /**
     * Retrieves the amount of fuel in kg for a specific fuel tank
     *
     * @param fuelTank The fuel tank you want the contents of
     *
     * @return The amount of fuel in kg in the fuel tank with the provided name
     */
    public double getFuelAmountForFuelTank(FuelTank fuelTank) {
        return fuelTankFillStatuses.getOrDefault(fuelTank, 0.0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Aircraft aircraft = (Aircraft) o;
        return getIdentifier().equals(aircraft.getIdentifier()) && getType().equals(aircraft.getType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdentifier(), getType());
    }

    @Override
    public int compareTo(Aircraft o) {
        int typeComparison = this.getType().compareTo(o.getType());
        if (typeComparison != 0) return typeComparison;
        return this.getIdentifier().compareTo(o.getIdentifier());
    }
}
