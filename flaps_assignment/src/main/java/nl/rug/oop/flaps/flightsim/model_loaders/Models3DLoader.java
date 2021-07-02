package nl.rug.oop.flaps.flightsim.model_loaders;

import gov.nasa.worldwind.WorldWindow;
import gov.nasa.worldwind.globes.Globe;
import gov.nasa.worldwind.osm.map.worldwind.gl.obj.ObjRenderable;
import lombok.Getter;
import nl.rug.oop.flaps.simulation.model.map.coordinates.GeographicCoordinates;

@Getter
public class Models3DLoader extends ModelLoader {
    private AircraftLoader aircraftLoader;
    private StructuresLoader structuresLoader;
    private ObjRenderable aircraft3d_obj, hangar3d_obj;

    public Models3DLoader(WorldWindow worldWindow, Globe earth, GeographicCoordinates coords) {
        super(worldWindow, earth, coords);
        this.aircraftLoader = new AircraftLoader(worldWindow, earth, coords);
        this.structuresLoader = new StructuresLoader(worldWindow, earth, coords);
        init();
    }

    private void init() {
        aircraft3d_obj = aircraftLoader.loadObjects();
        hangar3d_obj = structuresLoader.loadObjects();
    }
}
