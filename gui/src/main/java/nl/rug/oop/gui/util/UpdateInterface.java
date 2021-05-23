package nl.rug.oop.gui.util;

import nl.rug.oop.gui.model.AppCore;

/**
 * Implemented by classes subjected to model <-> view updates;
 */
public interface UpdateInterface {
    void update(AppCore model);
}
