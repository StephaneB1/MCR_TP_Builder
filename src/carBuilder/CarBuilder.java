package carBuilder;

import cars.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class CarBuilder implements EmptyCar, CarWithBody {

    private Motor motor;
    private Body body;
    private Tires tire;
    private Spoiler spoiler;
    private String name;
    private Color color;

    private Stats tempStats; // to show blueprint stats even if the car is not build yet

    public CarBuilder() {
        name = null;
        color = null;
        motor = null;
        body = null;
        tire = null;
        spoiler = null;
        tempStats = new Stats();
    }

    public EmptyCar getNewCar() {
        return new CarBuilder();
    }

    @Override
    public CarWithBody setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public CarWithBody setColor(Color color) {
        this.color = color;
        return this;
    }

    @Override
    public CarWithBody buildMotor(Motor motor) {
        // Check body etc...
        this.motor = motor;
        updateStats();
        return this;
    }

    @Override
    public CarWithBody buildBody(Body body) {
        this.body = body;
        updateStats();
        return this;
    }

    @Override
    public CarWithBody buildTire(Tires tires) {
        this.tire = tires;
        updateStats();
        return this;
    }

    @Override
    public CarWithBody buildSpoiler(Spoiler spoiler) {
        this.spoiler = spoiler;
        updateStats();
        return this;
    }

    @Override
    public Car getCar() {                                      // temp, just to make the simple builder work
        if(motor != null && spoiler != null &&  tire != null /*&& name != null && color != null*/) {
            return new Car(name, body, motor, tire, spoiler, color);
        } else {
            return null;
        }
    }

    public ArrayList<CarPart> getCarParts() {
        return new ArrayList<>(Arrays.asList(body, motor, tire, spoiler));
    }

    public Stats getTempStats() {
        return tempStats;
    }

    private void updateStats() {
        Stats.updateCarPartStats(tempStats, new ArrayList<>(Arrays.asList(body, motor, tire, spoiler)));
    }
}
