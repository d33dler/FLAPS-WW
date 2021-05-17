package nl.rug.oop.gui.view;

import nl.rug.oop.gui.model.AppCore;
import nl.rug.oop.gui.view.MainFrame;

import javax.swing.*;

public class FileChooser extends JFileChooser {
    JFileChooser fileExchange;
    MainFrame mainFrame;

    public FileChooser(AppCore model, MainFrame mainFrame) {
        this.fileExchange = new JFileChooser();
        this.mainFrame = mainFrame;
        init();
    }

    public void init() {
        fileExchange.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    }
}
