package nl.rug.oop.gui.view;

import nl.rug.oop.gui.model.AppCore;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class SearchBarPanel extends JTextField {

    AppCore model;
    JTextField txtField = new JTextField("placeholder", 40);

    {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                //model.setSearchField();
            }
        });
    }

    public SearchBarPanel(AppCore model) {
        this.model = model;
        setPreferredSize(new Dimension(300, 25));
        addTextField(model);
    }

    private void addTextField(AppCore model) {
        add(txtField);
    }

}
