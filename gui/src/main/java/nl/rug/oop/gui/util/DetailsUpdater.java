package nl.rug.oop.gui.util;
import nl.rug.oop.gui.model.AppCore;
import java.util.ArrayList;
import java.util.List;

/**
 * DetailsUpdater - updater class holding the list of listeners relevant to
 * the DetailsPanel view component;
 */
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
