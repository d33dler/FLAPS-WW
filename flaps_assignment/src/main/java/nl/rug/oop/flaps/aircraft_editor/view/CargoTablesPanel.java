package nl.rug.oop.flaps.aircraft_editor.view;

import lombok.Getter;
import lombok.Setter;
import nl.rug.oop.flaps.aircraft_editor.controller.actions.CargoUnitsListener;
import nl.rug.oop.flaps.aircraft_editor.model.EditorCore;
import nl.rug.oop.flaps.simulation.model.aircraft.Aircraft;
import nl.rug.oop.flaps.simulation.model.aircraft.CargoArea;
import nl.rug.oop.flaps.simulation.model.cargo.CargoFreight;
import nl.rug.oop.flaps.simulation.model.cargo.CargoType;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class CargoTablesPanel extends JPanel {
    private CargoArea cargoArea;
    private EditorCore editorCore;
    private JTable cargoTable;
    private JTextField searchDb, searchAircraft;
    private CargoSettings cargoSettings;
    private Set<CargoType> cargoTypeSet;
    private Set<CargoFreight> aircraftCargoUnits;
    private String command;

    public CargoTablesPanel(EditorCore editorCore, Set<CargoType> cargoTypeSet,
                            CargoSettings cargoSettings, String command) {
        this.command = command;
        this.editorCore = editorCore;
        this.cargoSettings = cargoSettings;
        this.cargoTypeSet = cargoTypeSet;
        this.cargoArea = (CargoArea) editorCore.getEditorFrame().getSettingsPanel().getCompartmentArea();
        addUnitSet();
        init(command);
    }

    private void init(String command) {
        setPreferredSize(new Dimension(500, 300));
        if (command.equals(CargoSettings.CARGO_ALL)) {
            cargoTable = addTable(CargoSettings.CARGO_ALL);
            add(new JScrollPane(cargoTable), BorderLayout.WEST);
        } else {
            cargoTable = addTable(CargoSettings.CARGO_PLANE);
            add(new JScrollPane(cargoTable), BorderLayout.EAST);

            editTableView(cargoTable, 1);
        }
    }

    private void addUnitSet() {
        this.aircraftCargoUnits = editorCore.getAircraft().getCargoAreaContents().get(cargoArea);
        if (aircraftCargoUnits == null) {
            System.out.println("He didn't init them");
            this.aircraftCargoUnits = new HashSet<>();
            editorCore.getAircraft().getCargoAreaContents().put(cargoArea, aircraftCargoUnits);
        }
    }

    private JTable addTable(String command) {
        JTable table = new JTable(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        DefaultTableModel model;
        if (command.equals(CargoSettings.CARGO_ALL)) {
            model = editorCore.getCargoDatabase().getDatabase(cargoTypeSet, CargoType.class);
        } else {
            model = editorCore.getCargoDatabase().getDatabase(aircraftCargoUnits, CargoFreight.class);
        }
        table.setModel(model);
        table.getTableHeader().setReorderingAllowed(false);
        table.setFillsViewportHeight(true);
        table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setPreferredScrollableViewportSize(new Dimension(500, 200));
        return table;
    }

    protected void editTableView(JTable table, int index) {
        table.removeColumn(table.getColumnModel().getColumn(index));
    }
    protected void update() {
        aircraftCargoUnits = editorCore.getAircraft().getCargoAreaContents().get(cargoArea);
        aircraftCargoUnits.forEach(obj -> System.out.println(obj.getCargoType().getName()));
        cargoTable.setModel(editorCore.getCargoDatabase().getDatabase(aircraftCargoUnits, CargoFreight.class));
        editTableView(cargoTable, 1);
        cargoTable.repaint();
    }
}
