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

        // Set up a layer to display the on-screen layer tree in the WorldWindow.
        this.hiddenLayer = new RenderableLayer();
        this.hiddenLayer.addRenderable(this.layerTree);
        worldView.getWwd().getModel().getLayers().add(this.hiddenLayer);

        // Mark the layer as hidden to prevent it being included in the layer tree's model. Including the layer in
        // the tree would enable the user to hide the layer tree display with no way of bringing it back.
        this.hiddenLayer.setValue(AVKey.HIDDEN, true);

        // Refresh the tree model with the WorldWindow's current layer list.
        this.layerTree.getModel().refresh(worldView.getWwd().getModel().getLayers());

        // Add a controller to handle input events on the layer tree.
        this.controller = new HotSpotController(worldView.getWwd());
    }
}
