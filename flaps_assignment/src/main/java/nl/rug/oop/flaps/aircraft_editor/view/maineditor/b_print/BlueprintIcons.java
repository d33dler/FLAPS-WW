package nl.rug.oop.flaps.aircraft_editor.view.maineditor.b_print;

import lombok.Getter;
import lombok.extern.java.Log;
import nl.rug.oop.flaps.aircraft_editor.view.Icons;

import java.awt.*;

@Getter
@Log
public class BlueprintIcons extends Icons {

    public static Image engineIcon, fuelIcon, cargoIcon, passengerIcon;

    static {
        WIDTH = 25;
        HEIGHT = 25;
        engineIcon = getImg("icons", "engine_icon.png", WIDTH, HEIGHT, scale_smooth);
        fuelIcon = getImg("icons", "fuel_tank_icon.png", WIDTH, HEIGHT, scale_smooth);
        cargoIcon = getImg("icons", "cargo_icon.png", WIDTH, HEIGHT, scale_smooth);
        passengerIcon = getImg("icons", "passenger_icon.png", WIDTH, HEIGHT, scale_smooth);
    }


}
