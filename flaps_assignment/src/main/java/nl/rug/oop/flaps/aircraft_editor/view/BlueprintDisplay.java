package nl.rug.oop.flaps.aircraft_editor.view;

import lombok.SneakyThrows;
import nl.rug.oop.flaps.aircraft_editor.model.BlueprintSelectionListener;
import nl.rug.oop.flaps.aircraft_editor.model.BlueprintSelectionModel;
import nl.rug.oop.flaps.simulation.model.aircraft.Aircraft;
import nl.rug.oop.flaps.simulation.model.aircraft.Compartment;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;


public class BlueprintDisplay extends JPanel implements BlueprintSelectionListener {
    private Image blueprintImage;
    private Image cachedBpImage;
    private Aircraft aircraft;
    public static final double MK_SIZE = 13;
    public static final int WIDTH = 500, HEIGHT = 500;
    private BlueprintSelectionModel sm;
    private BlueprintPanel bpPanel;

    @SneakyThrows
    public BlueprintDisplay(BlueprintSelectionModel editorCore, Aircraft aircraft, BlueprintPanel bpPanel) {
        this.bpPanel = bpPanel;
        this.sm = editorCore;
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.aircraft = aircraft;
        this.blueprintImage = aircraft.getType().
                getBlueprintImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_SMOOTH);
        sm.addListener(this);
    }

    @Override
    protected void paintComponent(Graphics schema) {
        super.paintComponent(schema);
        Graphics2D g2d = (Graphics2D) schema;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.WHITE);
        if (this.cachedBpImage == null) {
            this.cachedBpImage =
                    this.blueprintImage.getScaledInstance(WIDTH, HEIGHT, Image.SCALE_SMOOTH);
        }
        g2d.drawImage(this.cachedBpImage, 0, 0, this);
        this.aircraft.getType().getCargoAreas().forEach(cargoArea -> addAreaIndicators(g2d, cargoArea, Color.blue));
        this.aircraft.getType().getFuelTanks().forEach(fuelArea -> addAreaIndicators(g2d, fuelArea, Color.magenta));
    }

    private void addAreaIndicators(Graphics2D g2d, Compartment area, Color c) {
        double mk = MK_SIZE;
        boolean adjust = false;
        if (sm.getCompartment() != null && sm.getCompartment().equals(area)) {
            c = Color.GREEN;
            mk *= 2;
            adjust = true;
        }
        g2d.setColor(c);
        addIndicator(g2d, area, mk, adjust);
    }

    private void addIndicator(Graphics2D g2d, Compartment area, double mk, boolean adjust) {
        Point2D.Double pos = bpPanel.getModel().getLocalCoords().get(area.hashCode());
        if (adjust) {
            g2d.fill(new Ellipse2D.Double(pos.x - MK_SIZE / 2, pos.y - MK_SIZE / 2, mk, mk));
        } else {
            g2d.fill(new Rectangle2D.Double(pos.x, pos.y, mk, mk));
        }
    }

    @Override
    public void compartmentSelected(Compartment area) {
        this.repaint();
    }
}