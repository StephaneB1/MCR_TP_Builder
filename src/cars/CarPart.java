package cars;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public abstract class CarPart implements Displayable {

    private String name;

    // Graphic display
    private BufferedImage image;
    private BufferedImage image2;
    private Point relCoord;
    private Color color;

    // Stats
    private Stats stats;

    public CarPart(String name, String imagePath, Stats stats, Point relCoord) {
        this.name = name;
        this.stats = stats;
        this.relCoord = relCoord;
        this.color = Color.BLACK;
        try {
            BufferedImage in = ImageIO.read(new File(imagePath));

            image = new BufferedImage(
                    in.getWidth(), in.getHeight(), BufferedImage.TRANSLUCENT);

            Graphics2D g = image.createGraphics();
            g.drawImage(in, 0, 0, null);
            g.dispose();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public abstract String getCategory();

    public Stats getStats() {
        return stats;
    }

    public String getName() {
        return name;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public BufferedImage getImage() {
        return image;
    }

    @Override
    public int getXCoord() {
        return relCoord.x;
    }

    @Override
    public int getYCoord() {
        return relCoord.y;
    }

    /*public static int compareThem(CarPart a, CarPart b) {
        if(a.getLayerIndex() == b.getLayerIndex()) {
            return 0;
        } else if (a.getLayerIndex() < b.getLayerIndex()) {
            return -1;
        } else {
            return 1;
        }
    }*/
}
