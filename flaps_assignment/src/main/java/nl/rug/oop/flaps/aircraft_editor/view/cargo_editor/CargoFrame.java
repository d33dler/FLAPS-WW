package nl.rug.oop.flaps.aircraft_editor.view.cargo_editor;

import lombok.Getter;
import lombok.Setter;
import nl.rug.oop.flaps.aircraft_editor.controller.AircraftDataTracker;
import nl.rug.oop.flaps.aircraft_editor.controller.execcomm.comm_relay.SelectionCommand;
import nl.rug.oop.flaps.aircraft_editor.model.EditorCore;
import nl.rug.oop.flaps.aircraft_editor.model.listeners.interfaces.CargoUnitsListener;
import nl.rug.oop.flaps.aircraft_editor.model.mediators.CargoMediator;
import nl.rug.oop.flaps.aircraft_editor.view.maineditor.EditorWindows;
import nl.rug.oop.flaps.aircraft_editor.view.maineditor.area_panels.CargoPanel;
import nl.rug.oop.flaps.simulation.model.aircraft.Aircraft;
import nl.rug.oop.flaps.simulation.model.aircraft.areas.CargoArea;
import nl.rug.oop.flaps.simulation.model.cargo.CargoFreight;
import nl.rug.oop.flaps.simulation.model.cargo.CargoType;
import nl.rug.oop.flaps.simulation.model.loaders.utils.FileUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.util.Set;

/**
 * CargoTradeFrame class - frame displaying the cargo exchange environment between the user's aircraft cargo area
 * and the airports warehouse;
 */

@Getter
@Setter
public class CargoFrame extends EditorWindows implements CargoUnitsListener {

    private EditorCore editorCore;
    private CargoMediator mediator = new CargoMediator();
    private CargoArea cargoArea;
    private Aircraft aircraft;
    private Set<CargoType> cargoTypeSet;
    private Set<?> cargoUnitSet;
    private DatabaseTablePanel<CargoType> cargoWarehouse;
    private DatabaseTablePanel<CargoFreight> cargoAircraft;
    private CargoAmountPanel cargoAmountPanel;
    private CargoControlPanel cargoControlPanel;
    private CargoPanel cargoPanel;
    private SelectionCommand selectionCommand;
    protected static final String
            TITLE_L = "Warehouse: ", TITLE_R = "Aircraft Cargo: ";
    private static final int WIDTH = 1200, LENGTH = 500;


    public CargoFrame(EditorCore editorCore, CargoArea cargoArea, CargoPanel cargoPanel) {
        setTitle("Cargo Areas Loader");
        this.editorCore = editorCore;
        this.cargoArea = cargoArea;
        this.aircraft = editorCore.getAircraft();
        this.cargoTypeSet = editorCore.getWorld().getCargoSet();
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
        mediator.populateDatabaseHashmaps(aircraft, cargoTypeSet, cargoArea);
        buildDbTables();
        addAllPanels();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
        pack();
    }
    private void buildDbTables(){
        this.cargoUnitSet = FileUtils.addUnitSet(cargoArea, editorCore.getAircraft().getCargoAreaContents());
        this.cargoWarehouse = new TableBuilder<>()
                .core(editorCore)
                .frame(this)
                .db(cargoTypeSet)
                .title(TITLE_L + editorCore.getSource().getName())
                .model(editorCore.getDatabaseLoader().getDatabase(cargoTypeSet, CargoType.class))
                .pos(BorderLayout.EAST)
                .buildWarehouse();
        this.cargoAircraft = new TableBuilder<>()
                .core(editorCore)
                .frame(this)
                .db(cargoUnitSet)
                .title(TITLE_R + cargoArea.getName())
                .model(editorCore.getDatabaseLoader().getDatabase(cargoUnitSet, CargoFreight.class))
                .pos(BorderLayout.EAST)
                .editView(2, 2)
                .buildRemote();
    }

    /**
     * Initializes and adds all JTables and internal JComponents for the frame;
     */
    private void addAllPanels() {
        this.cargoAmountPanel = new CargoAmountPanel(this, editorCore.getController(), mediator);
        this.cargoControlPanel = new CargoControlPanel(editorCore, cargoWarehouse, cargoAircraft, this);
        cargoAmountPanel.setPreferredSize(new Dimension(200, 40));
        addTableListeners();
        add(cargoWarehouse, BorderLayout.WEST);
        add(cargoAircraft, BorderLayout.EAST);
        add(cargoAmountPanel, BorderLayout.SOUTH);
        add(cargoControlPanel, BorderLayout.CENTER);
        cargoAmountPanel.setVisible(false);
    }

    /**
     * Adds the table listeners
     */
    private void addTableListeners() {
        addDatabaseSelectionListener(cargoWarehouse.getDatabaseTable());
        addAircraftCargoSelectionListener(cargoAircraft.getDatabaseTable());
    }

    /**
     * A separate listener for the warehouse cargo set database
     */
    public void addDatabaseSelectionListener(JTable table) {
        table.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && !table.getSelectionModel().isSelectionEmpty()) {
                cargoAircraft.getDatabaseTable().getSelectionModel().clearSelection();
                mediator.setSelectedType(mediator.getCargoHashMap().
                        get(table.getValueAt(table.getSelectedRow(), 0).toString()));
                cargoControlPanel.setCargoImg(mediator.getSelectedType());
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
                cargoWarehouse.getDatabaseTable().getSelectionModel().clearSelection();
                mediator.setSelectedFreight(mediator.getFreightHashMap().
                        get(table.getValueAt(table.getSelectedRow(), 0).toString()));
                mediator.setSelectedType(mediator.getSelectedFreight().getCargoType());
                cargoControlPanel.setCargoImg(mediator.getSelectedType());
            }
        });
    }

    /**
     * Updates the JTable view
     */
    @Override
    public void fireCargoUpdate(AircraftDataTracker dataTracker) {
        cargoAircraft.update();
    }

    @Override
    public void windowClosed(WindowEvent e) {
        editorCore.getEditorFrame().getSettingsPanel().setCargoFrame(null);
        cargoPanel.getExCargoLoader().setEnabled(true);
    }
}