package nl.rug.oop.flaps.aircraft_editor.view.pass_editor;

import lombok.Getter;
import lombok.SneakyThrows;
import nl.rug.oop.flaps.aircraft_editor.model.EditorCore;
import nl.rug.oop.flaps.aircraft_editor.model.mediators.PassengerMediator;
import nl.rug.oop.flaps.aircraft_editor.view.generic_panels.GenericButtonPanel;
import nl.rug.oop.flaps.aircraft_editor.view.maineditor.main_panels.SpecialComponent;
import nl.rug.oop.flaps.simulation.model.aircraft.Aircraft;
import nl.rug.oop.flaps.simulation.model.loaders.utils.FileUtils;
import nl.rug.oop.flaps.simulation.model.loaders.utils.ViewUtils;
import nl.rug.oop.flaps.simulation.model.passengers.Passenger;
import nl.rug.oop.flaps.simulation.model.passengers.PassengerType;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

@Getter
public class DataBlanksPanel extends GenericButtonPanel {
    private EditorCore core;
    private Aircraft aircraft;
    private PassengersFrame boardFrame;
    private List<Field> passFields = new ArrayList<>();
    private JComboBox<PassengerType> comboBox;
    private PassengerMediator mediator;
    private JPanel fieldBtPanel;
    public static final int PH_WIDTH = 275, PH_HEIGHT = 300;

    private static final String TITLE = "Register Passenger", INV_IN = "×";
    private static final int WIDTH = 500, HEIGHT = 500, F_WIDTH = 130, F_HEIGHT = 40, VAL_WDT = 400;
    public static final Color
            R_BG = new Color(250, 74, 74, 81),
            OK_BG = new Color(146, 250, 76, 55);
    @Getter
    private JLabel passPhoto = new JLabel() {

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(PH_WIDTH, PH_HEIGHT);
        }

        @Override
        public Dimension getMaximumSize() {
            return this.getPreferredSize();
        }

    };
    private Image ava_pic;

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
        ViewUtils.setCustomBorder(this, TITLE, 1, TitledBorder.ABOVE_TOP, TitledBorder.TOP);
        setUpPhoto();
        setUpBlanks();
        setUpComboBox();
        setUpButtons();
    }

    private void setUpPhoto() {
        ViewUtils.setCustomBorder(passPhoto, "", 1, TitledBorder.ABOVE_TOP, TitledBorder.TOP);
        passPhoto.setAlignmentX(JLabel.CENTER);
        passPhoto.setAlignmentY(JLabel.CENTER);
        this.ava_pic = PassEditorIcons.initSize("default_avatar.jpg", PH_WIDTH, PH_HEIGHT);
        passPhoto.setIcon(new ImageIcon(ava_pic));
        boardFrame.add(passPhoto);
    }

    private void setUpBlanks() {
        this.passFields = FileUtils.getAllFieldsFiltered(Passenger.class);
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
        JLabel fieldName = ViewUtils.getFormattedName(field);
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
        this.fieldBtPanel = new JPanel(new FlowLayout());
        fieldBtPanel.add(newButton("Clear fields", () ->
                mediator.getBlankSet().forEach(field -> field.setText(""))));
        fieldBtPanel.add(newButton("Upload Photo", () -> {
            mediator.initPhotoChooser(this);
            this.setEnabled(false);
        }, "Maximum size: 6 Mb"));
        add(fieldBtPanel, 8);
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
        ViewUtils.setCustomBorder(field, title, 1, TitledBorder.BOTTOM, TitledBorder.BOTTOM);
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

    @SneakyThrows
    public void updatePhoto(File file) {
        this.ava_pic = ImageIO.read(file);
        double ratio = (double) ava_pic.getWidth(null) / (double) ava_pic.getHeight(null);
        ImageIcon avatar = new ImageIcon(ava_pic
                .getScaledInstance((int) Math.ceil(ratio * PH_WIDTH), PH_HEIGHT, Image.SCALE_SMOOTH)
        );
        passPhoto.setIcon(avatar);
        revalidate();
        repaint();
    }

    private void setBoxSelect() {
        mediator.setSelectedType((PassengerType) comboBox.getSelectedItem());
    }


}
