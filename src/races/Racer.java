package races;

import cars.Car;
import utils.Utils;

import java.util.Random;

public class Racer {
    private Car car;
    private double currentDistance;
    private String name;
    private boolean displayLogs;

    // Store stats to avoid unnecessary recalculations
    final double CRASH_CHANCE; // [0;1] Depends on car's adherence and maniability
    final double SPEED_FACTOR;  // [0;1]
    final double RESISTANCE_FACTOR; // [0;1]

    public Racer(String name, Car car, boolean displayLogs) {
        this.name = name;
        this.car = car;
        this.displayLogs = displayLogs;

        // Calculate stats
        CRASH_CHANCE = 1 - Utils.average(car.getAdherence(), car.getManiability());
        SPEED_FACTOR = Car.getBaseSpeedKmh() * car.getAcceleration();
        RESISTANCE_FACTOR = car.getResistance();
    }

    /**
     * Simple distance getter.
     * @return the current distance the Racer has reached
     */
    public double getCurrentDistance() {
        return currentDistance;
    }

    /**
     * Simple name getter.
     * @return the name of the Racer
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return the distance the Racer has ran this tick
     */
    public double runOneTick() {
        double runDistance = 0;
        Random rand = new Random();

        // Car may crash
        if(rand.nextDouble() < CRASH_CHANCE) {
            displayLog("The car crashed");
        }

        currentDistance += runDistance;
        return runDistance;
    }

    private void displayLog(String message) {
        if(displayLogs)
            System.out.println(message);
    }
}
