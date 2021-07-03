package gov.nasa.worldwind.flaps_interfaces;

import gov.nasa.worldwind.geom.Position;

import java.util.ArrayList;
import java.util.List;


public class SimWindowChangeModel {
    public List<SimWindowCallbackListener> callbackListeners = new ArrayList<>();

    public SimWindowChangeModel() {
    }
    public void addListener(SimWindowCallbackListener listener) {
        callbackListeners.add(listener);
    }
    public void fireUpdate(Position position) {
        this.callbackListeners.forEach(listener-> {
            listener.windowStateChanged(position);
        });
    }

}
