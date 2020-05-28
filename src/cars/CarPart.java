package cars;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public abstract class CarPart implements Displayable {

    private String name;

    // Graphic display
    private String imagePath;
    private Image image;
    private Point relCoord;

    // Stats
    private Stats stats;

    public CarPart(String name, String imagePath, Stats stats, Point relCoord) {
        this.name = name;
        this.imagePath = imagePath;
        this.stats = stats;
        this.relCoord = relCoord;

        try {
            image = ImageIO.read(new File(imagePath));
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

    @Override
    public Image getImage() {
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

    public static int compareThem(CarPart a, CarPart b) {
        if(a.getLayerIndex() == b.getLayerIndex()) {
            return 0;
        } else if (a.getLayerIndex() < b.getLayerIndex()) {
            return -1;
        } else {
            return 1;
        }
    }
}
