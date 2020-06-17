package cars;

import cars.parts.CarPart;
import utils.Utils;

import java.util.ArrayList;

/**
 * MCR PROJECT : Builder Design Pattern
 * Author      : Bottin Stéphane, Demarta Robin, Dessaules Loïc, Kot Chau-Ying
 */
public class Car {

    private ArrayList<CarPart> carParts;
    private Stats stats;

    public Car() {
        this(new ArrayList<>());
    }

    public Car(ArrayList<CarPart> carParts) {
        stats = new Stats();
        setCarParts(carParts);
    }

    /**
     * Set the car parts and update the stats
     * @param carParts : car parts to add
     */
    public void setCarParts(ArrayList<CarPart> carParts) {
        this.carParts = carParts;
        updateStats();
    }

    /**
     * Get the car parts
     * @return car parts of the car
     */
    public ArrayList<CarPart> getCarParts() {
        return carParts;
    }

    /**
     * Get the stats
     * @return stats
     */
    public Stats getStats() {
        return stats;
    }

    /**
     * Update the stats depending on the car's car parts
     */
    private void updateStats() {
        stats.setSpeed(Utils.averageFunc(carParts, o -> o.getStats().getSpeed()));
        stats.setManiability(Utils.averageFunc(carParts, o -> o.getStats().getManiability()));
        stats.setResistance(Utils.averageFunc(carParts, o -> o.getStats().getResistance()));
    }

}
