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
    private static double AIRCRAFT_LEN;
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
        updateUnitCoords();
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBorder(BorderFactory.createCompoundBorder());
        this.blueprintDisplay = new BlueprintDisplay(this.aircraft);
        add(blueprintDisplay, BorderLayout.CENTER);
    }

    private void updateUnitCoords() {
        if (!this.aircraft.isUpdatedUnitXY()) {
            AIRCRAFT_LEN = this.aircraft.getType().getLength();
            updateXY(this.aircraft.getType().getCargoAreas());
            updateXY(this.aircraft.getType().getFuelTanks());
            this.aircraft.setUpdatedUnitXY(true);
        }
    }

    private void updateXY(List<? extends Compartment> units) {
        units.forEach(cargoArea -> {
            var p = remap(cargoArea.getCoords());
            cargoArea.setX(p.x);
            cargoArea.setY(p.y);
        });
    }

    protected Point2D.Double remap(Point2D.Double source) {
        double bpSize = BlueprintDisplay.WIDTH;
        double s = BlueprintDisplay.MK_SIZE;
        double x = (bpSize / 2) + (source.x * (bpSize / AIRCRAFT_LEN)) - s / 2;
        double y = (source.y) * (bpSize / AIRCRAFT_LEN) - s / 2;
        return new Point2D.Double(x, y);
    }


}
