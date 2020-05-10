package cars;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public abstract class CarPart implements Stats {

    private String name;
    private String imagePath;
    private Image image;
    private Point relCoord;

    // Stats
    private double acceleration;
    private double weight;
    private double adherence;
    private double maniability;
    private double resistance;

    public CarPart(String name, String imagePath, double acceleration,
                   double weight, double adherence, double maniability,
                   double resistance, Point relCoord) {
        this.name = name;
        this.imagePath = imagePath;
        this.acceleration = acceleration;
        this.weight = weight;
        this.adherence = adherence;
        this.maniability = maniability;
        this.resistance = resistance;
        this.relCoord = relCoord;

        try {
            image = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public abstract String getCategory();

    @Override
    public double getAcceleration() {
        return 0;
    }

    @Override
    public double getWeight() {
        return 0;
    }

    @Override
    public double getAdherence() {
        return 0;
    }

    @Override
    public double getManiability() {
        return 0;
    }

    @Override
    public double getResistance() {
        return 0;
    }

    public Image getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public int getXCoord() {
        return relCoord.x;
    }

    public int getYCoord() {
        return relCoord.y;
    }
}
