package nl.rug.oop.flaps.aircraft_editor.flightsim.sim_model;

import gov.nasa.worldwind.view.orbit.BasicOrbitView;
import gov.nasa.worldwindx.examples.ApplicationTemplate;
import nl.rug.oop.flaps.aircraft_editor.flightsim.sim_view.SimulatorWindow;

import java.awt.*;

public class FlightSimApplication extends ApplicationTemplate {

        protected ApplicationTemplate.AppPanel wwjPanel;
        protected BasicOrbitView orbitView;
        protected SimulatorWindow simulatorWindow;
        protected Dimension canvasSize = new Dimension(1024, 768);

    public FlightSimApplication() {
    }

    public ApplicationTemplate.AppPanel init() {
            this.wwjPanel = new ApplicationTemplate.AppPanel(this.canvasSize, true);
            this.orbitView = (BasicOrbitView) wwjPanel.getWwd().getView();
          //  orbitView.setSimApp(this);
            wwjPanel.setPreferredSize(canvasSize);
            return wwjPanel;
        }

        public AppPanel getWwjPanel() {
            return wwjPanel;
        }

}
