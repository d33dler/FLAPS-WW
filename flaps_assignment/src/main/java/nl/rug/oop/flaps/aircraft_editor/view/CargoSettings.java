package nl.rug.oop.flaps.aircraft_editor.view;

import lombok.SneakyThrows;
import nl.rug.oop.flaps.aircraft_editor.model.EditorCore;
import nl.rug.oop.flaps.simulation.model.aircraft.Aircraft;
import nl.rug.oop.flaps.simulation.model.aircraft.CargoArea;
import nl.rug.oop.flaps.simulation.model.cargo.CargoType;

import javax.swing.*;
import java.awt.*;
import java.util.Set;

public class CargoSettings extends JFrame {
    private EditorCore editorCore;
    private CargoArea cargoArea;
    private Aircraft aircraft;
    private Set<CargoType> cargoTypeSet;
    private CargoTablesPanel cargoTables;
    private static final int WIDTH = 700, LENGTH = 500;

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
        this.cargoTables = new CargoTablesPanel(editorCore, cargoTypeSet);
        cargoTables.setPreferredSize(new Dimension(600,250));
        add(cargoTables, BorderLayout.CENTER);
    }

}
