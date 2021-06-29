package nl.rug.oop.flaps.aircraft_editor.view.pass_editor;

import nl.rug.oop.flaps.aircraft_editor.model.EditorCore;
import nl.rug.oop.flaps.aircraft_editor.model.mediators.PassengerMediator;
import nl.rug.oop.flaps.aircraft_editor.view.generic_panels.GenericButtonPanel;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class DataBlanksPanel extends GenericButtonPanel {
    private EditorCore core;
    private Aircraft aircraft;
    private PassengersFrame boardFrame;
    private List<Field> passFields = new ArrayList<>();
    private JComboBox<PassengerType> comboBox;
    private PassengerMediator mediator;

    private static final String TITLE = "Register Passenger", INV_IN = "×";
    private static final int WIDTH = 500, HEIGHT = 500, F_WIDTH = 130, F_HEIGHT = 40, VAL_WDT = 400;
    public static final Color
            R_BG = new Color(250, 74, 74, 81),
            OK_BG = new Color(146, 250, 76, 55);

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
        setUpButtons();
    }


    private void setUpBlanks() {
        for (Field field : passFields) {
            field.setAccessible(true);
            if (field.getAnnotation(BlankField.class) != null) {
                JPanel fieldPanel = createBlankSlot(field);
                add(new SpecialComponent(fieldPanel));
            }
        }
    }

    private JPanel createBlankSlot(Field field) {
        JPanel blankSlot = new JPanel();
        blankSlot.setLayout(new BoxLayout(blankSlot, BoxLayout.LINE_AXIS));
        JLabel fieldName = FileUtils.getFormattedName(field);
        fieldName.setHorizontalAlignment(SwingConstants.LEFT);
        fieldName.setPreferredSize(new Dimension(F_WIDTH, F_HEIGHT));
        JTextField blank = createCustomTextField(field.getAnnotation(BlankField.class));
        JPanel txtFieldHolder = new JPanel(new BorderLayout());
        blank.setPreferredSize(new Dimension(VAL_WDT, F_HEIGHT));
        txtFieldHolder.add(new SpecialComponent(blank), BorderLayout.CENTER);
        mediator.getBlankSet().add(blank);
        customizeBlankSlot(blankSlot, txtFieldHolder, fieldName);
        return blankSlot;
    }
    private void setUpButtons() {
        JPanel btPanel = new JPanel(new FlowLayout());
        btPanel.add(newButton("Clear fields", () -> {
            mediator.getBlankSet().forEach(field -> {
                field.setText("");
            });
        }));
        add(btPanel);
    }

    private void customizeBlankSlot(JPanel blankSlot, JPanel txtFieldHolder, JLabel fieldName) {
        blankSlot.add(fieldName, LEFT_ALIGNMENT);
        blankSlot.add(txtFieldHolder);
        blankSlot.setBorder(BorderFactory.createEtchedBorder());
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
                public void checkConstraints() {
                    if (!blank.getText().isBlank()
                            && fieldCheck(blank, bf)
                            && bf.min() != -1) {
                        boundsCheck(blank, bf);
                    }
                    if (blank.getText().isBlank()) invalidate(blank);
                }

                @Override
                public void insertUpdate(DocumentEvent e) {
                    checkConstraints();
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    checkConstraints();
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    checkConstraints();
                }
            });
            return blank;
        }
        return null;
    }

    private boolean fieldCheck(JTextField field, BlankField bf) {
        if (!field.getText().matches(bf.pattern())) {
            invalidate(field);
            return false;
        } else {
            validate(field);
            return true;
        }
    }

    private void boundsCheck(JTextField field, BlankField bf) {
        int val = Integer.parseInt(field.getText());
        if (val < bf.min() || val > bf.max()) invalidate(field);
        else validate(field);
    }

    private void setBorder(JTextField field, Color bg) {
        setBorder(field, "✔", bg);
    }

    private void setBorder(JTextField field, String title, Color bg) {
        field.setBorder(BorderFactory.createTitledBorder
                (BorderFactory.createEtchedBorder(1), title,
                        TitledBorder.BOTTOM, TitledBorder.BOTTOM));
        field.setBackground(bg);
    }

    private void invalidate(JTextField field) {
        mediator.getInvalidFields().add(field);
        setBorder(field, INV_IN, R_BG);
    }

    private void validate(JTextField field) {
        mediator.getInvalidFields().remove(field);
        setBorder(field, OK_BG);
    }

    private void setBoxSelect() {
        mediator.setSelectedType((PassengerType) comboBox.getSelectedItem());
    }


}
