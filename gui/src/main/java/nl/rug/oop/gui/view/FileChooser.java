package nl.rug.oop.gui.view;
import lombok.Getter;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * FileChooser holds no secondary components; A JFileChooser is initialised,
 * configured and stored in a FileChooser object;
 */
@Getter
public class FileChooser extends JFileChooser {
    JFileChooser fileExchange;
    public FileChooser() {
        this.fileExchange = new JFileChooser();
        init();
    }

    /**
     * sets filter to json files;
     */
    public void init() {
        FileNameExtensionFilter filter = new FileNameExtensionFilter("JSON file","json");
        this.fileExchange.setFileFilter(filter);
    }
}
