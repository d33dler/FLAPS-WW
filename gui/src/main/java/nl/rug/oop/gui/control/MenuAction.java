package nl.rug.oop.gui.control;

import nl.rug.oop.gui.view.FileChooser;
import nl.rug.oop.gui.view.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * A menu action. Very similar to the SubmitAction, except that we are using this one in the file menu
 * Note that they both extend AbstractAction. They have the exact same structure.
 * As such, we could theoretically switch them.
 */
public class MenuAction extends AbstractAction {

    private final MainFrame mainFrame;
    private FileChooser fileChooser;

    /**
     * Creates a new menu action
     *
     * @param name The name of this action
     */

    public MenuAction(String name, MainFrame mainFrame) {
        super(name);
        this.mainFrame = mainFrame;
        this.fileChooser = mainFrame.getFileChooser();
    }

    /**
     * The action that is performed when the user clicks on the item
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        int export = fileChooser.showSaveDialog(mainFrame);
        if (export == JFileChooser.APPROVE_OPTION) {
            DatabaseExport databaseExport = mainFrame.getModel().getDatabaseExport();
            databaseExport.exportFile(mainFrame.getModel(), fileChooser);
        }
    }
}
