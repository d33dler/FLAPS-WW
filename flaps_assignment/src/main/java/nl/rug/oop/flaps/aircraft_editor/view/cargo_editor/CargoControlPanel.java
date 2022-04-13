package nl.rug.oop.flaps.aircraft_editor.view.cargo_editor;

import lombok.Getter;
import nl.rug.oop.flaps.aircraft_editor.controller.configcore.Controller;
import nl.rug.oop.flaps.aircraft_editor.controller.execcomm.comm_relay.cargo_relay.AddCargoRelay;
import nl.rug.oop.flaps.aircraft_editor.controller.execcomm.comm_relay.cargo_relay.RemAllCargoRelay;
import nl.rug.oop.flaps.aircraft_editor.controller.execcomm.comm_relay.cargo_relay.RemoveCargoRelay;
import nl.rug.oop.flaps.aircraft_editor.model.EditorCore;
import nl.rug.oop.flaps.aircraft_editor.view.MessagesDb;
import nl.rug.oop.flaps.aircraft_editor.view.generic_panels.GenericButtonPanel;
import nl.rug.oop.flaps.aircraft_editor.view.maineditor.b_print.BlueprintIcons;
import nl.rug.oop.flaps.aircraft_editor.view.maineditor.main_panels.Logger;
import nl.rug.oop.flaps.aircraft_editor.view.maineditor.main_panels.SpecialComponent;
import nl.rug.oop.flaps.simulation.model.cargo.CargoType;

import javax.swing.*;
import javax.swing.border.TitledBorder;
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
    private JLabel cargoImg;
    public static final String ICON_TITLE = "Selected:";
    public static final Color ICON_C = new Color(17, 255, 255, 34);

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
        addImg();
    }

    private void addButtonControls() {
        add(newButton("Add", () -> {
            if (check(warehouse)) {
                controller.setSelectionCommand(new AddCargoRelay(controller));
                settings.getCargoAmountPanel().enable();
            } else {
                logger.notifyErrMsg(MessagesDb.ADD_CARGO_ERR);
            }
        }));
        add(newButton("Remove", () -> {
            if (check(aircraft)) {
                controller.setSelectionCommand(new RemoveCargoRelay(controller));
                settings.getCargoAmountPanel().enable();
            } else {
                logger.notifyErrMsg(MessagesDb.REM_CARGO_ERR);
            }
        }));
        add(newButton("RemoveAll", () -> {
            controller.setSelectionCommand(new RemAllCargoRelay(controller));
            settings.getCargoAmountPanel().question();
        }));
    }

    private void addImg() {
        add(new SpecialComponent(cargoImg = new JLabel(
                new ImageIcon(BlueprintIcons.cargoIcon
                        .getScaledInstance(75, 75, Image.SCALE_SMOOTH)))));
        cargoImg.setBorder(BorderFactory.createTitledBorder
                (BorderFactory.createEtchedBorder(1),
                        ICON_TITLE, TitledBorder.ABOVE_TOP, TitledBorder.TOP));
        cargoImg.setBackground(ICON_C);
    }

    public void setCargoImg(CargoType unit) {
        this.cargoImg.setIcon(new ImageIcon(unit.getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH)));
        revalidate();
        repaint();
    }

}
