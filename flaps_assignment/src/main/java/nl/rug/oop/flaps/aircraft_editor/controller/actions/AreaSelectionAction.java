package nl.rug.oop.flaps.aircraft_editor.controller.actions;

import nl.rug.oop.flaps.aircraft_editor.model.EditorCore;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

/**
 * AreaSelectionAction class - identifies the selection of compartments based on their relative distance from
 * the cursor click's coordinates;
 */
public class AreaSelectionAction extends MouseAdapter {

    EditorCore editorCore;

    public AreaSelectionAction(EditorCore editorCore) {
        this.editorCore = editorCore;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        SwingUtilities.invokeLater(() -> {
            Point2D.Double ePos = new Point2D.Double(e.getPoint().x, e.getPoint().y);
            var area = this.editorCore.getBpSelectionModel().extractApproxArea(ePos);
            area.ifPresent(a -> {
                this.editorCore.getBpSelectionModel().fireSelectedAreaUpdate(a.getCompartmentId(), a);
            });
        });
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        SwingUtilities.invokeLater(() -> {
            Point2D.Double ePos = new Point2D.Double(e.getPoint().x, e.getPoint().y);
            var area = this.editorCore.getBpSelectionModel().extractApproxArea(ePos);
            area.ifPresent(a -> {
                this.editorCore.getDataTracker().getDisplay().setCursorHover(a);
                this.editorCore.getDataTracker().getDisplay().repaint();
            });
        });
    }

}
