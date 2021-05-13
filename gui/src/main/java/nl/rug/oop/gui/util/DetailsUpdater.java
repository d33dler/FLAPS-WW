package nl.rug.oop.gui.util;

import nl.rug.oop.gui.model.AppCore;

import java.util.ArrayList;
import java.util.List;

public class DetailsUpdater implements UpdaterFunctions {
    List<UpdateInterface> updateDetailsList;

    public DetailsUpdater() {
        this.updateDetailsList = new ArrayList<>();
    }

    @Override
    public void addListener(UpdateInterface newListener) {
        updateDetailsList.add(newListener);
    }

    @Override
    public void fireUpdate(AppCore model) {
        updateDetailsList.forEach(updateDetails -> updateDetails.update(model));
    }
}
