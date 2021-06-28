package nl.rug.oop.flaps.aircraft_editor.view.cargo_editor;

import lombok.Getter;
import nl.rug.oop.flaps.aircraft_editor.controller.configcore.Controller;
import nl.rug.oop.flaps.aircraft_editor.controller.execcomm.comm_relay.cargo_relay.AddCargoRelay;
import nl.rug.oop.flaps.aircraft_editor.controller.execcomm.comm_relay.cargo_relay.RemoveAllRelay;
import nl.rug.oop.flaps.aircraft_editor.controller.execcomm.comm_relay.cargo_relay.RemoveCargoRelay;
import nl.rug.oop.flaps.aircraft_editor.model.EditorCore;
import nl.rug.oop.flaps.aircraft_editor.view.MessagesDb;
import nl.rug.oop.flaps.aircraft_editor.view.generic_panels.GenericButtonPanel;
import nl.rug.oop.flaps.aircraft_editor.view.maineditor.main_panels.Logger;

import java.awt.*;

/**
 * CargoButtonPanel - minimalistic panel containing the buttons for all cargo trading operations;
 */
@Getter
public class CargoControlPanel extends GenericButtonPanel {
    private EditorCore editorCore;
    private CargoFrame settings;
    private Controller controller;
    private DatabaseTablePanel<?> warehouse, aircraft;
    private Logger logger;

    public CargoControlPanel(EditorCore editorCore, DatabaseTablePanel<?> warehouse, DatabaseTablePanel<?> aircraft, CargoFrame settings) {
        this.editorCore = editorCore;
        this.settings = settings;
        this.controller = editorCore.getController();
        this.warehouse = warehouse;
        this.aircraft = aircraft;
        this.logger = controller.getLogger();
        setPreferredSize(new Dimension(20, 200));
        setLayout(new FlowLayout());
        addButtonControls();
    }

    private void addButtonControls() {
        add(newButton("Add", () -> {
            if (check(warehouse)) {
                controller.setSelectionCommand(new AddCargoRelay(controller));
                settings.getCargoAmountPanel().enable();
            } else {
                logger.notifyErrMsg(MessagesDb.ADD_ERR);
            }
        }));
        add(newButton("Remove", () -> {
            if (check(aircraft)) {
                controller.setSelectionCommand(new RemoveCargoRelay(controller));
                settings.getCargoAmountPanel().enable();
            } else {
                logger.notifyErrMsg(MessagesDb.REM_ERR);
            }
        }));
        add(newButton("RemoveAll", () -> {
            controller.setSelectionCommand(new RemoveAllRelay(controller));
            settings.getCargoAmountPanel().question();
        }));
    }

}
