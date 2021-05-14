package nl.rug.oop.gui.view;

import lombok.Getter;
import lombok.SneakyThrows;
import nl.rug.oop.gui.model.AppCore;
import nl.rug.oop.gui.model.NpcEntity;
import nl.rug.oop.gui.util.DataManager;
import nl.rug.oop.gui.util.UpdateInterface;
import nl.rug.oop.gui.util.UpdaterFunctions;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.file.Path;
@Getter
public class ImagePanel extends JPanel implements UpdateInterface {
    BufferedImage img;
    JLabel image;
    public ImagePanel(AppCore model) {
        model.getDetailsUpdater().addListener(this);
        init();
    }

    @SneakyThrows
    void init() {
        this.setLayout(new GridBagLayout());
        this.img = ImageIO.read(Path.of("images", ("andrew_caesar_bust.png")).toFile());
        this.image = new JLabel(new ImageIcon(img.getScaledInstance(200, 190, BufferedImage.SCALE_SMOOTH)));
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

    @SneakyThrows
    @Override
    public void update(AppCore model) {
        JTable table = model.getGui().getTablePanel().getTable();
        BufferedImage newImage = ImageIO.read(Path.of("images", (model.getDatabase().getTable().getValueAt(table.getSelectedRow(), 3).toString())).toFile());
        this.getImage().setIcon((new ImageIcon(newImage.getScaledInstance(200, 190, BufferedImage.SCALE_SMOOTH))));
        revalidate();
    }

    public void setImage(JLabel image) {
        this.image = image;
    }
}
