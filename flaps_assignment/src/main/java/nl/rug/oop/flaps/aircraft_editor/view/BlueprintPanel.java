package nl.rug.oop.flaps.aircraft_editor.view;

import lombok.SneakyThrows;
import nl.rug.oop.flaps.aircraft_editor.controller.actions.AircraftUnitSelectionController;
import nl.rug.oop.flaps.aircraft_editor.model.BlueprintSelectionModel;
import nl.rug.oop.flaps.aircraft_editor.model.EditorCore;
import nl.rug.oop.flaps.simulation.controller.AirportSelectionController;
import nl.rug.oop.flaps.simulation.model.aircraft.Aircraft;
import nl.rug.oop.flaps.simulation.model.aircraft.Compartment;
import nl.rug.oop.flaps.simulation.model.world.WorldSelectionModel;
import nl.rug.oop.flaps.simulation.model.world.WorldSelectionModelListener;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.List;

public class BlueprintPanel extends JPanel implements WorldSelectionModelListener {

    private final EditorCore model;
    private BlueprintDisplay blueprintDisplay;
    private BlueprintSelectionModel selectionModel;
    private Aircraft aircraft;

    private static final int WIDTH = 650, HEIGHT = 650;

    public BlueprintPanel(EditorCore model, BlueprintSelectionModel selectionModel, Aircraft aircraft) {
        this.model = model;
        this.selectionModel = selectionModel;
        this.aircraft = aircraft;

        AircraftUnitSelectionController controller = new AircraftUnitSelectionController(model);
        addMouseListener(controller);
        init();
    }

    @SneakyThrows
    private void init() {

        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBorder(BorderFactory.createCompoundBorder());
        this.blueprintDisplay = new BlueprintDisplay(this.aircraft);
        add(blueprintDisplay, BorderLayout.CENTER);
    }




}
