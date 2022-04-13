package nl.rug.oop.flaps.flightsim.model_loaders;

import gov.nasa.worldwind.WorldWindow;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.globes.Globe;
import gov.nasa.worldwind.layers.RenderableLayer;
import gov.nasa.worldwind.osm.map.worldwind.gl.obj.ObjRenderable;
import gov.nasa.worldwindx.examples.ApplicationTemplate;
import lombok.Getter;
import lombok.Setter;
import nl.rug.oop.flaps.flightsim.sim_model.sim_utils.SimulationUtils;
import nl.rug.oop.flaps.simulation.model.map.coordinates.GeographicCoordinates;

import java.io.File;

@Getter
@Setter
public class AircraftLoader extends ModelLoader implements Loader {
    private ObjRenderable aircraftObj_3d;

    public AircraftLoader(WorldWindow worldWindow, Globe earth, GeographicCoordinates og) {
        super(worldWindow, earth, og);
    }

    public ObjRenderable loadObjects() {
        RenderableLayer layer = new RenderableLayer();
        layer.setName("Aircraft");
        File file = new File("plane_3d_models/boeing_787/Boeing_787.obj");
        double elevation = SimulationUtils.getElevation(earth, coords.getLatitude(), coords.getLongitude());
        System.out.println("Plane : " + elevation);
        this.aircraftObj_3d = new ObjRenderable(Position.fromDegrees(coords.getLatitude(), coords.getLongitude(), elevation),
                file.getAbsolutePath());
        aircraftObj_3d.setSize(10);
        layer.addRenderable(aircraftObj_3d);
        ApplicationTemplate.insertBeforePlacenames(worldWindow, layer);
        aircraftObj_3d.setVisible(false);
        return this.aircraftObj_3d;
    }
}
