package nl.rug.oop.tutorial3.controller.actions;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * A menu action. Very similar to the SubmitAction, except that we are using this one in the file menu
 * Note that they both extend AbstractAction. They have the exact same structure.
 * As such, we could theoretically switch them.
 */
public class MenuAction extends AbstractAction {

    private final JFrame frame;

    /**
     * Creates a new menu action
     *
     * @param name The name of this action
     * @param frame The frame of our view
     */
    public MenuAction(String name, JFrame frame) {
        super(name);
        this.frame = frame;
    }

    /**
     * The action that is performed when the user clicks on the item
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        /*
         * The options in our popup dialog
         */
        Object[] options = new Object[]{"Erblin", "King Niels"};

        /*
         * A nice way to show a popup. Definitely look into JOptionPane more as it is very useful to display error
         * messages as well.
         */
        JOptionPane.showOptionDialog(
                frame,
                "Which TA is better?",
                "An insane question",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[1]
        );
    }
}
