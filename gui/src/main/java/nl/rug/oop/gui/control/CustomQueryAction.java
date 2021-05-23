package nl.rug.oop.gui.control;

import nl.rug.oop.gui.model.AppCore;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Overrides the AbstractAction method
 */
public class CustomQueryAction extends AbstractAction {
    AppCore model;
    JTextArea queryCommand;

    public CustomQueryAction(AppCore model, String bName, JTextArea queryCommand) {
        super(bName);
        this.model = model;
        this.queryCommand = queryCommand;
    }

    /**
     *
     * @param actionEvent submitting a custom query
     *                    actionPerformed() :
     *                    calls the model to update the remote and local database;
     */
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        model.setQueryCommand(queryCommand.getText());
        model.updateDatabase();
    }
}
