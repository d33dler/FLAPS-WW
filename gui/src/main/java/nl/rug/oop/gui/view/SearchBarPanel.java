package nl.rug.oop.gui.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class SearchBar extends JTextField {

    public SearchBar() {
        setPreferredSize(new Dimension(300,25));
        addTextField();
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                // I am called whenever the user types a new character
                // doSomething();
            }
        });
    }
    private void addTextField() {
        JTextField txtField = new JTextField("", 40);

        add(txtField);
    }
}
