package nl.rug.oop.flaps.aircraft_editor.view.pass_editor;

import lombok.Getter;
import lombok.Setter;
import nl.rug.oop.flaps.aircraft_editor.model.EditorCore;
import nl.rug.oop.flaps.aircraft_editor.view.maineditor.EditorWindows;
import nl.rug.oop.flaps.aircraft_editor.view.maineditor.area_panels.PassengerPanel;
import nl.rug.oop.flaps.simulation.model.aircraft.Aircraft;
import nl.rug.oop.flaps.simulation.model.aircraft.areas.Cabin;
import nl.rug.oop.flaps.simulation.model.passengers.Passenger;

import java.awt.*;
import java.awt.event.WindowEvent;
import java.util.HashMap;

@Getter
@Setter
public class PassengerBoardFrame extends EditorWindows {

    private EditorCore editorCore;
    private Aircraft aircraft;
    private Cabin cabinArea;
    private PassengerPanel passengerPanel;
    private DataBlanksPanel blanksPanel;
    private PassengerTable passDb;
    private HashMap<String, Passenger> passengerHashMap = new HashMap<>();
    private static final int WIDTH = 800, HEIGHT = 700;

    public PassengerBoardFrame(EditorCore editorCore, Cabin cabinArea, PassengerPanel passengerPanel) {
        setTitle("Passenger Boarding Register");
        this.editorCore = editorCore;
        this.cabinArea = cabinArea;
        this.passengerPanel = passengerPanel;
        this.aircraft = editorCore.getAircraft();
        init();
    }

    private void init() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setLayout(new FlowLayout());
        this.blanksPanel = new DataBlanksPanel(aircraft, this);
        this.passDb = new PassengerTable(editorCore,this);
        add(blanksPanel);
        add(passDb);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
        pack();
    }

    @Override
    public void windowClosed(WindowEvent e) {
        editorCore.getEditorFrame().getSettingsPanel().setPassengerBoardFrame(null);
        passengerPanel.getEmbarque().setEnabled(true);
    }
}
