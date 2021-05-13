package nl.rug.oop.gui.util;

import nl.rug.oop.gui.model.AppCore;

import java.util.ArrayList;
import java.util.List;

public class TableUpdater implements UpdaterFunctions {
    List<UpdateInterface> updateTableList;

    public TableUpdater() {
        this.updateTableList = new ArrayList<>();
    }

    @Override
    public void addListener(UpdateInterface newListener) {
    updateTableList.add(newListener);
    }
    @Override
    public void fireUpdate(AppCore model) {
        updateTableList.forEach(updateTable -> updateTable.update(model));
    }
}
