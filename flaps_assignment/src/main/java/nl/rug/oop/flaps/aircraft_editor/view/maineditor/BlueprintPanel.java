package nl.rug.oop.flaps.aircraft_editor.view.maineditor;

import lombok.Getter;
import lombok.SneakyThrows;
import nl.rug.oop.flaps.aircraft_editor.controller.actions.AreaSelectionAction;
import nl.rug.oop.flaps.aircraft_editor.model.BlueprintSelectionModel;
import nl.rug.oop.flaps.aircraft_editor.model.EditorCore;
import nl.rug.oop.flaps.aircraft_editor.model.Remapper;
import nl.rug.oop.flaps.simulation.model.aircraft.Aircraft;
import nl.rug.oop.flaps.simulation.model.world.WorldSelectionModelListener;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

/**
 * BlueprintPanel class - primary Panel for the blueprint graphical display;
 * It encapsulates the blueprintDisplay;
 */
@Getter
public class BlueprintPanel extends JPanel implements WorldSelectionModelListener {

    private final EditorCore model;
    private BlueprintDisplay blueprintDisplay;
    private final BlueprintSelectionModel selectionModel;
    private final Aircraft aircraft;
    public static String BP_TITLE;

    public BlueprintPanel(EditorCore model, BlueprintSelectionModel selectionModel, Aircraft aircraft) {
        this.model = model;
        this.selectionModel = selectionModel;
        this.aircraft = aircraft;
        BP_TITLE = this.aircraft.getType().getName() + " Blueprint";
        AreaSelectionAction controller = new AreaSelectionAction(model);
        addMouseListener(controller);
        addMouseMotionListener(controller);
        init();
    }

    /**
     * set border for the panel, size and initialize the Blueprint Display and add it as special component(transparent
     * JComponents fix *check SpecialComponent class)
     */
    @SneakyThrows
    private void init() {
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(1),
                BP_TITLE, TitledBorder.CENTER, TitledBorder.ABOVE_TOP));
        this.setPreferredSize(new Dimension((int) Remapper.BP_WIDTH, (int) (Remapper.BP_HEIGHT)));
        this.blueprintDisplay = new BlueprintDisplay(this.selectionModel, this.aircraft, this);
        add(new SpecialComponent(blueprintDisplay), BorderLayout.WEST);
    }
}
