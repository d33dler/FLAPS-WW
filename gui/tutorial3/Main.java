package nl.rug.oop.tutorial3;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import nl.rug.oop.tutorial3.model.TutorialModel;
import nl.rug.oop.tutorial3.view.TutorialFrame;

public class Main {

    public static void main(String[] args) {
        FlatDarculaLaf.install();
//        FlatIntelliJLaf.install();
        // Initialise our model
        TutorialModel model = new TutorialModel();
        // Initialise our view
        new TutorialFrame(model);
    }

}
