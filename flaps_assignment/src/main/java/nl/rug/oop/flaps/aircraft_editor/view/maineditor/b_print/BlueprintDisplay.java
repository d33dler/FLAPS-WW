package nl.rug.oop.flaps.aircraft_editor.view.maineditor.b_print;

import lombok.Setter;
import lombok.SneakyThrows;
import nl.rug.oop.flaps.aircraft_editor.model.listener_models.AircraftLoadingModel;
import nl.rug.oop.flaps.aircraft_editor.model.listener_models.BlueprintSelectionModel;
import nl.rug.oop.flaps.aircraft_editor.model.Remapper;
import nl.rug.oop.flaps.simulation.model.aircraft.Aircraft;
import nl.rug.oop.flaps.simulation.model.aircraft.areas.Compartment;
import nl.rug.oop.flaps.simulation.view.shapes.RegularPolygon;
import nl.rug.oop.flaps.simulation.view.shapes.RoundPolygon;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.List;

/**
 * BlueprintDisplay class - used to display the blueprint graphical component including all the indicators;
 */
public class BlueprintDisplay extends JPanel {
    private Image blueprintImage;
    private Image cachedBpImage;
    private Aircraft aircraft;
    public static int WIDTH, HEIGHT;
    private BlueprintSelectionModel bpSelectionModel;
    private AircraftLoadingModel cargoModel;
    private BlueprintPanel blueprintPanel;
    private Graphics2D g2d;
    private Remapper remapper;
    public static final int MK_SIZE = 18;
    private final static int OFFSET = MK_SIZE / 2;
    private boolean hover;
    @Setter
    private Compartment cursorHover;
    @Setter
    private Point2D.Double cursorHoverXY; //for tooltips
    /**
     * Color Palette
     */
    private final static Color
            C_ROYBLUE = new Color(48, 87, 225, 218),
            C_HMAG = new Color(255, 101, 21, 211),
            C_HGREEN = new Color(8, 212, 14, 220),
            C_CGMARK = new Color(200, 0, 255, 203),
            ENGINE_TEMP = new Color(255, 0, 0, 205),
            BP_BG = new Color(58, 66, 80, 171),
            C_HOVER = new Color(255, 235, 147, 213),
            C_CABIN = new Color(237, 246, 9, 213);


    /**
     * @param bpModel  selection model for blueprint indicator areas;
     * @param aircraft configured aircraft
     * @param bpPanel  blueprint parent panel
     */
    @SneakyThrows
    public BlueprintDisplay(BlueprintSelectionModel bpModel, Aircraft aircraft, BlueprintPanel bpPanel) {
        this.blueprintPanel = bpPanel;
        this.bpSelectionModel = bpModel;
        this.aircraft = aircraft;
        this.cargoModel = blueprintPanel.getModel().getAircraftLoadingModel();
        this.remapper = bpModel.getRemapper();
        setBackground(BP_BG);
        getSizes();
    }

    /**
     * sets up the blueprint size parameters according to the Remapper data;
     * collects the blueprint image;
     */
    private void getSizes() {
        setLayout(new FlowLayout());
        WIDTH = (int) (Remapper.BP_WIDTH);
        HEIGHT = (int) (Remapper.BP_HEIGHT);
        setPreferredSize(new Dimension((WIDTH), (int) (HEIGHT + Remapper.BP_MARGIN)));
        this.blueprintImage = aircraft.getType().
                getBlueprintImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_SMOOTH);
    }

    /**
     * Paints the main blueprint image and all the compartment areas indicators;
     */
    @Override
    protected void paintComponent(Graphics schema) {
        super.paintComponent(schema);
        hover = false;
        g2d = (Graphics2D) schema;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.WHITE);
        if (this.cachedBpImage == null) {
            this.cachedBpImage =
                    this.blueprintImage.getScaledInstance(WIDTH, HEIGHT, Image.SCALE_SMOOTH);
        }
        g2d.drawImage(this.cachedBpImage, (int) Remapper.BP_POS.x, (int) Remapper.BP_POS.y, this);
        addCoGindicator();
        addCabinIndicator();
        addSpecificIndicators(aircraft.getType().getCargoAreas(), BlueprintIcons.cargoIcon, C_ROYBLUE);
        addSpecificIndicators(aircraft.getType().getFuelTanks(), BlueprintIcons.fuelIcon, C_HMAG);
        addSpecificIndicators(aircraft.getType().getEngines(), BlueprintIcons.engineIcon, ENGINE_TEMP);
    }

    private int dynMK;
    private boolean dynAdjust;

    private void addSpecificIndicators(List<?> list, Image icon, Color color) {
        list.forEach(area -> {
            addAreaIndicators((Compartment) area, color);
            addIndicator(remapper.getBlueprintCoords().get(area.hashCode()), dynMK, dynAdjust, icon);
        });
    }

    /**
     * @param area compartment of focus
     * @param c    default color of the indicator for the area type
     *             Configures the indicator according to the parameters
     *             and checks if the compartment is selected (if true - adjust indicator)
     */
    private void addAreaIndicators(Compartment area, Color c) {
        dynMK = MK_SIZE;
        dynAdjust = false;
        if (bpSelectionModel.getCompartment() != null && bpSelectionModel.getCompartment().equals(area)) {
            c = C_HGREEN;
            dynMK *= 1.45;
            dynAdjust = true;
        }
        if (cursorHover != null && cursorHover.equals(area)) {
            c = C_HOVER;
            dynMK *= 1.25;
            hover = true;
        }
        g2d.setColor(c);
    }

    /**
     * @param pos    position of the current area of focus
     * @param mk     indicator size
     * @param adjust true if the indicator's shape must be adjusted
     *               configures the shape of the indicators &
     * @param icon
     */
    private void addIndicator(Point2D.Double pos, int mk, boolean adjust, Image icon) {
        int coordX = (int) (pos.x + OFFSET);
        int coordY = (int) (pos.y + OFFSET);
        if (adjust) {
            RegularPolygon regPoly = new RegularPolygon(coordX, coordY, mk, 8, 0);
            g2d.fill(regPoly);
        } else {
            g2d.fill(new RoundPolygon(new RegularPolygon(coordX, coordY, mk, 4, 0), 5));
        }
        g2d.drawImage(icon, coordX - BlueprintIcons.WIDTH / 2, coordY - BlueprintIcons.HEIGHT / 2, this);
    }


    /**
     * Customize and add the Center of gravity indicator ;
     */
    private void addCoGindicator() {
        Point2D.Double pos = aircraft.getCenterOfG();
        int coordX = (int) ((pos.x) + OFFSET);
        int coordY = (int) ((pos.y) + OFFSET);
        g2d.setColor(C_CGMARK);
        g2d.fill(new RegularPolygon(coordX, coordY, MK_SIZE, 6, 0));
        g2d.setColor(Color.BLACK);
        g2d.fill(new RegularPolygon(coordX, coordY, MK_SIZE / 2, 10, 0));
    }

    private void addCabinIndicator() {
        double coordX = Remapper.BP_WIDTH - Remapper.BP_OFF;
        double coordY = Remapper.BP_HEIGHT - Remapper.BP_OFF;
        aircraft.getType().getCabin().forEach(cabin -> {
            addAreaIndicators(cabin, C_CABIN);
            addIndicator(new Point2D.Double(coordX, coordY), dynMK, dynAdjust, BlueprintIcons.passengerIcon);
        });
    }

    @Override
    public String getToolTipText() {
        return super.getToolTipText();
    }
}