package nl.rug.oop.flaps.aircraft_editor.view.pass_editor;

import nl.rug.oop.flaps.aircraft_editor.controller.configcore.Controller;
import nl.rug.oop.flaps.aircraft_editor.controller.execcomm.comm_relay.pass_relay.AddPassRelay;
import nl.rug.oop.flaps.aircraft_editor.controller.execcomm.comm_relay.pass_relay.RemAllPassRelay;
import nl.rug.oop.flaps.aircraft_editor.controller.execcomm.comm_relay.pass_relay.RemPassRelay;
import nl.rug.oop.flaps.aircraft_editor.model.EditorCore;
import nl.rug.oop.flaps.aircraft_editor.model.mediators.PassengerMediator;
import nl.rug.oop.flaps.aircraft_editor.view.MessagesDb;
import nl.rug.oop.flaps.aircraft_editor.view.generic_panels.GenericButtonPanel;
import nl.rug.oop.flaps.aircraft_editor.view.maineditor.main_panels.Logger;

import javax.swing.*;
import java.awt.*;

public class PassengerControlPanel extends GenericButtonPanel {

    private EditorCore editorCore;
    private PassengersFrame boardFrame;
    private Controller controller;
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
            if (!mediator.checkFields() && mediator.getInvalidFields().size() == 0) {
                controller.setSelectionCommand(new AddPassRelay(controller));
                controller.delegateCommand(mediator);
                execCommonUpdate();
            } else {
                logger.notifyErrMsg(MessagesDb.ADD_PASS_ERR);
            }
        }));
        add(newButton("Remove", () -> {
            if (check(boardFrame.getPassDb().getPassengerTable())) {
                controller.setSelectionCommand(new RemPassRelay(controller));
                controller.delegateCommand(mediator);
                execCommonUpdate();
            } else {
                logger.notifyErrMsg(MessagesDb.REM_PASS_ERR);
                controller.relayConfiguratorMsg(MessagesDb.REM_PASS_NEG);
            }
        }));
        add(newButton("RemoveAll", () -> {
            controller.setSelectionCommand(new RemAllPassRelay(controller));
            controller.delegateCommand(mediator);
            execCommonUpdate();
            logger.notifyConfirmMsg(MessagesDb.REMALL_PASS_POS);
        }));
    }

    private void execCommonUpdate() {
        boardFrame.getPassDb().getPassengerTable().getDatabaseTable().clearSelection();
    }

    private void initConfirmPanel() {
        confirmPanel = new JPanel(new FlowLayout());
        confirmPanel.setPreferredSize(new Dimension(100, 30));
        confirmPanel.add(newButton("Confirm", () -> {
        }));
    }
}
