package nl.rug.oop.flaps.aircraft_editor.view;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import nl.rug.oop.flaps.aircraft_editor.controller.DataTracker;
import nl.rug.oop.flaps.aircraft_editor.controller.actions.BlueprintSelectionListener;
import nl.rug.oop.flaps.aircraft_editor.controller.actions.CargoUnitsListener;
import nl.rug.oop.flaps.aircraft_editor.model.EditorCore;
import nl.rug.oop.flaps.simulation.model.aircraft.Aircraft;
import nl.rug.oop.flaps.simulation.model.aircraft.Compartment;
import nl.rug.oop.flaps.simulation.model.loaders.FileUtils;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@Setter
public class InfoPanel extends JPanel implements CargoUnitsListener, BlueprintSelectionListener {
    private EditorCore model;
    private Aircraft aircraft;
    private DataTracker dataTracker;
    private List<JLabel> dataFieldSet;
    private List<Field> fieldList;
    private JPanel infoStock;
    private static final String PANEL_TITLE = "AIRCRAFT DATA";
    private static final Color PANEL_BG = new Color(61, 77, 109, 116);

    public InfoPanel(EditorCore editorCore) {
        this.model = editorCore;
        this.aircraft = editorCore.getAircraft();
        this.dataTracker = editorCore.getDataTracker();
        this.dataFieldSet = new ArrayList<>();
        this.fieldList = new ArrayList<>();

        extractFields();
        editorCore.getCargoManipulationModel().addListener(this);
        editorCore.getSelectionModel().addListener(EditorCore.generalListenerID, this);
        setLayout(new FlowLayout());
        setPreferredSize(new Dimension(300, 550));
        init();
    }

    private void extractFields() {
        List<Field> list = Arrays.asList(dataTracker.getClass().getDeclaredFields());
        list.forEach(field -> {
            if (FileUtils.isFieldPrimitiveDeserializable(field)
                    && !Modifier.isStatic(field.getModifiers())) {
                fieldList.add(field);
            }
        });
    }

    @SneakyThrows
    protected void init() {
        this.infoStock = new JPanel();
        infoStock.setBackground(PANEL_BG);
        infoStock.setBorder
                (BorderFactory.createTitledBorder
                        (BorderFactory.createRaisedSoftBevelBorder(),
                                PANEL_TITLE, TitledBorder.CENTER, TitledBorder.ABOVE_TOP));
        infoStock.setLayout(new BoxLayout(infoStock, BoxLayout.PAGE_AXIS));
        infoStock.setPreferredSize(new Dimension(300, 600));
        for (Field field : fieldList) {
            field.setAccessible(true);
            if (FileUtils.isFieldPrimitiveDeserializable(field) && !Modifier.isStatic(field.getModifiers())) {

                JLabel data = new JLabel(StringUtils.capitalize(toNiceCase(field.getName()))
                        + " : " + field.get(dataTracker).toString());
                data.setBorder(BorderFactory.createEtchedBorder());
               // data.setFont(getFont().deriveFont(Font.));
                infoStock.add(new JLabel("\n"));
                dataFieldSet.add(data);
                infoStock.add(data);
            }
        }
        add(infoStock);
    }

    @SneakyThrows
    private void updateFields() {
        Iterator<Field> fieldIterator = fieldList.iterator();
        for (JLabel label : dataFieldSet) {
            Field field = fieldIterator.next();
            label.setText(StringUtils.capitalize(toNiceCase(field.getName()))
                    + " : " + field.get(dataTracker).toString());
        }
    }

    @Override
    public void compartmentSelected(Compartment area) {
        notifyChange(aircraft);
    }

    @Override
    public void notifyChange(Aircraft aircraft) {
        updateFields();
        repaint();
    }

    protected static String toNiceCase(String camelCase) {
        Matcher m = Pattern.compile("(?<=[a-z])[A-Z]").matcher(camelCase);
        return m.replaceAll(match -> " " + match.group().toLowerCase());
    }
}
