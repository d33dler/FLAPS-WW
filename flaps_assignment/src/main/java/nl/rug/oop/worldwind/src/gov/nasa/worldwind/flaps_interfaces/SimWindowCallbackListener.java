package gov.nasa.worldwind.flaps_interfaces;

import gov.nasa.worldwind.geom.Position;

/**
 * Used for worldWind WorldWindow GLCanvas matrix changes tracking
 */
public interface SimWindowCallbackListener {
    void windowStateChanged(Position userPosition);

}
