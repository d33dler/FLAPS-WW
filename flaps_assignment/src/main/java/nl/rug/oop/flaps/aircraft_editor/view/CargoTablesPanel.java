package nl.rug.oop.flaps.aircraft_editor.view;

import nl.rug.oop.flaps.aircraft_editor.model.EditorCore;
import nl.rug.oop.flaps.simulation.model.aircraft.CargoArea;
import nl.rug.oop.flaps.simulation.model.cargo.CargoType;
import nl.rug.oop.flaps.simulation.model.cargo.CargoUnit;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class CargoTablesPanel extends JPanel {

    private CargoArea cargoArea;
    private EditorCore editorCore;
    private Set<CargoType> cargoTypeSet;
    private JTable allCargo, aircraftCargo;
    private JTextField searchDb, searchAircraft;
    private CargoType selected;
    private HashMap<String, CargoType> cargoHashMap = new HashMap<>();
    private SettingsPanel settingsPanel;
    private Set<CargoUnit> aircraftCargoUnits;

    public CargoTablesPanel(EditorCore editorCore, Set<CargoType> cargoTypeSet) {
        this.editorCore = editorCore;
        this.settingsPanel = editorCore.getEditorFrame().getSettingsPanel();
        this.cargoTypeSet = cargoTypeSet;
        this.cargoArea = (CargoArea) settingsPanel.getCompartmentArea();
        addUnitSet();
        init();
    }

    private void init() {
        setPreferredSize(new Dimension(600, 300));
        cargoTypeSet.forEach(cargoType -> cargoHashMap.put(cargoType.getName(), cargoType));
        allCargo = addTable("all");
        aircraftCargo = addTable("aircraft");
        editTableView(allCargo);
        add(new JScrollPane(allCargo), BorderLayout.WEST);
        add(new JScrollPane(aircraftCargo), BorderLayout.EAST);
    }

    private void addUnitSet() {
        this.aircraftCargoUnits = editorCore.getAircraft().getCargoAreaContents().get(cargoArea);
        if (aircraftCargoUnits == null) {
            System.out.println("He didn't init them");
            this.aircraftCargoUnits = new HashSet<>();
            editorCore.getAircraft().getCargoAreaContents().put(cargoArea,aircraftCargoUnits);
        }
    }

    public JTable addTable(String command) {
        DefaultTableModel model;
        if (command.equals("all")) {
            model = editorCore.getCargoDatabase().getDatabase(cargoTypeSet);
        } else {
            model = editorCore.getCargoDatabase().getAircraftHold(aircraftCargoUnits);
        }
        JTable table = new JTable(model) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table.getTableHeader().setReorderingAllowed(false);
        table.setFillsViewportHeight(true);
        table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setPreferredScrollableViewportSize(new Dimension(250, 200));
        addTableSelectionListener(table);
        return table;
    }

    public void editTableView(JTable table) {
        table.removeColumn(table.getColumnModel().getColumn(2));
    }

    public void addTableSelectionListener(JTable table) {
        table.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && !table.getSelectionModel().isSelectionEmpty()) {
                selected = cargoHashMap.get(table.getValueAt(table.getSelectedRow(), 0).toString());
            }
        });
    }
}
