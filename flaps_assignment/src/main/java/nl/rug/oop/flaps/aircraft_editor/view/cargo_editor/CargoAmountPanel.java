package nl.rug.oop.flaps.aircraft_editor.view.cargo_editor;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * CargoAmountPanel - minimalistic panel containing the confirm button and textfield for user input of cargo amounts;
 */
@Getter
@Setter
public class CargoAmountPanel extends JPanel {

    private CargoTradeFrame cargoTradeFrame;
    private JTextField amountField;
    private JButton confirmButton, cancelButton;

    public CargoAmountPanel(CargoTradeFrame cargoTradeFrame) {
        this.cargoTradeFrame = cargoTradeFrame;
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
        this.confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargoTradeFrame.delegateCommand();
                executeCommonUpdate();
            }
        });
        this.cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargoTradeFrame.getCargoWarehouse().getDatabaseTable().getSelectionModel().clearSelection();
                cargoTradeFrame.getCargoAircraft().getDatabaseTable().getSelectionModel().clearSelection();
                executeCommonUpdate();
            }
        });
        add(confirmButton);
        add(cancelButton);
        confirmButton.setEnabled(false);
    }

    /**
     * Executes common updates upon button click
     */
    private void executeCommonUpdate() {
        cargoTradeFrame.getCargoButtonPanel().disableButtons();
        amountField.setText("");
        cargoTradeFrame.setCommandRequest(null);
        setVisible(false);
    }

    /**
     * Init and configure the cargo amount text field
     */
    private void addTextField() {
        this.amountField = new JTextField("", 5);
        amountField.setPreferredSize(new Dimension(50, 15));
        amountField.setToolTipText("Enter cargo unit amount...");
        addDocumentListener(amountField);
        add(amountField);
    }

    /**
     *
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
     *
     * @param input - user input in the amount text field
     *              verifies if the input matches the pattern consisting of digits only;
     */
    private void tweakButton(String input) {
        confirmButton.setEnabled(input.matches("[0-9]+"));
    }

    protected void enablePartial() {
        this.setVisible(true);
        amountField.setVisible(false);
        confirmButton.setEnabled(true);
    }

    protected void enableFull() {
        this.setVisible(true);
        amountField.setVisible(true);
        confirmButton.setEnabled(true);
    }
}
