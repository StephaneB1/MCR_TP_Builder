package races;

import cars.Car;
import utils.Utils;

import java.awt.*;
import java.util.Random;

public class Racer {
    private Car car;
    private double currentDistance; // TODO make all in meters
    private String name;
    private boolean displayLogs;
    private Color color;

    // Store stats to avoid unnecessary recalculations
    // Factors and chances: [0;1]
    private final double CRASH_CHANCE; // Depends on car's adherence and maniability
    private final double SPEED; // Racer's speed affected by Car's stats
    private final double RESISTANCE_FACTOR;

    public Racer(String name, Car car, Color color,  boolean displayLogs) {
        this.name = name;
        this.car = car;
        this.color = color;
        this.displayLogs = displayLogs;

        // Calculate stats
        // TODO make weight affect speed
        CRASH_CHANCE = 1 - Utils.average(car.getAdherence(), car.getManiability());
        SPEED = Car.getBaseSpeedKmh() * car.getAcceleration();
        RESISTANCE_FACTOR = car.getResistance();
    }

    /**
     * Simple color getter
     * @return color of the Racer
     */
    public Color getColor() {
        return color;
    }

    /**
     * Simple name getter.
     * @return the name of the Racer
     */
    public String getName() {
        return name;
    }

    /**
     * Simple Car getter
     * @return Car the car of the Racer
     */
    public Car getCar() {
        return car;
    }

    /**
     * The total distance the Racer has ran so far.
     * @return the distance in meter
     */
    public double getCurrentDistanceMeter() {
        return currentDistance;
    }

    public void runOneTick(double tickValueHour) {
        // The tick must have a positive temporal value
        if(tickValueHour < 0)
            throw new RuntimeException("Cannot run for a negative amount of time.");

        double runDistanceKm = tickValueHour * SPEED; // Initialize to the distance the Racer may run at maximum;
        Random rand = new Random();

        // Car may crash => apply distance malus
        if(rand.nextDouble() < CRASH_CHANCE) {
            // The Racer's resistance softens the crash
            double crashMalusDistanceKm = runDistanceKm - (runDistanceKm * RESISTANCE_FACTOR);
            runDistanceKm -= crashMalusDistanceKm;

            displayLog(name + " crashed: -" + crashMalusDistanceKm + "m");
        }

        currentDistance += runDistanceKm;
        displayLog(name + " ran " + runDistanceKm + "m");
    }

    private void displayLog(String message) {
        if(displayLogs)
            System.out.println(message);
    }
}
