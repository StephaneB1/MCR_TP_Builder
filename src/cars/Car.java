package cars;

import javax.swing.*;
import java.awt.*;

public class Car extends JPanel implements Stats{

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

    public Car(String name, Body body, Motor motor, Tires tires, Spoiler spoiler, Color color) {
        this.name = name;
        this.body = body;
        this.motor = motor;
        this.tires = tires;
        this.spoiler = spoiler;
        this.color = color;

        this.setSize(450, 200);
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(body.getImage(), body.getXCoord(), body.getYCoord(), this);
        g.drawImage(tires.getImage(), tires.getXCoord(), tires.getYCoord(), this);
        g.drawImage(motor.getImage(), motor.getXCoord(), motor.getYCoord(), this);
        g.drawImage(spoiler.getImage(), spoiler.getXCoord(), spoiler.getYCoord(), this);
    }
}
