package cars;

import java.awt.*;

public abstract class CarPart implements Stats{

    private String name;
    private String imagePath;

    // Stats
    private double acceleration;
    private double weight;
    private double adherence;
    private double maniability;
    private double resistance;
    private Color color;

    public CarPart(String name, String imagePath, double acceleration, double weight, double adherence, double maniability, double resistance, Color color) {
        this.name = name;
        this.imagePath = imagePath;
        this.acceleration = acceleration;
        this.weight = weight;
        this.adherence = adherence;
        this.maniability = maniability;
        this.resistance = resistance;
        this.color = color;
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
}
