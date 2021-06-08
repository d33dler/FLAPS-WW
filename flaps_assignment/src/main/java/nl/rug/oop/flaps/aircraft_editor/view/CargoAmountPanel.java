package nl.rug.oop.flaps.aircraft_editor.view;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.ActionEvent;

@Getter
@Setter
public class CargoAmountPanel extends JPanel {

    private CargoSettings cargoSettings;
    private JTextField amountField;
    private JButton confirmButton, cancelButton;

    public CargoAmountPanel(CargoSettings cargoSettings) {
        this.cargoSettings = cargoSettings;
        init();
    }

    private void init() {
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        setPreferredSize(new Dimension(100, 20));
        setButtons();
        addTextField();
    }

    private void setButtons() {
        this.confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargoSettings.delegateCommands(cargoSettings.getCommandRequest());
                System.out.println(cargoSettings.getCommandRequest());
                executeCommonUpdate();
            }
        });
        this.cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargoSettings.getCargoWarehouse().getCargoTable().getSelectionModel().clearSelection();
                cargoSettings.getCargoAircraft().getCargoTable().getSelectionModel().clearSelection();
                executeCommonUpdate();
            }
        });
        add(confirmButton);
        add(cancelButton);
        confirmButton.setEnabled(false);
    }

    private void executeCommonUpdate() {
        cargoSettings.getCargoButtonPanel().disableButtons();
        amountField.setText("");
        cargoSettings.setCommandRequest(null);
        setVisible(false);
    }

    private void addTextField() {
        this.amountField = new JTextField("", 5);
        amountField.setPreferredSize(new Dimension(50,15));
        amountField.setToolTipText("Enter cargo unit amount...");
        addDocumentListener(amountField);
        add(amountField);
    }

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
