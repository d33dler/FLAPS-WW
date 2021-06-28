package nl.rug.oop.flaps.aircraft_editor.view.generic_panels;

import nl.rug.oop.flaps.aircraft_editor.view.cargo_editor.DatabaseTablePanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;


public abstract class GenericButtonPanel extends JPanel {

    protected List<JButton> buttonList = new ArrayList<>();

    protected JButton newButton(String text, Runnable r) {
        JButton button = new JButton(text);
        button.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                r.run();
            }
        });
        buttonList.add(button);   //list for special purposes
        return button;
    }
    protected JButton newButton(String text, Runnable r, boolean b) {
        JButton bt = newButton(text,r);
        bt.setEnabled(b);
        return bt;
    }
    //TODO generify JTextfield creation

    protected boolean check(DatabaseTablePanel<?> panel) {
        return !panel.getDatabaseTable().getSelectionModel().isSelectionEmpty();
    }

}
