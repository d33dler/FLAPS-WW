package nl.rug.oop.gui.util;

import nl.rug.oop.gui.model.AppCore;

import java.util.ArrayList;
import java.util.List;

public class Updater {
    private List<UpdateListeners> upMembers;

    public Updater() {
        this.upMembers = new ArrayList<>();
    }

    public void addListener(UpdateListeners newL) {
        upMembers.add(newL);
    }

    public void fireUpdate(AppCore model) {
        upMembers.forEach(updateListeners -> updateListeners.update(model));
    }
}
