package nl.rug.oop.flaps.aircraft_editor.view;

import nl.rug.oop.flaps.aircraft_editor.model.EditorCore;
import nl.rug.oop.flaps.simulation.model.aircraft.Aircraft;
import nl.rug.oop.flaps.simulation.model.aircraft.CargoArea;
import nl.rug.oop.flaps.simulation.model.cargo.CargoType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Set;

public class CargoSettings extends JFrame {
    private EditorCore editorCore;
    private CargoArea cargoArea;
    private Aircraft aircraft;
    private Set<CargoType> cargoTypeSet;
    private CargoTablesPanel cargoWarehouse, cargoAircraft;
    private static final int WIDTH = 1000, LENGTH = 600;
    protected static final String CARGO_ALL = "allcargo", CARGO_PLANE = "aircargo";

    public CargoSettings(EditorCore editorCore, CargoArea cargoArea, Set<CargoType> newSet) {
        super("Cargo Areas Loader");
        this.editorCore = editorCore;
        this.cargoArea = cargoArea;
        this.aircraft = editorCore.getAircraft();
        this.cargoTypeSet = newSet;
        init();
    }

    private void init() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(WIDTH, LENGTH));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addAllPanels();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
        pack();
    }

    private void addAllPanels() {
        this.cargoWarehouse = new CargoTablesPanel(editorCore, cargoTypeSet, CARGO_ALL);
        this.cargoAircraft = new CargoTablesPanel(editorCore, cargoTypeSet, CARGO_PLANE);
        cargoWarehouse.setPreferredSize(new Dimension(500, 300));
        cargoAircraft.setPreferredSize(new Dimension(500, 300));
        add(cargoWarehouse, BorderLayout.WEST);
        add(cargoAircraft, BorderLayout.EAST);
    }

    private void addAllButtons() {
        JButton add = new JButton("Add");
        add.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        JButton remove = new JButton("Remove");
        remove.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
}
