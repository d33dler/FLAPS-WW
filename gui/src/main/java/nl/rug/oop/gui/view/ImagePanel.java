package nl.rug.oop.gui.view;

import lombok.SneakyThrows;
import nl.rug.oop.gui.model.NpcEntity;
import nl.rug.oop.gui.util.DataManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.file.Path;

public class ImagePanel extends JPanel {
    public ImagePanel() {
        init();
    }

    @SneakyThrows
    void init() {
        this.setLayout(new GridBagLayout());
        BufferedImage img = ImageIO.read(Path.of("images", ("andrew_caesar_bust.png")).toFile());
        JLabel image = new JLabel(new ImageIcon(img.getScaledInstance(200, 190, BufferedImage.SCALE_SMOOTH)));
        setPreferredSize(new Dimension(230,200));
        Border border = BorderFactory.createBevelBorder(BevelBorder.RAISED);
        image.setBorder(border);
        GridBagConstraints gbC = new GridBagConstraints();
        gbC.weightx = 0.25;
        gbC.gridx = 1;
        gbC.gridy = 0;
        add(image, gbC);
        validate();
    }

}
