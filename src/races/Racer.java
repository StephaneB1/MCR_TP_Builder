package races;

import cars.Car;
import cars.Stats;

import java.awt.*;
import java.util.Random;

public class Racer implements Comparable<Racer>, Cloneable{
    private Car car;
    private double currentDistance;
    private String name;
    private boolean displayLogs;
    private Color color;
    private boolean hasFinished = false;

    private double crashTimeout = 0; // Crash time (does not vary during crash)
    private double crashTimer = 0; // Crash countdown timer (decreases during crash)
    private final double crashDuration;
    private final double crashChance;
    private final double distancePerTick;
    private static final Random rand = new Random();

    /***** GLOBAL PARAMETERS *****/
    private static final double REALTIME_RANDOMNESS = 0.5;  // [0;1] global randomness factor (0 = no randomness)
    private static final double CRASH_DURATION = 200;       // [tick]
    private static final double CRASH_CHANCE = 0.008;       // [0;1]
    private static final double DISTANCE_PER_TICK = 5;      // [meter], base velocity


    public Racer(String name, Car car, Color color, boolean displayLogs) {
        this.name = name;
        this.car = car;
        this.color = color;
        this.displayLogs = displayLogs;

        // Apply Car's Stats to global constants
        Stats carStats = car.getStats();
        crashDuration = CRASH_DURATION * (1 - Stats.toPercent(carStats.getResistance()));
        crashChance = CRASH_CHANCE * (1 - Stats.toPercent(carStats.getManiability()));
        distancePerTick = DISTANCE_PER_TICK * Stats.toPercent(carStats.getSpeed());
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

    /**
     * Simple hasFinished getter
     * @return hasFinished
     */
    public boolean hasFinished() {
        return hasFinished;
    }

    /**
     * Set hasFinished
     * @param hasFinished has finished boolean
     */
    public void setHasFinished(boolean hasFinished) {
        this.hasFinished = hasFinished;
    }

    /**
     * Check if the Racer is crashed
     * @return true if Racer is currently crashed
     */
    public boolean isCrashed() {
        return crashTimer > 0;
    }

    /**
     * Remaining crash time in percent
     * 1 = just began crash, 0 = finished crash
     * @return percent [0;1]
     */
    public double crashProgression() {
        return crashTimer / crashTimeout;
    }

    /**
     * Make the Racer run with randomness and chances to crash
     * c.f. global parameters constants above
     */
    public void runOneTick() {
        if(crashTimer > 0) {
            if(--crashTimer == 0) {
                displayLog(name + " gets back to race");
            }
        } else { // Racer runs
            if(rand.nextDouble() < crashChance) { // Bad luck, Racer crashes
                // Store crash time in crashTimeout and count down using crashTimer
                crashTimeout = crashDuration * globalRandomMultiplier(); // Racer has to wait crashDuration ticks
                crashTimer = crashTimeout;
                displayLog(name + " crashes and waits for " + crashTimeout);
            } else { // Racer runs normally
                double tickDistance = distancePerTick * globalRandomMultiplier();
                currentDistance += tickDistance;
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

    /**
     * Generates a factor between 1-GLOBAL_REALTIME_RANDOMNESS and 1+GLOBAL_REALTIME_RANDOMNESS)
     * e.g. if GLOBAL_REALTIME_RANDOMNESS = 0.4, methods return range will be [0.6;1.4]
     * @return factor in percent
     */
    private double globalRandomMultiplier() {
        //return 1 + (new Random()).nextDouble() * GLOBAL_REALTIME_RANDOMNESS * 2 - GLOBAL_REALTIME_RANDOMNESS;
        return 1 + REALTIME_RANDOMNESS * (2 * (new Random()).nextDouble() - 1);
    }

    @Override
    public int compareTo(Racer racer) {
        // Refactor to use only Double value ?
        return (int) (currentDistance - racer.getCurrentDistance());
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return new Racer(this.name, this.car, this.color, this.displayLogs);
    }
}
