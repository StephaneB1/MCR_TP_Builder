package cars;

import cars.parts.CarPart;

import java.util.ArrayList;

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
     * Add the list of car part to the car
     * @param carParts
     */
    public void setCarParts(ArrayList<CarPart> carParts) {
        this.carParts = carParts;
        updateStats();
    }

    public ArrayList<CarPart> getCarParts() {
        return carParts;
    }

    public Stats getStats() {
        return stats;
    }

    /**
     * Update the statistic for this car
     */
    public void updateStats() {
        Stats.updateCarPartStats(stats, carParts);
    }

}
