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

    public Car() {

    }

    public Car(String name, Body body, Motor motor, Tires tires, Spoiler spoiler, Color color) {
        this.name = name;
        this.body = body;
        this.motor = motor;
        this.tires = tires;
        this.spoiler = spoiler;
        this.color = color;

        this.setSize(450, 200);
    }

    /**
     * TEMP : Should be done by the builder or another design less ugly than switch
     */
    public void installCarPart(CarPart carPart) {
        switch(carPart.getCategory()) {
            case "Motor":
                motor = (Motor) carPart;
                break;
            case "Tires":
                tires = (Tires) carPart;
                break;
            case "Spoiler":
                spoiler = (Spoiler) carPart;
                break;
            case "Body":
                body = (Body) carPart;
                break;
        }
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

        if(body != null) g.drawImage(body.getImage(), body.getXCoord(), body.getYCoord(), this);
        if(tires != null) g.drawImage(tires.getImage(), tires.getXCoord(), tires.getYCoord(), this);
        if(motor != null) g.drawImage(motor.getImage(), motor.getXCoord(), motor.getYCoord(), this);
        if(spoiler != null) g.drawImage(spoiler.getImage(), spoiler.getXCoord(), spoiler.getYCoord(), this);
    }
}
