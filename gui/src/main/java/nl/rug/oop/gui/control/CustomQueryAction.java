package nl.rug.oop.gui.control;

import nl.rug.oop.gui.model.AppCore;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class CustomQueryAction extends AbstractAction {
    AppCore model;
    JTextArea queryCommand;

    public CustomQueryAction(AppCore model, String bName, JTextArea queryCommand) {
        super(bName);
        this.model = model;
        this.queryCommand = queryCommand;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        model.setSearchField("");
        System.out.println("this is not cool: " + queryCommand.getText());
        model.setQueryCommand(queryCommand.getText());
        model.updateDatabase();
    }
}
