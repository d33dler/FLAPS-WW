package nl.rug.oop.flaps.aircraft_editor.view.pass_editor;

import lombok.Getter;
import lombok.Setter;
import nl.rug.oop.flaps.aircraft_editor.model.EditorCore;
import nl.rug.oop.flaps.aircraft_editor.model.mediators.PassengerMediator;
import nl.rug.oop.flaps.aircraft_editor.view.maineditor.EditorWindows;
import nl.rug.oop.flaps.aircraft_editor.view.maineditor.area_panels.PassengerPanel;
import nl.rug.oop.flaps.simulation.model.aircraft.Aircraft;
import nl.rug.oop.flaps.simulation.model.aircraft.areas.Cabin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;

@Getter
@Setter
public class PassengersFrame extends EditorWindows  {


    private EditorCore editorCore;
    private PassengerMediator mediator = new PassengerMediator();
    private Aircraft aircraft;
    private Cabin cabinArea;
    private PassengerPanel passengerPanel;
    private DataBlanksPanel blanksPanel;
    private PassengerTable passDb;
    private static final int WIDTH = 900, HEIGHT = 700;

    public PassengersFrame(EditorCore editorCore, Cabin cabinArea, PassengerPanel passengerPanel) {
        setTitle("Passenger Boarding Register");
        this.editorCore = editorCore;
        this.cabinArea = cabinArea;
        this.passengerPanel = passengerPanel;
        this.aircraft = editorCore.getAircraft();
        init();
    }

    private void init() {
        addWindowListener(this);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setLayout(new FlowLayout());
        this.blanksPanel = new DataBlanksPanel(editorCore,aircraft, this);
        this.passDb = new PassengerTable(editorCore,this);
        add(blanksPanel);
        add(passDb);
        add(new PassengerControlPanel(editorCore,this));
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
        pack();
    }

    @Override
    public void windowClosed(WindowEvent e) {
        editorCore.getEditorFrame().getSettingsPanel().setPassengersFrame(null);
        passengerPanel.getEmbarque().setEnabled(true);
    }

    @Override
    public void windowClosing(WindowEvent e) {
        this.windowClosed(e);
    }

}
