package cars;

import java.awt.*;

public class Car implements Stats{

    private String name;
    private Body body;
    private Motor motor;
    private Tires tires;
    private Spoiler spoiler;

    // Stats
    private double acceleration;
    private double weight;
    private double adherence;
    private double maniability;
    private double resistance;
    private Color color;


    public Car(String name, Body body, Motor motor, Tires tires, Spoiler spoiler) {
        this.name = name;
        this.body = body;
        this.motor = motor;
        this.tires = tires;
        this.spoiler = spoiler;
    }

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

    public Color getColor(){
        return color;
    }
}
