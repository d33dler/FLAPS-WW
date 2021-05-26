package nl.rug.oop.flaps.aircraft_editor.model;

import lombok.Getter;
import lombok.Setter;
import nl.rug.oop.flaps.simulation.model.aircraft.Aircraft;
import nl.rug.oop.flaps.simulation.model.aircraft.CargoArea;
import nl.rug.oop.flaps.simulation.model.aircraft.FuelTank;
import nl.rug.oop.flaps.simulation.model.aircraft.FuelType;
import nl.rug.oop.flaps.simulation.model.map.coordinates.PointProvider;

import java.awt.geom.Point2D;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

@Getter
@Setter
public class EditorCore {
    private final Aircraft aircraft;
    private final Map<String, CargoArea> cargoAreaMap;
    private final Map<String, FuelTank> fuelTankMap;
    private final BlueprintSelectionModel selectionModel;
    private static final double NEARBY_UNIT_RANGE = 250.0;


    public EditorCore(Aircraft aircraft,
                      List<CargoArea> cargoAreaList,
                      List<FuelTank> fuelTankList,
                      BlueprintSelectionModel selectionModel) {
        this.aircraft = aircraft;
        this.cargoAreaMap = listToMap(cargoAreaList, CargoArea::getName);
        this.fuelTankMap = listToMap(fuelTankList, FuelTank::getName);
        this.selectionModel = selectionModel;
    }

 /*   public Optional<Object> getClosestUnit(PointProvider unitPos, double tolerance, Class<?> clazz) {
        if (clazz.equals(CargoArea.class)) {
            return
        } else {
            return this.fuelTankMap.values().stream().filter(unit -> unit.)
        }
    }

*/
    private <P, O> Map<P, O> listToMap(List<O> list, Function<O, P> opFunction) {
        Map<P, O> map = new ConcurrentHashMap<>(list.size());
        list.forEach(ob -> map.put(opFunction.apply(ob), ob));
        return map;
    }
}

