package cars;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Car extends JPanel implements Stats{

    private String name;
    private Body body;
    private Motor motor;
    private Tires tires;
    private Spoiler spoiler;

    private ArrayList<CarPart> carParts;

    // Stats
    private Color color;

    public Car() {
        carParts = new ArrayList<>();
    }

    public Car(String name, Body body, Motor motor, Tires tires, Spoiler spoiler, Color color) {
        this();
        this.name = name;
        this.color = color;

        // Add the car parts to the car
        carParts.add(body);
        carParts.add(motor);
        carParts.add(tires);
        carParts.add(spoiler);

        this.setSize(450, 200);
    }

    /**
     * TEMP : Should be done by the builder or another design less ugly than switch
     */
    public void installCarPart(CarPart carPart) {
        if(!carParts.contains(carPart)) {
            System.out.println("adding : " + carPart.getName());
            carParts.add(carPart);
        } else {
            System.out.println("already in");
        }
    }

    @Override
    public double getAcceleration() {
        double averageAcceleration = 0;

        for(CarPart carPart : carParts)
            averageAcceleration += carPart.getAcceleration();

        return averageAcceleration;
    }

    @Override
    public double getWeight() {
        double averageWeight = 0;

        for(CarPart carPart : carParts)
            averageWeight += carPart.getWeight();

        return averageWeight;
    }

    @Override
    public double getAdherence() {
        double averageAdherence = 0;

        for(CarPart carPart : carParts)
            averageAdherence += carPart.getAdherence();

        return averageAdherence;
    }

    @Override
    public double getManiability() {
        double averageManiability = 0;

        for(CarPart carPart : carParts)
            averageManiability += carPart.getManiability() / carParts.size();

        return averageManiability;
    }

    @Override
    public double getResistance() {
        double averageResistance = 0;

        for(CarPart carPart : carParts)
            averageResistance += carPart.getResistance() / carParts.size();

        return averageResistance;
    }

    public Color getColor(){
        return color;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for(CarPart carPart : carParts)
            g.drawImage(carPart.getImage(), carPart.getXCoord(), carPart.getYCoord(), this);
    }
}
