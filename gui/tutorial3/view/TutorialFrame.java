package nl.rug.oop.tutorial3.view;

import nl.rug.oop.tutorial3.controller.actions.MenuAction;
import nl.rug.oop.tutorial3.model.TutorialModel;

import javax.swing.*;
import java.awt.*;

/**
 * The most basic component of our view
 * Think of the JFrame as the frame of a painting
 * It is a bit different in the sense that a paintingframe usually only supports a single piece of paper
 * With the JFrame, we can add as many pieces of paper as we want
 *
 */
public class TutorialFrame extends JFrame {

    /**
     * Two constants that dictate the initial size in pixels of the frame on our screen
     */
    private final static int WIDTH = 840;
    private final static int HEIGHT = 840;

    /**
     * The model that this view is displaying
     * Remember: we want to nicely separate our model and our view
     * So we do not have a bunch of model information in our view; just a reference to our model
     */
    private TutorialModel model;

    /**
     * Constructor; creates a new TutorialFrame
     *
     * @param model The model that this view is for
     */
    public TutorialFrame(TutorialModel model) {
        /* By passing a string here we set the title of the frame */
        super("Tutorial code");
        this.model = model;
        init();
    }

    /**
     * Initialises some basic components
     */
    private void init() {
        /* Since we are sticking with the painting analogy: the thing that we are going to paint on */
        TutorialPanel panel = new TutorialPanel(model);
        /* We add a panel to our frame. Think of us adding piece of paper to our painting frame. We need something to paint on */
        add(panel);
        /* This method is inherited from JFrame; it set's the size of the frame */
        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        addMenuBar();

        /* We don't allow the user to resize the frame */
        setResizable(false);
        /* Make sure the frame appears in the middle of our screen */
        setLocationRelativeTo(null);
        /* Makes sure that all components are at or above their preferredSize */
        pack();
        /* Make sure our frame is actually visible :) */
        setVisible(true);
    }

    /**
     * Adds a menu bar to this frame (the file menu that you usually see in all kinds of applications
     */
    private void addMenuBar() {
        /* The actual bar at the top */
        JMenuBar menuBar = new JMenuBar();
        /* A "dropdown" list item */
        JMenu fileMenu = new JMenu("File");
        /* An item for within that dropdown list */
        JMenuItem openMenuItem = new JMenuItem(new MenuAction("Open", this));
        fileMenu.add(openMenuItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);
    }
}
