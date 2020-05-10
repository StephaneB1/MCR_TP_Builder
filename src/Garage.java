import cars.Body;
import cars.Motor;
import cars.Spoiler;
import cars.Tires;

import java.util.ArrayList;

public class Garage {

    private ArrayList<Body> bodies;
    private ArrayList<Motor> motors;
    private ArrayList<Tires> tires;
    private ArrayList<Spoiler> spoilers;

    public Garage() {
        // TODO : construct the products
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
}
