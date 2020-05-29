package cars;

import javax.swing.*;
import java.awt.*;
import java.awt.image.RescaleOp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class Car implements Stats {

    private static final int BASE_SPEED_KMH = 120; // Unit: km/h

    private String name;

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

    }

    public void installCarPart(CarPart carPart) {
        if(!carParts.contains(carPart)) {
            carParts.add(carPart);

            // Reorder the parts by layers for the graphical display
            Collections.sort(carParts, new Comparator<CarPart>() {
                @Override
                public int compare(CarPart a, CarPart b) {
                    return Integer.compare(a.getLayerIndex(), b.getLayerIndex());
                }
            });
        }
    }

    // TODO refactor stats getters

    @Override
    public double getAcceleration() {
        double averageAcceleration = 0;

        for(CarPart carPart : carParts)
            averageAcceleration += carPart.getAcceleration();

        return averageAcceleration / carParts.size();
    }

    @Override
    public double getWeight() {
        double totalWeight = 0;

        for(CarPart carPart : carParts)
            totalWeight += carPart.getWeight();

        return totalWeight;
    }

    @Override
    public double getAdherence() {
        double averageAdherence = 0;

        for(CarPart carPart : carParts)
            averageAdherence += carPart.getAdherence();

        return averageAdherence / carParts.size();
    }

    @Override
    public double getManiability() {
        double averageManiability = 0;

        for(CarPart carPart : carParts)
            averageManiability += carPart.getManiability() / carParts.size();

        return averageManiability / carParts.size();
    }

    @Override
    public double getResistance() {
        double averageResistance = 0;

        for(CarPart carPart : carParts)
            averageResistance += carPart.getResistance() / carParts.size();

        return averageResistance / carParts.size();
    }

    public Color getColor(){
        return color;
    }

    public ArrayList<CarPart> getCarParts() {
        return carParts;
    }

    public static int getBaseSpeedKmh() {
        return BASE_SPEED_KMH;
    }
}
