package nl.rug.oop.flaps.aircraft_editor.view.pass_editor;

import nl.rug.oop.flaps.aircraft_editor.controller.configcore.Controller;
import nl.rug.oop.flaps.aircraft_editor.controller.execcomm.comm_relay.pass_relay.AddPassRelay;
import nl.rug.oop.flaps.aircraft_editor.controller.execcomm.comm_relay.pass_relay.RemPassRelay;
import nl.rug.oop.flaps.aircraft_editor.model.EditorCore;
import nl.rug.oop.flaps.aircraft_editor.model.mediators.PassengerMediator;
import nl.rug.oop.flaps.aircraft_editor.view.MessagesDb;
import nl.rug.oop.flaps.aircraft_editor.view.cargo_editor.DatabaseTablePanel;
import nl.rug.oop.flaps.aircraft_editor.view.generic_panels.GenericButtonPanel;
import nl.rug.oop.flaps.aircraft_editor.view.maineditor.main_panels.Logger;
import nl.rug.oop.flaps.simulation.model.passengers.Passenger;

import javax.swing.*;
import java.awt.*;

public class PassengerControlPanel extends GenericButtonPanel {

    private EditorCore editorCore;
    private PassengersFrame boardFrame;
    private Controller controller;
    private DatabaseTablePanel<Passenger> passengerTable;
    private DataBlanksPanel panel;
    private JPanel confirmPanel;
    private PassengerMediator mediator;
    private Logger logger;
    public PassengerControlPanel(EditorCore editorCore, PassengersFrame boardFrame) {
        this.editorCore = editorCore;
        this.boardFrame = boardFrame;
        this.controller = editorCore.getController();
        this.mediator = boardFrame.getMediator();
        this.logger = controller.getLogger();
        init();
        initConfirmPanel();
    }

    private void init() {
        add(newButton("Add", () -> {
            if (!mediator.checkFields()) {
                 controller.setSelectionCommand(new AddPassRelay(controller));
                 controller.delegateCommand(mediator);
            } else {
                    logger.notifyErrMsg(MessagesDb.ADD_ERR);
            }
        }));
        add(newButton("Remove", () -> {
            if (check(passengerTable)) {
                controller.setSelectionCommand(new RemPassRelay());
                controller.delegateCommand(mediator);
            } else {
                 logger.notifyErrMsg(MessagesDb.REM_ERR);
            }
        }));
        add(newButton("RemoveAll", () -> {

            //TODO confirm JPane
            // settings.setSelectionCommand(new RemoveAllRelay(controller));
        }));

    }

    private void initConfirmPanel() {
        confirmPanel = new JPanel(new FlowLayout());
        confirmPanel.setPreferredSize(new Dimension(100, 30));
        confirmPanel.add(newButton("Confirm", () -> {
        }));
    }
}
