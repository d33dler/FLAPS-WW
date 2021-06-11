package nl.rug.oop.flaps.aircraft_editor.view.cargoeditor;

import lombok.Getter;
import lombok.Setter;
import nl.rug.oop.flaps.aircraft_editor.model.EditorCore;
import nl.rug.oop.flaps.simulation.model.aircraft.CargoArea;
import nl.rug.oop.flaps.simulation.model.cargo.CargoFreight;
import nl.rug.oop.flaps.simulation.model.cargo.CargoType;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

/**
 * CargoTablesPanel class - contains the JTable itself to separate additional functions implementations;
 */
@Getter
@Setter
public class CargoTablesPanel extends JPanel {
    private CargoArea cargoArea;
    private EditorCore editorCore;
    private JTable cargoTable;
    private JTextField searchDb, searchAircraft;
    private CargoTradeFrame cargoTradeFrame;
    private Set<CargoType> cargoTypeSet;
    private Set<CargoFreight> aircraftCargoUnits;
    private String command, tableName;

    public CargoTablesPanel(EditorCore editorCore, Set<CargoType> cargoTypeSet,
                            CargoTradeFrame cargoTradeFrame, String command, String title) {
        this.command = command;
        this.tableName = title;
        this.editorCore = editorCore;
        this.cargoTradeFrame = cargoTradeFrame;
        this.cargoTypeSet = cargoTypeSet;
        this.cargoArea = (CargoArea) editorCore.getEditorFrame().getSettingsPanel().getCompartmentArea();
        addUnitSet();
        init(command);
    }

    /**
     *
     * Set common settings and construct
     * the appropriate JTable and its Table model according to the command;
     */
    private void init(String command) {
        setBorder(BorderFactory.createTitledBorder
                (BorderFactory.createEtchedBorder(1),
                        tableName, TitledBorder.CENTER, TitledBorder.ABOVE_TOP));
        setPreferredSize(new Dimension(500, 300));
        if (command.equals(CargoTradeFrame.WAREHOUSE_DB)) {
            cargoTable = addTable(CargoTradeFrame.WAREHOUSE_DB);
            add(new JScrollPane(cargoTable), BorderLayout.WEST);
        } else {
            cargoTable = addTable(CargoTradeFrame.CARGO_PLANE);
            add(new JScrollPane(cargoTable), BorderLayout.EAST);
            editTableView(cargoTable, 2, 2);
        }
    }

    /**
     * Collects the set of cargo freights for the selected cargoArea;
     * If it is null -> initialize new hashset;
     */
    private void addUnitSet() {
        this.aircraftCargoUnits = editorCore.getAircraft().getCargoAreaContents().get(cargoArea);
        if (aircraftCargoUnits == null) {
            this.aircraftCargoUnits = new HashSet<>();
            editorCore.getAircraft().getCargoAreaContents().put(cargoArea, aircraftCargoUnits);
        }
    }

    /**
     *
     * @param command command to request specific Jtable model ;
     * @return configured JTable with the required JTable model;
     */
    private JTable addTable(String command) {
        JTable table = new JTable() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        DefaultTableModel model;
        if (command.equals(CargoTradeFrame.WAREHOUSE_DB)) {
            model = editorCore.getCargoDatabase().getDatabase(cargoTypeSet, CargoType.class);
        } else {
            model = editorCore.getCargoDatabase().getDatabase(aircraftCargoUnits, CargoFreight.class);
        }
        table.setModel(model);
        table.getTableHeader().setReorderingAllowed(false);
        table.setFillsViewportHeight(true);
        table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setPreferredScrollableViewportSize(new Dimension(500, 300));
        return table;
    }

    /**
     *
     * @param table table to edit
     * @param start beginning column to eliminate
     * @param interval nr of eliminations
                Trims the number of columns for the specific table;
     */
    protected void editTableView(JTable table, int start, int interval) {
        for (int i = 0; i < interval; i++) {
            table.removeColumn(table.getColumnModel().getColumn(start));
        }
    }

    /**
     * repaints the view of the JTable
     */
    protected void update() {
        aircraftCargoUnits = editorCore.getAircraft().getCargoAreaContents().get(cargoArea);
        cargoTable.setModel(editorCore.getCargoDatabase().getDatabase(aircraftCargoUnits, CargoFreight.class));
        editTableView(cargoTable, 2, 2);
        cargoTable.repaint();
    }
}
