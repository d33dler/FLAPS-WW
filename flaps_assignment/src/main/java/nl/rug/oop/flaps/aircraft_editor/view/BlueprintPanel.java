package nl.rug.oop.flaps.aircraft_editor.view;

import lombok.SneakyThrows;
import nl.rug.oop.flaps.aircraft_editor.model.EditorCore;
import nl.rug.oop.flaps.simulation.model.aircraft.Aircraft;
import nl.rug.oop.flaps.simulation.model.world.WorldSelectionModel;
import nl.rug.oop.flaps.simulation.model.world.WorldSelectionModelListener;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;

import static nl.rug.oop.flaps.aircraft_editor.view.BlueprintDisplay.INDICATOR_SIZE;

public class BlueprintPanel extends JPanel implements WorldSelectionModelListener {
    EditorCore model;

    private BlueprintDisplay blueprintDisplay;
    private WorldSelectionModel selectionModel;
    private Aircraft aircraft;
    private static double AIRCRAFT_LEN;
    public static final double INDICATOR_SIZE = 13;
    private final int WIDTH = 650, HEIGHT = 650;
    public BlueprintPanel(EditorCore model, WorldSelectionModel selectionModel, Aircraft aircraft) {
        this.model = model;
        this.selectionModel = selectionModel;
        this.aircraft = aircraft;
        init();
    }

    @SneakyThrows
    private void init() {
        updateUnitCoords();
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(WIDTH,HEIGHT));
        this.setBorder(BorderFactory.createCompoundBorder());
        this.blueprintDisplay = new BlueprintDisplay(this.aircraft);
        add(blueprintDisplay, BorderLayout.CENTER);
    }

    private void updateUnitCoords() {
        if (!this.aircraft.isUpdatedUnitXY()) {
            AIRCRAFT_LEN = this.aircraft.getType().getLength();
            this.aircraft.getType().getCargoAreas().forEach(cargoArea -> {
                var p = remap(cargoArea.getCoords());
                System.out.println(cargoArea.getCoords().x + "  " + cargoArea.getCoords().y + " <- before\n");
                cargoArea.setX(p.x);
                cargoArea.setY(p.y);
                System.out.println(p.x + " <- setX\n");
                System.out.println(p.y + " <- setY\n");
            });
            this.aircraft.getType().getFuelTanks().forEach(fuelTank -> {
                var p = remap(fuelTank.getCoords());
                fuelTank.setX(p.x);
                fuelTank.setY(p.y);
            });
            this.aircraft.setUpdatedUnitXY(true);
        }
    }

    protected Point2D.Double remap(Point2D.Double source) {
        double s = INDICATOR_SIZE;
        double x = ((double) 500 / 2) + (source.x * ((double) 500 / AIRCRAFT_LEN)) - s / 2;
        double y = (source.y) * ((double) 500 / AIRCRAFT_LEN) - s / 2;
        return new Point2D.Double(x, y);
    }

    private void drawCargoIndicators() {

    }

    private void drawFuelIndicators() {

    }

}
