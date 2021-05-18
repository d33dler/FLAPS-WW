package nl.rug.oop.gui.view;
import nl.rug.oop.gui.model.AppCore;
import javax.swing.*;
import java.awt.*;

public class SearchBarPanel extends JTextField {
    AppCore model;
    JTextField txtField = new JTextField("", 40);

    public SearchBarPanel(AppCore model) {
        this.model = model;
        setPreferredSize(new Dimension(300, 25));
        add(txtField);
    }
}
