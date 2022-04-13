package nl.rug.oop.flaps.aircraft_editor.view;

import lombok.SneakyThrows;

import javax.imageio.ImageIO;
import java.awt.*;
import java.nio.file.Path;

public abstract class Icons {
   public static int WIDTH, HEIGHT, scale_smooth = Image.SCALE_SMOOTH;

   @SneakyThrows
   public static Image getImg(String first, String more,int w,int h, int scaling ) {
       return  ImageIO.read(Path.of(first, (more)).toFile()).
               getScaledInstance(w, h, scale_smooth);
   }
}
