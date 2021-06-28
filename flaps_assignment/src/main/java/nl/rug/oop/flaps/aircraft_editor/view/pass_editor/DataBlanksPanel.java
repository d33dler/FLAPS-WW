package nl.rug.oop.flaps.aircraft_editor.view.pass_editor;

import nl.rug.oop.flaps.aircraft_editor.model.EditorCore;
import nl.rug.oop.flaps.aircraft_editor.model.mediators.PassengerMediator;
import nl.rug.oop.flaps.aircraft_editor.view.maineditor.main_panels.SpecialComponent;
import nl.rug.oop.flaps.simulation.model.aircraft.Aircraft;
import nl.rug.oop.flaps.simulation.model.loaders.FileUtils;
import nl.rug.oop.flaps.simulation.model.passengers.Passenger;
import nl.rug.oop.flaps.simulation.model.passengers.PassengerType;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.List;

public class DataBlanksPanel extends JPanel {
    private EditorCore core;
    private Aircraft aircraft;
    private PassengersFrame boardFrame;
    private List<Field> passFields = new ArrayList<>();
    private JComboBox<PassengerType> comboBox;
    private static final int WIDTH = 400, HEIGHT = 300, F_WIDTH = 400, F_HEIGHT = 30;
    private PassengerMediator mediator;
    private final String TITLE = "Register Passenger";

    public DataBlanksPanel(EditorCore core, Aircraft aircraft, PassengersFrame boardFrame) {
        this.core = core;
        this.aircraft = aircraft;
        this.boardFrame = boardFrame;
        this.mediator = boardFrame.getMediator();
        init();
    }

    private void init() {
        setSize(new Dimension(WIDTH, HEIGHT));
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setBorder(BorderFactory.createTitledBorder
                (BorderFactory.createEtchedBorder(1),
                        TITLE, TitledBorder.ABOVE_TOP, TitledBorder.TOP));
        this.passFields = FileUtils.getAllFieldsFiltered(Passenger.class);
        setUpBlanks();
        setUpComboBox();
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
        JTextField blank = createCustomTextField(field.getAnnotation(BlankField.class));
        blank.setColumns(13);
        mediator.getBlankSet().add(blank);
        blankSlot.add(fieldName);
        blankSlot.add(blank);
        return blankSlot;
    }

    private void setUpComboBox() {
        Vector<PassengerType> vector = new Vector<>(core.getSource().getPassengerTypes());
        this.comboBox = new JComboBox<>(vector);
        setBoxSelect();
        comboBox.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setBoxSelect();
            }
        });
        add(comboBox);
    }

    private JTextField createCustomTextField(BlankField bf) {
        if (bf != null) {
            JTextField blank = new JTextField();
            blank.setName(bf.id());
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
        return null;
    }

    private void setBoxSelect() {
        mediator.setSelectedType((PassengerType) comboBox.getSelectedItem());
    }


}
