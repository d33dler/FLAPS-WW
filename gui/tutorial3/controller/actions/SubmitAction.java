package nl.rug.oop.tutorial3.controller.actions;

import nl.rug.oop.tutorial3.model.TutorialModel;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * The action of our button
 */
public class SubmitAction extends AbstractAction {

    private final TutorialModel model;
    private final JTextField textField;

    /**
     * Creates a new action
     *
     * @param name The name of this action. This is also what appears on the button
     * @param model The model that should be updated when this button is pressed
     * @param textField The text field from which we read what we need to update
     */
    public SubmitAction(String name, TutorialModel model, JTextField textField) {
        super(name);
        this.model = model;
        this.textField = textField;
    }

    /**
     * This method is called when the user clicks the button
     *
     * @param e Action event; Don't need to do anything with it
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        model.setText(textField.getText());
    }
}
