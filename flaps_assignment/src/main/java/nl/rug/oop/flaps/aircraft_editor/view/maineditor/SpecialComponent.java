package nl.rug.oop.flaps.aircraft_editor.view.maineditor;

import javax.swing.*;
import java.awt.*;

/**
 * Minimalistic class used for Swing components with transparent background
 * to fix the artifacts created by repaint()
 */
public class SpecialComponent extends JComponent {
    private JComponent component;

    public SpecialComponent(JComponent component) {
        this.component = component;
        setLayout(new BorderLayout());
        setOpaque(false);
        component.setOpaque(false);
        add(component);
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(component.getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());
    }
}
