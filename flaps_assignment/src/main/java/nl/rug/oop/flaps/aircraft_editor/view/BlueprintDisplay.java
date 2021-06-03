package nl.rug.oop.flaps.aircraft_editor.view;

import lombok.SneakyThrows;
import nl.rug.oop.flaps.aircraft_editor.controller.actions.BlueprintSelectionListener;
import nl.rug.oop.flaps.aircraft_editor.model.BlueprintSelectionModel;
import nl.rug.oop.flaps.aircraft_editor.model.EditorCore;
import nl.rug.oop.flaps.simulation.model.aircraft.Aircraft;
import nl.rug.oop.flaps.simulation.model.aircraft.Compartment;
import nl.rug.oop.flaps.simulation.model.aircraft.FuelTank;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;


public class BlueprintDisplay extends JPanel implements BlueprintSelectionListener {
    private Image blueprintImage;
    private Image cachedBpImage;
    private Aircraft aircraft;
    public static final double MK_SIZE = 13;
    public static String BP_TITLE;
    public static int WIDTH, HEIGHT;
    private BlueprintSelectionModel sm;
    private BlueprintPanel bpPanel;

    @SneakyThrows
    public BlueprintDisplay(BlueprintSelectionModel bpSmodel, Aircraft aircraft, BlueprintPanel bpPanel) {
        this.bpPanel = bpPanel;
        this.sm = bpSmodel;
        this.aircraft = aircraft;
        getSizes();
        BP_TITLE = this.aircraft.getType().getName() + " Blueprint";
        sm.addListener(this);
    }

    private void getSizes() {
        setLayout(new FlowLayout());
        WIDTH = (int) (EditorCore.BP_WIDTH);
        HEIGHT = (int) (EditorCore.BP_HEIGHT);
        setPreferredSize(new Dimension((int) (WIDTH), (int) (HEIGHT + EditorCore.BP_MARGIN)));
        this.blueprintImage = aircraft.getType().
                getBlueprintImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_SMOOTH);
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
        g2d.drawImage(this.cachedBpImage, (int) EditorCore.BP_POS.x, (int) EditorCore.BP_POS.y, this);
        this.aircraft.getType().getCargoAreas().forEach(cargoArea -> addAreaIndicators(g2d, cargoArea, Color.blue));
        this.aircraft.getType().getFuelTanks().forEach(fuelArea -> addAreaIndicators(g2d, fuelArea, Color.magenta));
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(1), BP_TITLE, TitledBorder.CENTER, TitledBorder.ABOVE_TOP));
        Color myWhite = new Color(64, 64, 64);
        setBackground(myWhite);
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
    public void compartmentSelected(FuelTank area) {
        this.repaint();
    }
}