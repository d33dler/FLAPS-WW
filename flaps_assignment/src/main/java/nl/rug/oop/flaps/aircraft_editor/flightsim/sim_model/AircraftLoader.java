package nl.rug.oop.flaps.aircraft_editor.flightsim.sim_model;

import gov.nasa.worldwind.WorldWindow;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.globes.Globe;
import gov.nasa.worldwind.layers.RenderableLayer;
import gov.nasa.worldwind.osm.map.worldwind.gl.obj.ObjRenderable;
import gov.nasa.worldwindx.examples.ApplicationTemplate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nl.rug.oop.flaps.aircraft_editor.flightsim.sim_model.sim_utils.SimulationUtils;
import nl.rug.oop.flaps.simulation.model.map.coordinates.GeographicCoordinates;

import java.io.File;

@NoArgsConstructor
public class AircraftLoader {
    @Getter
    private ObjRenderable aircraftObj_3d;
    private WorldWindow worldWindow;

    public AircraftLoader(WorldWindow worldWindow) {
        this.worldWindow = worldWindow;
    }

    public ObjRenderable loadAircraft(WorldWindow worldWindow, Globe earth, GeographicCoordinates og) {
        RenderableLayer layer = new RenderableLayer();
        layer.setName("Aircraft");
        File file = new File("plane_3d_models/boeing_787/Boeing_787.obj");
        double elevation = SimulationUtils.getElevation(earth, og.getLatitude(), og.getLongitude());
        this.aircraftObj_3d = new ObjRenderable(Position.fromDegrees(og.getLatitude(), og.getLongitude(), elevation),
                file.getAbsolutePath());
        aircraftObj_3d.setSize(10);
        layer.addRenderable(aircraftObj_3d);
        ApplicationTemplate.insertBeforePlacenames(worldWindow, layer);
        return this.aircraftObj_3d;
    }
}
