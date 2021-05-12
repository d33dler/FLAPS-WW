package nl.rug.oop.gui.view;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {
    public MainPanel() {
        init();
    }

    public void init() {
        setPreferredSize(new Dimension(500, 50));
        this.setLayout(new FlowLayout());
        addTextFields();
        addButton();
        this.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        validate();
    }


    private void addTextFields() {
        JTextField txtField = new JTextField("sdf", 20);
        add(txtField);
    }

    private void addButton() {
        JButton button = new JButton("Enter");
        add(button);
    }
}
