package nl.rug.oop.flaps.aircraft_editor.controller.actions;
import nl.rug.oop.flaps.aircraft_editor.model.EditorCore;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

public class AreaSelectionModel extends MouseAdapter {

    EditorCore editorCore;

    public AreaSelectionModel(EditorCore editorCore) {
        this.editorCore = editorCore;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        SwingUtilities.invokeLater(() -> {
            Point2D.Double ePos = new Point2D.Double(e.getPoint().x,e.getPoint().y);
            var area = this.editorCore.extractApproxArea(ePos);
            area.ifPresent(a -> {
                this.editorCore.getSelectionModel().setSelectedCompartment(a.getCompartmentId(), a);
                System.out.println(area.get().getName());
            });
        });
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

}
