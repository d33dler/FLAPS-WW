package nl.rug.oop.flaps.aircraft_editor.view.cargoeditor;

import lombok.Getter;
import lombok.Setter;
import nl.rug.oop.flaps.aircraft_editor.controller.AircraftDataTracker;
import nl.rug.oop.flaps.aircraft_editor.controller.execcomm.commrelay.SelectionCommand;
import nl.rug.oop.flaps.aircraft_editor.model.EditorCore;
import nl.rug.oop.flaps.aircraft_editor.model.listeners.interfaces.CargoUnitsListener;
import nl.rug.oop.flaps.aircraft_editor.view.maineditor.area_panels.CargoPanel;
import nl.rug.oop.flaps.aircraft_editor.view.maineditor.EditorWindows;
import nl.rug.oop.flaps.simulation.model.aircraft.Aircraft;
import nl.rug.oop.flaps.simulation.model.aircraft.areas.CargoArea;
import nl.rug.oop.flaps.simulation.model.cargo.CargoFreight;
import nl.rug.oop.flaps.simulation.model.cargo.CargoType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.HashMap;
import java.util.Set;

/**
 * CargoTradeFrame class - frame displaying the cargo exchange environment between the user's aircraft cargo area
 * and the airports warehouse;
 */

@Getter
@Setter
public class CargoTradeFrame extends EditorWindows implements CargoUnitsListener, WindowListener {
    private EditorCore editorCore;
    private CargoArea cargoArea;
    private Aircraft aircraft;
    private Set<CargoType> cargoTypeSet;
    private CargoTablesPanel cargoWarehouse, cargoAircraft;
    private CargoAmountPanel cargoAmountPanel;
    private HashMap<String, CargoType> cargoHashMap = new HashMap<>();
    private HashMap<String, CargoFreight> freightHashMap = new HashMap<>();
    private String commandRequest;
    private int quantity;
    private CargoButtonPanel cargoButtonPanel;
    private CargoType selectedType;
    private CargoFreight selectedFreight;
    private CargoPanel cargoPanel;
    private SelectionCommand selectionCommand;
    private float totalCargoAreaWgt;
    protected static final String
            WAREHOUSE_DB = "allcargo", CARGO_PLANE = "aircargo",
            TITLE_L = "Warehouse: ", TITLE_R = "Aircraft Cargo";
    private static final int WIDTH = 1200, LENGTH = 500;
    private int amount;


    public CargoTradeFrame(EditorCore editorCore, CargoArea cargoArea, CargoPanel cargoPanel) {
        setTitle("Cargo Areas Loader");
        this.editorCore = editorCore;
        this.cargoArea = cargoArea;
        this.aircraft = editorCore.getAircraft();
        this.cargoTypeSet = editorCore.getWorld().getCargoSet();
        this.totalCargoAreaWgt = 0;
        this.cargoPanel = cargoPanel;
        editorCore.getAircraftLoadingModel().addListener(this);
        addWindowListener(this);
        init();
    }

    /**
     * Sets common settings
     */
    private void init() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(WIDTH, LENGTH));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        populateDatabaseHashmaps();
        addAllPanels();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
        pack();
    }

    /**
     * Store cargo type names and cargo freight IDs for fast retrieval during selections;
     * Storing is dynamical - based on current warehouse types and cargo area's freights;
     */
    private void populateDatabaseHashmaps(){
        cargoTypeSet.forEach(cargoType -> {
            cargoHashMap.put(cargoType.getName(), cargoType);
        });
        aircraft.getCargoAreaContents(cargoArea).forEach(cargoFreight
                -> {
            totalCargoAreaWgt += cargoFreight.getTotalWeight();
            freightHashMap.put(cargoFreight.getId(), cargoFreight);
        });
    }

    /**
     * Initializes and adds all JTables and internal JComponents for the frame;
     */
    private void addAllPanels() {
        this.cargoWarehouse = new CargoTablesPanel(editorCore, cargoTypeSet,
                this, WAREHOUSE_DB, TITLE_L + editorCore.getSource().getName());
        this.cargoAircraft = new CargoTablesPanel(editorCore, cargoTypeSet, this, CARGO_PLANE, TITLE_R);
        this.cargoAmountPanel = new CargoAmountPanel(this);
        this.cargoButtonPanel = new CargoButtonPanel(editorCore, this);
        cargoWarehouse.setPreferredSize(new Dimension(520, 300));
        cargoAircraft.setPreferredSize(new Dimension(520, 300));
        cargoAmountPanel.setPreferredSize(new Dimension(200, 40));
        addTableListeners();
        add(cargoWarehouse, BorderLayout.WEST);
        add(cargoAircraft, BorderLayout.EAST);
        add(cargoAmountPanel, BorderLayout.SOUTH);
        add(cargoButtonPanel, BorderLayout.CENTER);
        cargoAmountPanel.setVisible(false);
    }

    /**
     * Adds the table listeners
     */
    private void addTableListeners() {
        addDatabaseSelectionListener(cargoWarehouse.getCargoTable());
        addAircraftCargoSelectionListener(cargoAircraft.getCargoTable());
    }

    /**
     *
     * A separate listener for the warehouse cargo set database
     */
    public void addDatabaseSelectionListener(JTable table) {
        table.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && !table.getSelectionModel().isSelectionEmpty()) {
                cargoAircraft.getCargoTable().getSelectionModel().clearSelection();
                selectedType = cargoHashMap.get(table.getValueAt(table.getSelectedRow(), 0).toString());
                cargoButtonPanel.switchOne();
                System.out.println(selectedType.getName());
            }
        });
    }

    /**
     * A separate listener for the aircraft cargo area freight set database. Necessary, since a cargo area may have
     * different cargo freight units with the same cargoType.
     */
    public void addAircraftCargoSelectionListener(JTable table) {
        table.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && !table.getSelectionModel().isSelectionEmpty()) {
                cargoWarehouse.getCargoTable().getSelectionModel().clearSelection();
                selectedFreight = freightHashMap.get(table.getValueAt(table.getSelectedRow(), 0).toString());
                selectedType = cargoHashMap.get(table.getValueAt(table.getSelectedRow(), 1).toString());
                cargoButtonPanel.switchTwo();
            }
        });
    }

    /**
     * Delegate specific cargo exchange command
     */
    protected void delegateCommand() {
        performPriorUpdates();
        if (selectionCommand != null) {
            selectionCommand.confirmExec();
        }
    }

    /**
     * Collect setting data and update values before delegating the execution of the selected command;
     */
    protected void performPriorUpdates() {
        if (!cargoAmountPanel.getAmountField().getText().isBlank()) {
            this.amount = Integer.parseInt(cargoAmountPanel.getAmountField().getText());
        }
    }

    /**
     *
     * Updates the JTable view
     */
    @Override
    public void fireCargoTradeUpdate(AircraftDataTracker dataTracker) {
        cargoAircraft.update();
    }

    @Override
    public void windowClosed(WindowEvent e) {
        editorCore.getEditorFrame().getSettingsPanel().setCargoTradeFrame(null);
        cargoPanel.getExCargoLoader().setEnabled(true);
    }
}