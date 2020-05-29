package races;

import cars.Car;

import java.awt.*;
import java.util.Random;

public class Racer implements Comparable<Racer>{
    private Car car;
    private double currentDistance; // TODO make all in meters
    private String name;
    private boolean displayLogs;
    private Color color;

    // Store stats to avoid unnecessary recalculations
    // Factors and chances: [0;1]
    private final double crashChance; // Depends on car's adherence and maniability
    private final double speed; // Racer's speed affected by Car's stats
    private final double resistanceFactor;

    public Racer(String name, Car car, Color color,  boolean displayLogs) {
        this.name = name;
        this.car = car;
        this.color = color;
        this.displayLogs = displayLogs;

        // TODO REWORK

        // Calculate stats
        crashChance = 0.5/*1 - Utils.average(car.getAdherence(), car.getManiability())*/;
        speed = 5/*Car.getBaseSpeedKmh() * car.getAcceleration()*/;
        resistanceFactor = 0.5/*car.getResistance()*/;
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
        // TODO FULL REWORK
        System.out.println(car.getStats());
        // The tick must have a positive temporal value
        if(tickValueHour < 0)
            throw new RuntimeException("Cannot run for a negative amount of time.");

        double runDistanceKm = tickValueHour * speed; // Initialize to the distance the Racer may run at maximum;
        Random rand = new Random();

        // Car may crash => apply distance malus
        if(rand.nextDouble() < crashChance) {
            // The Racer's resistance softens the crash
            double crashMalusDistanceKm = runDistanceKm - (runDistanceKm * resistanceFactor);
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

    @Override
    public int compareTo(Racer racer) {
        // Refctor to use only Double value ?
        return (int) (currentDistance - racer.getCurrentDistanceMeter());
    }
}
