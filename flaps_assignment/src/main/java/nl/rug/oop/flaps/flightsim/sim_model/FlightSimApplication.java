package nl.rug.oop.flaps.flightsim.sim_model;

import gov.nasa.worldwind.WorldWindow;
import gov.nasa.worldwind.view.firstperson.BasicFlyView;
import gov.nasa.worldwind.view.orbit.BasicOrbitView;
import gov.nasa.worldwindx.examples.ApplicationTemplate;
import lombok.Getter;
import lombok.Setter;
import nl.rug.oop.flaps.flightsim.sim_view.SimulatorWindow;

import java.awt.*;

/**
 * WorldWind API extending component class which initializes WorldWindow
 * (the generated WorldWind world and it's functions)
 */
@Getter
public class FlightSimApplication extends ApplicationTemplate {
    @Setter
    private FlightSimCore core;
    protected ApplicationTemplate.AppPanel worldPanel;
    protected BasicOrbitView orbitView;
    protected BasicFlyView basicFlyView = new BasicFlyView();
    protected SimulatorWindow simulatorWindow;
    protected WorldWindow worldWindow;
    protected Dimension canvasSize = new Dimension(1700, 1000);

    public FlightSimApplication() {
    }

    public ApplicationTemplate.AppPanel init(SimulatorWindow simulatorWindow) {
        this.simulatorWindow = simulatorWindow;
        this.worldPanel = new ApplicationTemplate.AppPanel(this.canvasSize, true);
        this.orbitView = (BasicOrbitView) worldPanel.getWwd().getView();
        worldPanel.setPreferredSize(canvasSize);
        this.worldWindow = worldPanel.getWwd();
        return worldPanel;
    }

    public AppPanel getWorldPanel() {
        return worldPanel;
    }

}
