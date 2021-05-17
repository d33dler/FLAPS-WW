package nl.rug.oop.tutorial3.view;

import nl.rug.oop.tutorial3.controller.TutorialKeyListener;
import nl.rug.oop.tutorial3.controller.actions.SubmitAction;
import nl.rug.oop.tutorial3.model.TutorialModel;
import nl.rug.oop.tutorial3.util.UpdateListener;

import javax.swing.*;
import java.awt.*;

/**
 * The piece of paper that we are painting on
 */
public class TutorialPanel extends JPanel implements UpdateListener {
    /**
     * The width of the text areas in this panel
     * Note that we are using a constant for this. This prevent having magic numbers in our code
     */
    private static final int TEXT_AREA_WIDTH = 256;
    /**
     * The height of the text areas in this panel
     */
    private static final int TEXT_AREA_HEIGHT = 256;


    private JTextArea leftTextArea;
    private JTextArea rightTextArea;
    private JTextField textField;

    /**
     * This is something that was not in the tutorial, but it ensures that our components appear at the correct location
     * on our screen
     */
    private GridBagConstraints gbc;

    /**
     * The model that this view displays
     */
    private final TutorialModel model;

    /**
     * Creates a new tutorial frame with 2 text areas, 1 text box and 1 button
     *
     * @param model The model this view displays
     */
    public TutorialPanel(TutorialModel model) {
        /* We use a GridBagLayout to ensure our components end up in the proper position */
        super(new GridBagLayout());
        this.model = model;
        /* Let our view listen to our model */
        model.addListener(this);
        init();
    }

    public void init() {
        gbc = new GridBagConstraints();
        /* Add some padding between our components */
        gbc.insets = new Insets(10, 10, 10, 10);
        /* Initialise our grid coordinates to 0. Note that (0,0) is top left */
        gbc.gridx = 0;
        gbc.gridy = 0;
        addTextAreas();
        /* We reset the x coordinate so that we don't shift everything to the right */
        gbc.gridx = 0;
        /* We want the next two components to be underneath our previous components, so increment y again */
        gbc.gridy++;
        addTextField();
        /* We want our button next to our text field, so we increment x */
        gbc.gridx++;
        addButton();
        /* If you want to add more here, don't forget to reset x and increment y :) */
    }

    /**
     * Adds two text areas to our panel
     * Note that this already contains some duplicate code...
     */
    private void addTextAreas() {
        leftTextArea = new JTextArea();
        rightTextArea = new JTextArea();

        /* Set the size of our text areas */
        leftTextArea.setPreferredSize(new Dimension(TEXT_AREA_WIDTH, TEXT_AREA_HEIGHT));
        rightTextArea.setPreferredSize(new Dimension(TEXT_AREA_WIDTH, TEXT_AREA_HEIGHT));

        /*
         * Make sure the user cannot manually change these areas, since they are only used for displaying data
         * We use the text field later on to allow for actual user input
         */
        leftTextArea.setEditable(false);
        rightTextArea.setEditable(false);

        /* Set the background color to make it stand out a tiny bit */
        leftTextArea.setBackground(Color.GRAY);
        rightTextArea.setBackground(Color.GRAY);

        /*
         * Add to the panel. Note the second gbc parameter. this is very important, because otherwise it won't take
         * the layout we gave this panel into consideration
         */
        add(leftTextArea, gbc);
        /* We want them to be next to each other, so we increment the x coordinate of our grid */
        gbc.gridx++;
        add(rightTextArea, gbc);
    }

    /**
     * Creates and adds a text field to the panel that allows the user to type some stuff
     */
    private void addTextField() {
        textField = new JTextField("", 30);
        /* Add a listener to the text field. This allows us to do updates whenever the user types something */
        textField.getDocument().addDocumentListener(new TutorialKeyListener(leftTextArea));
        /* Add text field to the panel */
        add(textField, gbc);
    }

    /**
     * Creates and adds a button to the panel
     */
    private void addButton() {
        /*
         * We create a button. Each button has an action that is invoked when the user presses the button
         * The name of this action dictates what text appears on our button
         */
        JButton button = new JButton(new SubmitAction("Submit", model, textField));
        /* We can even set a tooltip! */
        button.setToolTipText("I am a helpful tooltip!");
        /* Add button to panel */
        add(button, gbc);
    }

    /**
     * Update method from our UpdateListener. If this is called, it means our model told us that something updated
     * As such, we should also update some info
     */
    @Override
    public void update() {
        rightTextArea.setText(model.getText());
    }
}
