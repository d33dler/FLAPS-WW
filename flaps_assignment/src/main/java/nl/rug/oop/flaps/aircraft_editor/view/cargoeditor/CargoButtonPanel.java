package nl.rug.oop.flaps.aircraft_editor.view.cargoeditor;

import lombok.Getter;
import nl.rug.oop.flaps.aircraft_editor.controller.configcore.Configurator;
import nl.rug.oop.flaps.aircraft_editor.controller.execcomm.commrelay.AddCargoRelay;
import nl.rug.oop.flaps.aircraft_editor.controller.execcomm.commrelay.RemoveAllRelay;
import nl.rug.oop.flaps.aircraft_editor.controller.execcomm.commrelay.RemoveCargoRelay;
import nl.rug.oop.flaps.aircraft_editor.model.EditorCore;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * CargoButtonPanel - minimalistic panel containing the buttons for all cargo trading operations;
 */
@Getter
public class CargoButtonPanel extends JPanel {
    private EditorCore editorCore;
    private CargoTradeFrame settings;
    private Configurator configurator;
    private JButton add, remove, removeAll;

    public CargoButtonPanel(EditorCore editorCore, CargoTradeFrame settings) {
        this.editorCore = editorCore;
        this.settings = settings;
        this.configurator = editorCore.getConfigurator();
        setPreferredSize(new Dimension(20, 200));
        setLayout(new FlowLayout());
        addAllButtons();
    }

    /**
     * Initializes,adds an action listener and sets the appropriate abstract action for each button;
     */
    private void addAllButtons() {
        this.add = new JButton("Add");
        add.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                settings.getCargoAmountPanel().enableFull();
                settings.setSelectionCommand(new AddCargoRelay(configurator));
            }
        });
        this.remove = new JButton("Remove");
        remove.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                settings.getCargoAmountPanel().enableFull();
                settings.setSelectionCommand(new RemoveCargoRelay(configurator));
            }
        });
        this.removeAll = new JButton("Return All");
        removeAll.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                settings.getCargoAmountPanel().enablePartial();
                settings.setSelectionCommand(new RemoveAllRelay(configurator));
            }
        });
        add(add);
        add(remove);
        add(removeAll);
        add.setEnabled(false);
        remove.setEnabled(false);
        validate();
    }

    /**
     * All methods below are switches to limit users ability to one type of operation depending on the
     * selected cargoType/Freight and command;
     */
    protected void switchOne() {
        add.setEnabled(true);
        remove.setEnabled(false);
    }
    protected void switchTwo() {
        add.setEnabled(false);
        remove.setEnabled(true);
    }
    protected void disableButtons() {
        add.setEnabled(false);
        remove.setEnabled(false);
    }
}
