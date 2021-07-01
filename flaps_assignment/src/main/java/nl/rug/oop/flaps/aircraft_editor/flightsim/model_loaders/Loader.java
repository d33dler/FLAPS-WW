package nl.rug.oop.flaps.aircraft_editor.flightsim.model_loaders;

import gov.nasa.worldwind.osm.map.worldwind.gl.obj.ObjRenderable;

public interface Loader {
    ObjRenderable loadObjects();
}
