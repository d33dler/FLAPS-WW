package nl.rug.oop.flaps.flightsim.model_loaders;

import gov.nasa.worldwind.WorldWindow;
import gov.nasa.worldwind.globes.Globe;
import lombok.Getter;
import lombok.Setter;
import nl.rug.oop.flaps.simulation.model.map.coordinates.GeographicCoordinates;

@Getter
@Setter
public class ModelLoader {

    protected WorldWindow worldWindow;
    protected Globe earth;
    protected GeographicCoordinates coords;

    public ModelLoader(WorldWindow worldWindow, Globe earth, GeographicCoordinates coords) {
        this.worldWindow = worldWindow;
        this.earth = earth;
        this.coords = coords;
    }
}
