package cars;

import utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Car {

    private static final int BASE_SPEED_KMH = 120; // Unit: km/h

    private String name;
    private Stats stats;
    private float alphaTransparency = 0.5f; // Transparency is set default to 0.5, use the setter to modify it

    private ArrayList<CarPart> carParts;

    // Stats
    private Color color;

    public Car() {
        carParts = new ArrayList<>();
        stats = new Stats();
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

        updateStats();
    }

    public Stats getStats() {
        return stats;
    }

    public Color getColor(){
        return color;
    }

    public void setAlphaTransparency(float alphaTransparency) {
        this.alphaTransparency = alphaTransparency;
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

    /**
     * Set the Car's Stats values to all CarPart's, averaged
     */
    void updateStats() {
        // Car's stats is its CarParts' averaged
        stats.setSpeed(Utils.averageFunc(carParts, o -> o.getStats().getSpeed()));
        stats.setManiability(Utils.averageFunc(carParts, o -> o.getStats().getManiability()));
        stats.setResistance(Utils.averageFunc(carParts, o -> o.getStats().getResistance()));
    }

    public ArrayList<CarPart> getCarParts() {
        return carParts;
    }

    public static int getBaseSpeedKmh() {
        return BASE_SPEED_KMH;
    }
}
