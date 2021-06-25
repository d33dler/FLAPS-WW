package nl.rug.oop.flaps.aircraft_editor.view.pass_editor;

import nl.rug.oop.flaps.aircraft_editor.view.maineditor.main_panels.SpecialComponent;
import nl.rug.oop.flaps.simulation.model.aircraft.Aircraft;
import nl.rug.oop.flaps.simulation.model.loaders.FileUtils;
import nl.rug.oop.flaps.simulation.model.passengers.Passenger;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class DataBlanksPanel extends JPanel {
    private Aircraft aircraft;
    private PassengerBoardFrame boardFrame;
    private List<JTextField> blankFieldList = new ArrayList<>();
    private List<Field> passFields = new ArrayList<>();

    private static final int WIDTH = 400, HEIGHT = 300, F_WIDTH = 400, F_HEIGHT = 30;

    public DataBlanksPanel(Aircraft aircraft, PassengerBoardFrame boardFrame) {
        this.aircraft = aircraft;
        this.boardFrame = boardFrame;
        init();
    }

    private void init() {
        setSize(new Dimension(WIDTH, HEIGHT));
         setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        this.passFields = FileUtils.extractAllFieldsFiltered(Passenger.class);
        setUpBlanks();
    }

    private void setUpBlanks() {
        for (Field field : passFields) {
            field.setAccessible(true);
            if (FileUtils.isFieldPrimitiveDeserializable(field)
                    && !Modifier.isStatic(field.getModifiers())) {
                JPanel fieldPanel = createBlankSlot(field);
                add(new SpecialComponent(fieldPanel));
            }
        }
    }

    private JPanel createBlankSlot(Field field) {
        JPanel blankSlot = new JPanel();
        blankSlot.setLayout(new GridLayout(1, 2));
        JLabel fieldName = FileUtils.getFormattedName(field);
        JTextField blank = createCustomTextField(field.getName());
        blank.setColumns(10);
        blankFieldList.add(blank);
        blankSlot.add(fieldName);
        blankSlot.add(blank);
        return blankSlot;
    }

    private JTextField createCustomTextField(String name) {
       JTextField blank = new JTextField();
       blank.setName(name);
       blank.getDocument().addDocumentListener(new DocumentListener() {

           @Override
           public void insertUpdate(DocumentEvent e) {

           }

           @Override
           public void removeUpdate(DocumentEvent e) {

           }

           @Override
           public void changedUpdate(DocumentEvent e) {

           }
       });

       return blank;
    }
}
