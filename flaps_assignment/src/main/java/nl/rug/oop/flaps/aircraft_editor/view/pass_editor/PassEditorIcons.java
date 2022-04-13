package nl.rug.oop.flaps.aircraft_editor.view.pass_editor;

import nl.rug.oop.flaps.aircraft_editor.view.Icons;

import java.awt.*;

public class PassEditorIcons extends Icons {
    public static Image defaultAvatar;

    static {
        WIDTH = 25;
        HEIGHT = 25;
        defaultAvatar = getImg("icons", "default_avatar.jpg", WIDTH, HEIGHT, scale_smooth);
    }

    public static Image initSize(String name, int w, int h) {
      return getImg("icons", name, w, h, scale_smooth);
    }

}
