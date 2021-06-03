package nl.rug.oop.flaps.aircraft_editor.view;

import lombok.Getter;
import nl.rug.oop.flaps.aircraft_editor.model.EditorCore;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

@Getter
public class CargoButtonPanel extends JPanel {
    private EditorCore editorCore;
    private CargoSettings settings;
    private JButton add, remove, removeAll;
    protected static final String ADD_COM = "addcom", REM_COM = "remove", REMALL_COM = "remall";

    public CargoButtonPanel(EditorCore editorCore, CargoSettings settings) {
        this.editorCore = editorCore;
        this.settings = settings;
        setPreferredSize(new Dimension(20, 200));
        setLayout(new FlowLayout());
        addAllButtons();
    }

    private void addAllButtons() {
        this.add = new JButton("Add");
        add.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                settings.getCargoAmountPanel().setVisible(true);
                settings.setCommandRequest(ADD_COM);
            }
        });
        this.remove = new JButton("Remove");
        remove.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                settings.getCargoAmountPanel().setVisible(true);
                settings.setCommandRequest(REM_COM);
            }
        });
        this.removeAll = new JButton("Return All");
        removeAll.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                settings.getCargoAmountPanel().setVisible(true);
                settings.getCargoAmountPanel().getAmountField().setVisible(false);
                settings.setCommandRequest(REMALL_COM);
            }
        });
        add(add);
        add(remove);
        add(removeAll);
        add.setEnabled(false);
        remove.setEnabled(false);
        validate();
    }
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
