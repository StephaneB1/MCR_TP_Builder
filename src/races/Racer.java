package races;

import cars.Car;
import cars.Stats;

import java.awt.*;
import java.util.Random;

public class Racer implements Comparable<Racer>{
    private Car car;
    private double currentDistance;
    private String name;
    private boolean displayLogs;
    private Color color;

    private double crashTimeout = 0;
    private final double crashDuration;
    private final double crashChance;
    private final double distancePerTick;

    // Global constants the Stats factors will affect/use
    private static final double CRASH_DURATION = 200; // [tick]
    private static final double CRASH_CHANCE = 0.005;
    private static final double DISTANCE_PER_TICK = 5; // [meter], base velocity
    // [0;1] amount of realtime global randomness (0 = no randomness, 1 = a lot)
    private static final double REALTIME_RANDOMNESS = 0; // TODO WIP
    private static final Random rand = new Random();

    public Racer(String name, Car car, Color color, boolean displayLogs) {
        this.name = name;
        this.car = car;
        this.color = color;
        this.displayLogs = displayLogs;

        // Apply Car's Stats to environment constants
        Stats carStats = car.getStats();
        crashDuration = CRASH_DURATION * (1 - Stats.toPercent(carStats.getResistance()));
        crashChance = CRASH_CHANCE * (1 - Stats.toPercent(carStats.getManiability()));
        distancePerTick = DISTANCE_PER_TICK * Stats.toPercent(carStats.getSpeed());

        displayLog(
                "==========\n" + name
                + "\n" + car.getStats()
                + "\nCrash duration: " + crashDuration
                + "\nCrash chance  : " + crashChance
                + "\nmeter pertick : " + distancePerTick
        );
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
    public double getCurrentDistance() {
        return currentDistance;
    }

    public boolean isCrashed() {
        return crashTimeout > 0;
    }

    /**
     * TODO WIP
     */
    public void runOneTick() {
        if(crashTimeout > 0) {
            if(--crashTimeout == 0) {
                displayLog(name + " gets back to race");
            }
        } else { // Racer runs
            if(rand.nextDouble() < crashChance) { // Bad luck, Racer crashes
                crashTimeout = crashDuration; // Racer has to wait crashDuration ticks
                displayLog(name + " crashes");
            } else { // Racer runs normally
                currentDistance += distancePerTick;
            }
        }
    }

    /**
     * Displays message to console if displayLogs attribute is set to true
     * @param message text to display in console
     */
    private void displayLog(String message) {
        if(displayLogs)
            System.out.println(message);
    }

    @Override
    public int compareTo(Racer racer) {
        // Refactor to use only Double value ?
        return (int) (currentDistance - racer.getCurrentDistance());
    }
}
