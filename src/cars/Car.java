package cars;

import utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Car extends JPanel {

    private static final int BASE_SPEED_KMH = 120; // Unit: km/h

    private String name;
    private Body body;
    private Motor motor;
    private Tires tires;
    private Spoiler spoiler;
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

        this.setSize(450, 200);
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
                    if(a.getLayerIndex() == b.getLayerIndex()) {
                        return 0;
                    } else if (a.getLayerIndex() < b.getLayerIndex()) {
                        return -1;
                    } else {
                        return 1;
                    }
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        // display the car parts
        for(CarPart carPart : carParts) {
            AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaTransparency);
            ((Graphics2D) g).setComposite(ac);
            g.drawImage(carPart.getImage(), carPart.getXCoord(), carPart.getYCoord(), this);
        }
    }

    public static int getBaseSpeedKmh() {
        return BASE_SPEED_KMH;
    }
}
