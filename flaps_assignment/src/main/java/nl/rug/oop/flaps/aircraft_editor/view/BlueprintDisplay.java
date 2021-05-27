package nl.rug.oop.flaps.aircraft_editor.view;

import lombok.SneakyThrows;
import nl.rug.oop.flaps.simulation.model.aircraft.Aircraft;
import nl.rug.oop.flaps.simulation.model.aircraft.Compartment;
import nl.rug.oop.flaps.simulation.model.aircraft.CargoArea;
import nl.rug.oop.flaps.simulation.model.aircraft.FuelTank;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

public class BlueprintDisplay extends JPanel {
    private Image blueprintImage;
    private Image cachedBpImage;
    private Aircraft aircraft;
    public static final double MK_SIZE = 13;
    public static final int WIDTH = 500, HEIGHT = 500;


    @SneakyThrows
    public BlueprintDisplay(Aircraft aircraft) {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.aircraft = aircraft;
        this.blueprintImage =
                aircraft.getType().getBlueprintImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_SMOOTH);
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
        this.aircraft.getType().getCargoAreas().forEach(cargoArea -> addCargoIndicators(g2d, cargoArea));
        this.aircraft.getType().getFuelTanks().forEach(fuelArea -> addFuelTanksIndicators(g2d, fuelArea));
    }

    private void addCargoIndicators(Graphics2D g2d, CargoArea cargoArea) {
        Color c = Color.blue;
        g2d.setColor(c);
        addIndicator(g2d, cargoArea);
    }

    private void addFuelTanksIndicators(Graphics2D g2d, FuelTank fuelTank) {
        Color c = Color.magenta;
        g2d.setColor(c);
        addIndicator(g2d, fuelTank);
    }

    private void addIndicator(Graphics2D g2d, Compartment area) {
        Shape marker = new Rectangle2D.
                Double(area.getCoords().x, area.getCoords().y, MK_SIZE, MK_SIZE);
        g2d.fill(marker);
    }
}