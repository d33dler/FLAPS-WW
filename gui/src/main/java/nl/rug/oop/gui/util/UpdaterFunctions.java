package nl.rug.oop.gui.util;

import nl.rug.oop.gui.model.AppCore;

public interface UpdaterFunctions {
    void addListener(UpdateInterface newListener);

    void fireUpdate(AppCore model);
}
