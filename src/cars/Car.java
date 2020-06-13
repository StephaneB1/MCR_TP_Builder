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
        this.carParts = carParts;
        stats = new Stats();
    }

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

    public void updateStats() {
        Stats.updateCarPartStats(stats, carParts);
    }

}
