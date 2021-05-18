package nl.rug.oop.gui.view;
import lombok.Getter;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

@Getter
public class FileChooser extends JFileChooser {
    JFileChooser fileExchange;
    public FileChooser() {
        this.fileExchange = new JFileChooser();
        init();
    }

    public void init() {
        FileNameExtensionFilter filter = new FileNameExtensionFilter("JSON file","json");
        this.fileExchange.setFileFilter(filter);
    }
}
