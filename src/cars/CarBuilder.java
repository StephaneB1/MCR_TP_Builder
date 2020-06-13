package cars;

import cars.parts.*;
import garage.Garage;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class CarBuilder {

    private Motor motor;
    private Body body;
    private Tires tire;
    private Spoiler spoiler;

    public CarBuilder() {
        super();
        motor = null;
        body = null;
        tire = null;
        spoiler = null;
    }

    public CarBuilder buildMotor(Motor motor) {
        if(body == null)
            return null;

        this.motor = motor;
        return this;
    }

    public CarBuilder buildBody(Body body) {
        if(body == null)
            return null;

        this.body = body;
        return this;
    }

    public CarBuilder buildTire(Tires tires) {
        if (body == null) {
            return null;
        }
        this.tire = tires;
        return this;
    }

    public CarBuilder buildSpoiler(Spoiler spoiler) {
        if (body == null) {
            return null;
        }
        this.spoiler = spoiler;
        return this;
    }

    public Car getCar() {
        if (motor != null && tire != null) {
            return new Car(getCarParts());
        } else {
            return null;
        }
    }

    public ArrayList<CarPart> getCarParts() {
        return new ArrayList<>(Arrays.asList(body, motor, tire, spoiler));
    }

    private CarPart getRandCarPart(Garage garage, Random rand, int category) {
        CarPart result = garage.getInventory().get(category).getProducts()
                .get(rand.nextInt(garage.getInventory().get(category)
                        .getProducts().size())).clone();
        result.setColor(garage.getPaintJobs().get(rand.nextInt(garage.getPaintJobs().size())).getColor());
        return result;
    }

    public void buildRandomCar(Garage garage) {
        Random random = new Random();
        this.buildBody((Body) getRandCarPart(garage, random, Garage.CATEGORY_BODY))
                .buildMotor((Motor) getRandCarPart(garage, random, Garage.CATEGORY_MOTORS))
                .buildTire((Tires) getRandCarPart(garage, random, Garage.CATEGORY_TIRES))
                .buildSpoiler((Spoiler) getRandCarPart(garage, random, Garage.CATEGORY_SPOILERS));
    }

}
