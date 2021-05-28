package nl.rug.oop.flaps.aircraft_editor.view;
import lombok.Getter;
import lombok.SneakyThrows;
import nl.rug.oop.flaps.aircraft_editor.controller.actions.AreaSelectionListener;
import nl.rug.oop.flaps.aircraft_editor.model.BlueprintSelectionModel;
import nl.rug.oop.flaps.aircraft_editor.model.EditorCore;
import nl.rug.oop.flaps.simulation.model.aircraft.Aircraft;
import nl.rug.oop.flaps.simulation.model.world.WorldSelectionModelListener;
import javax.swing.*;
import java.awt.*;

@Getter
public class BlueprintPanel extends JPanel implements WorldSelectionModelListener {

    private final EditorCore model;
    private BlueprintDisplay blueprintDisplay;
    private BlueprintSelectionModel selectionModel;
    private Aircraft aircraft;
    private static final int WIDTH = 650, HEIGHT = 650;

    public BlueprintPanel(EditorCore model, BlueprintSelectionModel selectionModel, Aircraft aircraft) {
        this.model = model;
        this.selectionModel = selectionModel;
        this.aircraft = aircraft;

        AreaSelectionListener controller = new AreaSelectionListener(model);
        addMouseListener(controller);
        init();
    }

    @SneakyThrows
    private void init() {
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBorder(BorderFactory.createCompoundBorder());
        this.blueprintDisplay = new BlueprintDisplay(this.selectionModel, this.aircraft, this);
        add(blueprintDisplay, BorderLayout.CENTER);
    }


}
