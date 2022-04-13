package nl.rug.oop.flaps.aircraft_editor.view.generic_panels;

import nl.rug.oop.flaps.aircraft_editor.view.cargo_editor.DatabaseTablePanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;


public abstract class GenericButtonPanel extends JPanel {

    protected Map<String, JButton> buttonList = new HashMap<>();
    /**
     * After click side-effects
     */
    public static final int SET_OFF = 0, SET_ON = 1;
    private boolean after_click = true;

    protected JButton newButton(String text, Runnable r) {
        JButton button = new JButton(text);
        button.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                r.run();
                if (!after_click) {
                    button.setEnabled(false);
                }
            }
        });
        buttonList.put(text,button);   //list for special purposes
        return button;
    }

    protected JButton newButton(String text, Runnable r, boolean b) {
        JButton bt = newButton(text, r);
        bt.setEnabled(b);
        return bt;
    }

    protected JButton newButton(String text, Runnable r, int c) {
        after_click = c == 1;
        JButton bt = newButton(text, r);
        return bt;
    }

    protected JButton newButton(String text, Runnable r, boolean b, int c) {
        JButton bt = newButton(text, r, c);
        bt.setEnabled(b);
        return bt;
    }

    protected JButton newButton(String text, Runnable r, String s) {
        JButton bt = newButton(text, r);
        bt.setToolTipText(s);
        return bt;

    }


    protected boolean check(DatabaseTablePanel<?> panel) {
        return !panel.getDatabaseTable().getSelectionModel().isSelectionEmpty();
    }

}
