package races;

import cars.Car;
import utils.Utils;

import java.util.Random;

public class Racer {
    private Car car;
    private double currentDistanceKm;
    private String name;
    private boolean displayLogs;

    // Store stats to avoid unnecessary recalculations
    // Factors and chances: [0;1]
    private final double CRASH_CHANCE; // Depends on car's adherence and maniability
    private final double SPEED; // Racer's speed affected by Car's stats
    private final double RESISTANCE_FACTOR;

    private final static double CRASH_DISTANCE_KM = 0.1; // Base distance in km that the Racer looses if it crashes

    public Racer(String name, Car car, boolean displayLogs) {
        this.name = name;
        this.car = car;
        this.displayLogs = displayLogs;

        // Calculate stats
        CRASH_CHANCE = 1 - Utils.average(car.getAdherence(), car.getManiability());
        SPEED = Car.getBaseSpeedKmh() * car.getAcceleration();
        RESISTANCE_FACTOR = car.getResistance();
    }

    /**
     * The total distance the Racer has ran so far.
     * @return the distance in kilometer
     */
    public double getCurrentDistanceKilometer() {
        return currentDistanceKm;
    }

    /**
     * The total distance the Racer has ran so far.
     * @return the distance in meter
     */
    public double getCurrentDistanceMeter() {
        return getCurrentDistanceKilometer() / 1000;
    }

    /**
     * Simple name getter.
     * @return the name of the Racer
     */
    public String getName() {
        return name;
    }

    public double runOneTick(double tickValueHour) {
        // The tick must have a positive temporal value
        if(tickValueHour < 0)
            throw new RuntimeException("Cannot run for a negative amount of time.");

        double runDistanceKm = tickValueHour * SPEED; // Initialize to the distance the Racer may run at maximum;
        Random rand = new Random();

        // Car may crash => apply distance malus
        if(rand.nextDouble() < CRASH_CHANCE) {
            // The Racer's resistance softens the crash
            double crashMalusDistanceKm = CRASH_DISTANCE_KM * (1 - RESISTANCE_FACTOR);
            runDistanceKm -= crashMalusDistanceKm;
            displayLog(name + " crashed: -" + crashMalusDistanceKm + "Km");
        }

        currentDistanceKm += runDistanceKm;
        displayLog(name + " ran " + runDistanceKm + "Km");
        return runDistanceKm;
    }

    private void displayLog(String message) {
        if(displayLogs)
            System.out.println(message);
    }
}
