package nl.rug.oop.flaps.simulation.view;

import lombok.Getter;
import nl.rug.oop.flaps.simulation.model.world.World;
import nl.rug.oop.flaps.simulation.view.panels.WorldPanel;
import nl.rug.oop.flaps.simulation.view.panels.aircraft.AircraftPanel;
import nl.rug.oop.flaps.simulation.view.panels.airport.AirportPanel;

import javax.swing.*;
import java.awt.*;

/**
 * The frame of the main application
 *
 * @author T.O.W.E.R.
 */
public class FlapsFrame extends JFrame {

    private static final int WIDTH = 1620;
    private static final int HEIGHT = 920;
    @Getter
    private WorldPanel worldPanel;
    @Getter
    private AircraftPanel aircraftPanel;
    private AirportPanel airportPanel;
    public FlapsFrame(World world) {
        super("Flight Logistics Aviation Planning Simulation");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        world.getDimensions().setMapHeight(HEIGHT/2);
        world.getDimensions().setMapWidth(WIDTH/2);
        initPanels(world);
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initPanels(World world) {

        this.worldPanel = new WorldPanel(world);
        worldPanel.setPreferredSize(new Dimension(WIDTH/2, HEIGHT/2));

        this.aircraftPanel = new AircraftPanel(world);
        this.airportPanel = new AirportPanel(world);
        airportPanel.setPreferredSize(new Dimension(WIDTH/2, HEIGHT/2));

        JSplitPane worldPlaneSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, worldPanel, aircraftPanel);
        worldPlaneSplit.setDividerLocation(HEIGHT/2);
        worldPlaneSplit.setDividerSize(0);

        JSplitPane leftRightSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, airportPanel, worldPlaneSplit);
        leftRightSplit.setDividerLocation(WIDTH/2);
        leftRightSplit.setDividerSize(0);
        add(leftRightSplit);
    }
}
