package nl.rug.oop.flaps.aircraft_editor.view;

import lombok.SneakyThrows;
import nl.rug.oop.flaps.aircraft_editor.controller.actions.BlueprintSelectionListener;
import nl.rug.oop.flaps.aircraft_editor.model.BlueprintSelectionModel;
import nl.rug.oop.flaps.aircraft_editor.model.EditorCore;
import nl.rug.oop.flaps.simulation.model.aircraft.Aircraft;
import nl.rug.oop.flaps.simulation.model.aircraft.Compartment;
import nl.rug.oop.flaps.simulation.model.aircraft.FuelTank;
import nl.rug.oop.flaps.simulation.view.shapes.RegularPolygon;
import nl.rug.oop.flaps.simulation.view.shapes.RoundPolygon;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.geom.Point2D;


public class BlueprintDisplay extends JPanel implements BlueprintSelectionListener {
    private Image blueprintImage;
    private Image cachedBpImage;
    private Aircraft aircraft;
    public static final int MK_SIZE = 18;
    public static int WIDTH, HEIGHT;
    private BlueprintSelectionModel sm;
    private BlueprintPanel bpPanel;
    private Graphics2D g2d;
    private final static String listener_Id = EditorCore.generalListenerID;

    /* Color Palette */
    private final static Color
            C_ROYBLUE = new Color(48, 87, 225, 218),
            C_HMAG = new Color(232, 55, 238, 211),
            C_HGREEN = new Color(8, 212, 14, 220),
            BP_BG = new Color(58, 66, 80, 171);

    /**
     * @param bpSmodel
     * @param aircraft
     * @param bpPanel
     */
    @SneakyThrows
    public BlueprintDisplay(BlueprintSelectionModel bpSmodel, Aircraft aircraft, BlueprintPanel bpPanel) {
        this.bpPanel = bpPanel;
        this.sm = bpSmodel;
        this.aircraft = aircraft;
        setBackground(BP_BG);
        getSizes();
        sm.addListener(listener_Id,this);

    }

    private void getSizes() {
        setLayout(new FlowLayout());
        WIDTH = (int) (EditorCore.BP_WIDTH);
        HEIGHT = (int) (EditorCore.BP_HEIGHT);
        setPreferredSize(new Dimension((WIDTH), (int) (HEIGHT + EditorCore.BP_MARGIN)));
        this.blueprintImage = aircraft.getType().
                getBlueprintImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_SMOOTH);
    }

    @Override
    protected void paintComponent(Graphics schema) {
        super.paintComponent(schema);
        g2d = (Graphics2D) schema;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.WHITE);
        if (this.cachedBpImage == null) {
            this.cachedBpImage =
                    this.blueprintImage.getScaledInstance(WIDTH, HEIGHT, Image.SCALE_SMOOTH);
        }
        g2d.drawImage(this.cachedBpImage, (int) EditorCore.BP_POS.x, (int) EditorCore.BP_POS.y, this);
        this.aircraft.getType().getCargoAreas()
                .forEach(cargoArea -> addAreaIndicators(cargoArea, C_ROYBLUE));
        this.aircraft.getType().getFuelTanks()
                .forEach(fuelArea -> addAreaIndicators(fuelArea, C_HMAG));
    }

    private void addAreaIndicators(Compartment area, Color c) {
        int mk = MK_SIZE;
        boolean adjust = false;
        if (sm.getCompartment() != null && sm.getCompartment().equals(area)) {
            c = C_HGREEN;
            mk *= 1.45;
            adjust = true;
        }
        g2d.setColor(c);
        addIndicator(area, mk, adjust);
    }

    private void addIndicator(Compartment area, int mk, boolean adjust) {
        Point2D.Double pos = bpPanel.getModel().getLocalCoords().get(area.hashCode());
        int off = MK_SIZE / 2;
        int coordX = (int) (pos.x + off);
        int coordY = (int) (pos.y + off);
        if (adjust) {
            g2d.fill(new RegularPolygon(coordX, coordY, mk, 8, 0));
        } else {
            g2d.fill(new RoundPolygon(new RegularPolygon(coordX, coordY, mk, 4, 0), 5));
        }
    }

    @Override
    public void compartmentSelected(Compartment area) {
        this.repaint();
    }
}