package nl.rug.oop.flaps.aircraft_editor.view.maineditor.b_print;

import lombok.Getter;
import lombok.SneakyThrows;

import javax.imageio.ImageIO;
import java.awt.*;
import java.nio.file.Path;

@Getter
public class BlueprintIcons {
    private Image engineIcon, fuelIcon, cargoIcon;
    public static final int WIDTH = 25, HEIGHT = 25;

    public BlueprintIcons() {
        init();
    }

    @SneakyThrows
    private void init() {
        this.engineIcon = ImageIO.read(Path.of("icons", ("engine_icon.png")).toFile()).
                getScaledInstance(WIDTH, HEIGHT, Image.SCALE_SMOOTH);
        this.fuelIcon = ImageIO.read(Path.of("icons", ("fuel_tank_icon.png")).toFile()).
                getScaledInstance(WIDTH, HEIGHT, Image.SCALE_SMOOTH);
        this.cargoIcon = ImageIO.read(Path.of("icons", ("cargo_icon.png")).toFile()).
                getScaledInstance(WIDTH, HEIGHT, Image.SCALE_SMOOTH);
    }
}
