package nl.rug.oop.flaps.flightsim.model_loaders;

import gov.nasa.worldwind.WorldWindow;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.globes.Globe;
import gov.nasa.worldwind.layers.RenderableLayer;
import gov.nasa.worldwind.osm.map.worldwind.gl.obj.ObjRenderable;
import gov.nasa.worldwindx.examples.ApplicationTemplate;
import lombok.Getter;
import nl.rug.oop.flaps.flightsim.sim_model.sim_utils.SimulationUtils;
import nl.rug.oop.flaps.simulation.model.map.coordinates.GeographicCoordinates;

import java.io.File;

@Getter
public class StructuresLoader extends ModelLoader implements Loader {

    private ObjRenderable hangar_3d_obj;

    public StructuresLoader(WorldWindow worldWindow, Globe earth, GeographicCoordinates coords) {
        super(worldWindow, earth, coords);
    }

    @Override
    public ObjRenderable loadObjects() {
        RenderableLayer layer = new RenderableLayer();
        layer.setName("Hangar");
        File file = new File("structure_3d_models/hangar/Shelter_simple.obj");
        double elevation = SimulationUtils.getElevation(earth, coords.getLatitude(), coords.getLongitude());
        System.out.println("HAngar : " + elevation);
        this.hangar_3d_obj = new ObjRenderable(Position.fromDegrees(coords.getLatitude(), coords.getLongitude(), elevation),
                file.getAbsolutePath());
        hangar_3d_obj.setSize(3);
        layer.addRenderable(hangar_3d_obj);
        ApplicationTemplate.insertBeforePlacenames(worldWindow, layer);
        hangar_3d_obj.setVisible(false);
        return hangar_3d_obj;
    }
}
