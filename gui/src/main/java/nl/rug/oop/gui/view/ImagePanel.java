package nl.rug.oop.gui.view;

import lombok.Getter;
import lombok.SneakyThrows;
import nl.rug.oop.gui.model.AppCore;
import nl.rug.oop.gui.util.UpdateInterface;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.file.Path;

/**
 * ImagePanel holds no secondary components; only a JLabel is used to represent a picture;
 */
@Getter
public class ImagePanel extends JPanel implements UpdateInterface {
    BufferedImage img;
    JLabel image;

    /**
     * Constructor adds the class to the listener list for updates from DetailsUpdater
     */
    public ImagePanel(AppCore model) {
        model.getDetailsUpdater().addListener(this);
        init();
    }

    /**
     * init() : configures the GridBag layout, default launch picture, preferred size, border;
     */
    @SneakyThrows
    void init() {
        this.setLayout(new GridBagLayout());
        this.img = ImageIO.read(Path.of("images", ("andrew_caesar_bust.png")).toFile());
        this.image = new JLabel(new ImageIcon(img.getScaledInstance(200, 190, BufferedImage.SCALE_SMOOTH)));
        setPreferredSize(new Dimension(230, 200));
        Border border = BorderFactory.createBevelBorder(BevelBorder.RAISED);
        image.setBorder(border);
        GridBagConstraints gbC = new GridBagConstraints();
        gbC.weightx = 0.25;
        gbC.gridx = 1;
        gbC.gridy = 0;
        add(image, gbC);
        validate();
    }

    /**
     * @param model - supplies the image location of the currently selected entity row;
     *              the image is changed and revalidated.
     */
    @SneakyThrows
    @Override
    public void update(AppCore model) {
        BufferedImage newImage;
        JTable table = model.getGui().getTablePanel().getTable();
        try {
            newImage = ImageIO.read(Path.of("images", (model.getDatabase().getTable().getValueAt(table.getSelectedRow(), 3).toString())).toFile());
        } catch (NullPointerException e) {
            newImage = ImageIO.read(Path.of("images", ("notfound.gif")).toFile());
        }
        this.getImage().setIcon(new ImageIcon(newImage.getScaledInstance(200, 190, BufferedImage.SCALE_SMOOTH)));
        revalidate();
    }

}
