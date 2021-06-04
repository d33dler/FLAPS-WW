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
import java.awt.*;
import java.util.HashMap;
import java.util.Set;

@Getter
@Setter
public class CargoSettings extends JFrame implements CargoUnitsListener {
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
    private float totalCargoAreaWgt;
    protected static final String CARGO_ALL = "allcargo", CARGO_PLANE = "aircargo";
    private static final int WIDTH = 1200, LENGTH = 600;

    public CargoSettings(EditorCore editorCore, CargoArea cargoArea) {
        super("Cargo Areas Loader");
        this.editorCore = editorCore;
        this.cargoArea = cargoArea;
        this.aircraft = editorCore.getAircraft();
        this.cargoTypeSet = editorCore.getWorld().getCargoSet();
        this.totalCargoAreaWgt = 0;
        editorCore.getCargoManipulationModel().addListener(this);
        init();
    }

    private void init() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(WIDTH, LENGTH));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        cargoTypeSet.forEach(cargoType -> {
            cargoHashMap.put(cargoType.getName(), cargoType);
        });
        aircraft.getCargoAreaContents(cargoArea).forEach(cargoFreight
                -> {
            totalCargoAreaWgt += cargoFreight.getTotalWeight();
            freightHashMap.put(cargoFreight.getId(), cargoFreight);
        });

        addAllPanels();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
        pack();
    }

    private void addAllPanels() {
        this.cargoWarehouse = new CargoTablesPanel(editorCore, cargoTypeSet, this, CARGO_ALL);
        this.cargoAircraft = new CargoTablesPanel(editorCore, cargoTypeSet, this, CARGO_PLANE);
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

    private void addTableListeners() {
        addDatabaseSelectionListener(cargoWarehouse.getCargoTable());
        addAircraftCargoSelectionListener(cargoAircraft.getCargoTable());
    }

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

    protected void delegateCommands(String command) {
        int amount = Integer.parseInt(cargoAmountPanel.getAmountField().getText());
        if (command != null) {
            switch (command) {
                case CargoButtonPanel.ADD_COM: {
                    float weight = amount * selectedType.getWeightPerUnit();
                    if (editorCore.getDataTracker().performCargoCheck(weight)) {
                        editorCore.getConfigurator().unitAdded(aircraft, selectedType, amount);
                    }
                    return;
                }
                case CargoButtonPanel.REM_COM: {
                    editorCore.getConfigurator().unitRemoved(aircraft, selectedFreight, amount);
                    return;
                }
                case CargoButtonPanel.REMALL_COM: {
                    editorCore.getConfigurator().allCargoRemove(aircraft);
                    return;
                }
                default:
            }
        } else {
            System.out.println("There was an issue loading the cargo");
        }
    }

    @Override
    public void notifyChange(Aircraft aircraft) {
        CargoUnitsListener.super.notifyChange(aircraft);
        System.out.println("repaintTable");
        cargoAircraft.update();
    }
}