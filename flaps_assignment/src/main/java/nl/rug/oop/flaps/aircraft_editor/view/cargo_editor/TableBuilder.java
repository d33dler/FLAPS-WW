package nl.rug.oop.flaps.aircraft_editor.view.cargo_editor;

import lombok.SneakyThrows;
import nl.rug.oop.flaps.aircraft_editor.controller.configcore.ControlSolicitor;
import nl.rug.oop.flaps.aircraft_editor.model.EditorCore;
import nl.rug.oop.flaps.simulation.model.cargo.CargoFreight;
import nl.rug.oop.flaps.simulation.model.cargo.CargoType;
import nl.rug.oop.flaps.simulation.model.passengers.Passenger;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Set;

public class TableBuilder<T> extends DatabaseTablePanel<T> {

    public TableBuilder<T> core(EditorCore editorCore) {
        this.editorCore = editorCore;
        return this;
    }

    public TableBuilder<T> title(String title) {
        this.tableName = title;
        return this;
    }

    public TableBuilder<T> frame(JFrame tradeFrame) {
        this.holderFrame = tradeFrame;
        return this;
    }

    public TableBuilder<T> db(Set<?> set) {
        this.objectSet = set;
        return this;
    }

    public TableBuilder<T> model(DefaultTableModel model) {
        this.model = model;
        this.databaseTable = addTable();
        return this;
    }

    public TableBuilder<T> size(int w, int h) {
        this.WIDTH = w;
        this.HEIGHT = h;
        return this;
    }

    public TableBuilder<T> mediator(ControlSolicitor solicitor) {
        this.solicitor = solicitor;
        return this;
    }

    public TableBuilder<T> editView(int st, int end) {
        editTableView(databaseTable, st, end);
        return this;
    }

    public TableBuilder<T> pos(String pos) {
        this.pos = pos;
        return this;
    }

    @SneakyThrows
    public DatabaseTablePanel<CargoFreight> buildRemote() {
        DatabaseTablePanel<CargoFreight> tablesPanel = new TableBuilder<>() {
            @Override
            public void update() {
                databaseTable.setModel(editorCore.getDatabaseLoader().getDatabase(objectSet, CargoFreight.class));
                editTableView(databaseTable, 2, 2);
                super.update();
            }
        };
        init(tablesPanel, this);
        return tablesPanel;
    }

    @SneakyThrows
    public DatabaseTablePanel<CargoType> buildWarehouse() {
        DatabaseTablePanel<CargoType> tablesPanel = new TableBuilder<>();
        init(tablesPanel, this);
        return tablesPanel;
    }

    @SneakyThrows
    public DatabaseTablePanel<Passenger> buildPassengerDb() {
        DatabaseTablePanel<Passenger> tablesPanel = new TableBuilder<>() {
            @Override
            public void update() {
                objectSet = solicitor.getDataSet();
                databaseTable.setModel(editorCore.getDatabaseLoader().getDatabase(objectSet, Passenger.class));
                super.update();
            }
        };
        init(tablesPanel, this);
        return tablesPanel;
    }

}
