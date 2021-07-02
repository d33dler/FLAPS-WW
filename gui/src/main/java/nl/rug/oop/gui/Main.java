package nl.rug.oop.gui;
import com.formdev.flatlaf.FlatDarculaLaf;
import lombok.SneakyThrows;
import nl.rug.oop.gui.model.AppCore;

/**
 * The GUI Database is initialised here.
 */

public class Main {
    @SneakyThrows
    public static void main(String[] args) {
        FlatDarculaLaf.install();
		new AppCore();
    }
}
