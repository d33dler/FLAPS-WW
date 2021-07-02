package nl.rug.oop.gui.util;

import nl.rug.oop.gui.model.AppCore;

/**
 * Interface to force implementation of methods for different updater classes;
 */
public interface UpdaterFunctions {
    void addListener(UpdateInterface newListener);
    void fireUpdate(AppCore model);
}
