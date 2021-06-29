package nl.rug.oop.flaps.aircraft_editor.flightsim.sim_view;

import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.layers.RenderableLayer;
import gov.nasa.worldwind.util.layertree.LayerTree;
import gov.nasa.worldwindx.examples.ApplicationTemplate;
import gov.nasa.worldwindx.examples.util.HotSpotController;
import nl.rug.oop.flaps.aircraft_editor.flightsim.sim_model.FlightSimApplication;

public class MapViewControls {

    protected LayerTree layerTree;
    protected RenderableLayer hiddenLayer;
    private FlightSimApplication flightSimApp;
    protected HotSpotController controller;
    protected ApplicationTemplate.AppPanel worldView;

    public MapViewControls(FlightSimApplication iteration) {
        this.flightSimApp = iteration;
        this.worldView = iteration.getWwjPanel();
        init();
    }
    private void init() {
        this.layerTree = new LayerTree();

        this.hiddenLayer = new RenderableLayer();
        this.hiddenLayer.addRenderable(this.layerTree);
        worldView.getWwd().getModel().getLayers().add(hiddenLayer);

        this.hiddenLayer.setValue(AVKey.HIDDEN, true);

        this.layerTree.getModel().refresh(worldView.getWwd().getModel().getLayers());

        this.controller = new HotSpotController(worldView.getWwd());
    }
}
