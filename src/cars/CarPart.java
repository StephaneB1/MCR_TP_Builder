package cars;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public abstract class CarPart implements Stats, Displayable {

    private String name;

    // Graphic display
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

        this.name         = name;
        this.imagePath    = imagePath;
        this.acceleration = acceleration;
        this.weight       = weight;
        this.adherence    = adherence;
        this.maniability  = maniability;
        this.resistance   = resistance;
        this.relCoord     = relCoord;

        try {
            image = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public abstract String getCategory();

    @Override
    public double getAcceleration() {
        return acceleration;
    }

    @Override
    public double getWeight() {
        return weight;
    }

    @Override
    public double getAdherence() {
        return adherence;
    }

    @Override
    public double getManiability() {
        return maniability;
    }

    @Override
    public double getResistance() {
        return resistance;
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
