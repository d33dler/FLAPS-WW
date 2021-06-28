package nl.rug.oop.flaps.aircraft_editor.view.cargo_editor;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import nl.rug.oop.flaps.aircraft_editor.controller.configcore.Controller;
import nl.rug.oop.flaps.aircraft_editor.model.mediators.CargoMediator;
import nl.rug.oop.flaps.aircraft_editor.view.generic_panels.GenericButtonPanel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import java.awt.*;

/**
 * CargoAmountPanel - minimalistic panel containing the confirm button and textfield for user input of cargo amounts;
 */
@Getter
@Setter
public class CargoAmountPanel extends GenericButtonPanel {
    private CargoFrame cargoFrame;
    private JTextField amountField;
    private CargoMediator mediator;
    private JButton confirmButton, cancelButton;
    private Controller controller;

    public CargoAmountPanel(CargoFrame cargoFrame, Controller controller, CargoMediator mediator) {
        this.controller = controller;
        this.cargoFrame = cargoFrame;
        this.mediator = mediator;
        init();
    }

    private void init() {
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        setPreferredSize(new Dimension(100, 20));
        setButtons();
        addTextField();
    }

    /**
     * Setup JButtons;
     */
    private void setButtons() {
        add(confirmButton = newButton("Confirm", () -> {
            controller.delegateCommand(mediator);
            executeCommonUpdate();
        }, false));
        add(cancelButton = newButton("Cancel", this::executeCommonUpdate));
    }

    /**
     * Executes common updates upon button click
     */
    private void executeCommonUpdate() {
        cargoFrame.getCargoWarehouse().getDatabaseTable().getSelectionModel().clearSelection();
        cargoFrame.getCargoAircraft().getDatabaseTable().getSelectionModel().clearSelection();
        amountField.setText("");
        setVisible(false);
    }

    /**
     * Init and configure the cargo amount text field
     */
    private void addTextField() {
        this.amountField = new JTextField("", 10);
        amountField.setToolTipText("Enter cargo unit amount...");
        addDocumentListener(amountField);
        mediator.setQuantityField(amountField);
        add(new JPanel(new FlowLayout()).add(amountField));
    }

    /**
     * @param field - field to get a DocumentListener;
     *              verifies user input to disable the confirm button if the input is invalid;
     */
    private void addDocumentListener(JTextField field) {
        field.getDocument().addDocumentListener(new DocumentListener() {
            public void update(DocumentEvent e) throws BadLocationException {
                tweakButton(e.getDocument().getText(0, e.getDocument().getLength()));
            }

            @SneakyThrows
            @Override
            public void insertUpdate(DocumentEvent e) {
                update(e);
            }

            @SneakyThrows
            @Override
            public void removeUpdate(DocumentEvent e) {
                update(e);
            }

            @SneakyThrows
            @Override
            public void changedUpdate(DocumentEvent e) {
                update(e);
            }
        });
    }

    /**
     * @param input - user input in the amount text field
     *              verifies if the input matches the pattern consisting of digits only;
     */
    private void tweakButton(String input) {
        confirmButton.setEnabled(input.matches("[0-9]+"));
    }

    @Override
    public void enable() {
        setVisible(true);
    }

    public void question() {
        int n = JOptionPane.showConfirmDialog(
                cargoFrame,
                "Execute selected action?",
                "Confirm",
                JOptionPane.YES_NO_OPTION);
        if (n == 1) cancelButton.doClick();
        else {
            confirmButton.setEnabled(true);
            confirmButton.doClick();
        }
    }
}
