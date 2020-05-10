import cars.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Garage {

    private ArrayList<Body> bodies;
    private ArrayList<Motor> motors;
    private ArrayList<Tires> tires;
    private ArrayList<Spoiler> spoilers;

    private ArrayList<Car> cars;

    public Garage() {

        //---------------------------------//
        // GARAGE INVENTORY                //
        //---------------------------------//
        // Car bodies
        bodies   = new ArrayList<>();
        bodies.add(new Body("Body-1", "resources/cars/bodies/body-1.png", 2, 2, 2, 2, 2));
        // Car motors
        motors   = new ArrayList<>();
        motors.add(new Motor("Motor-1", "resources/cars/motors/motor-1.png", 2, 2, 2, 2, 2));
        // Car tires
        tires    = new ArrayList<>();
        tires.add(new Tires("Tires-1", "resources/cars/tires/tires-1.png", 2, 2, 2, 2, 2));
        // Car spoilers
        spoilers = new ArrayList<>();
        spoilers.add(new Spoiler("Spoiler-1", "resources/cars/spoilers/spoiler-1.png", 2, 2, 2, 2, 2));
        // Cars
        cars = new ArrayList<>();
        cars.add(getRandomCar());
    }

    public ArrayList<Body> getBodies() {
        return bodies;
    }

    public ArrayList<Motor> getMotors() {
        return motors;
    }

    public ArrayList<Tires> getTires() {
        return tires;
    }

    public ArrayList<Spoiler> getSpoilers() {
        return spoilers;
    }

    public ArrayList<Car> getCars() {
        return cars;
    }

    public Car getRandomCar() {
        return new Car(
                "Carx",
                bodies.get(new Random().nextInt(bodies.size())),
                motors.get(new Random().nextInt(tires.size())),
                tires.get(new Random().nextInt(tires.size())),
                spoilers.get(new Random().nextInt(tires.size())),
                Color.RED);
    }
}
