import cars.*;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Garage {

    private ArrayList<Body> bodies;
    private ArrayList<Motor> motors;
    private ArrayList<Tires> tires;
    private ArrayList<Spoiler> spoilers;

    private ArrayList<Car> cars;

    public Garage() {
        cars = new ArrayList<>();

        Body body1 = new Body("Body-1", "resources/cars/bodies/body-1.png", 2, 2, 2, 2, 2);
        Motor motor1 = new Motor("Motor-1", "resources/cars/motors/motor-1.png", 2, 2, 2, 2, 2);
        Tires tires1 = new Tires("Tires-1", "resources/cars/tires/tires-1.png", 2, 2, 2, 2, 2);
        Spoiler spoiler1 = new Spoiler("Spoiler-1", "resources/cars/spoilers/spoiler-1.png", 2, 2, 2, 2, 2);

        cars.add(new Car("Car-1", body1, motor1, tires1, spoiler1, Color.RED));
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
}
