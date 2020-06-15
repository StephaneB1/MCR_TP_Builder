package cars;

import cars.parts.*;
import garage.Garage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * MCR PROJECT : Builder Design Pattern
 * Author      : Bottin Stéphane, Demarta Robin, Dessaules Loïc, Kot Chau-Ying
 */
public class CarBuilder {

    private Motor motor;
    private Body body;
    private Tires tire;
    private Spoiler spoiler;

    public CarBuilder() {
        motor = null;
        body = null;
        tire = null;
        spoiler = null;
    }

    /**
     * Add a motor to the car builder
     *
     * @param motor
     * @return Null if the body isn't added to the builder, else the builder
     */
    public CarBuilder buildMotor(Motor motor) {
        if (body == null) { // Can't add a motor to a car without a body
            return null;
        }

        this.motor = motor;
        return this;
    }

    /**
     * Add a Body to the car builder
     *
     * @param body
     * @return the builder
     */
    public CarBuilder buildBody(Body body) {

        this.body = body;
        return this;
    }

    /**
     * Add tires to the car builder
     *
     * @param tires
     * @return Null if the body isn't added to the builder, else the builder
     */
    public CarBuilder buildTire(Tires tires) {
        if (body == null) {
            return null;
        }
        this.tire = tires;
        return this;
    }

    /**
     * Add a spoiler to the car builder
     *
     * @param spoiler
     * @return  Null if the body isn't added to the builder, else the builder
     */
    public CarBuilder buildSpoiler(Spoiler spoiler) {
        if (body == null) {
            return null;
        }
        this.spoiler = spoiler;
        return this;
    }

    /**
     * Build the car with car part added previously
     * @return Null if the motor and the tires ares missing, else a new Car
     */
    public Car getCar() {
        if (motor != null && tire != null) { // The car is incomplete without a motor and tires
            return new Car(getCarParts());
        } else {
            return null;
        }
    }

    /**
     * Return the car parts that added to the builder
     *
     * @return A list for CarPart
     */
    public ArrayList<CarPart> getCarParts() {
        return new ArrayList<>(Arrays.asList(body, motor, tire, spoiler));
    }


    /**
     * Put random car part in the builder
     * @param garage
     */
    public void buildRandomCar(Garage garage) {
        Random random = new Random();
        this.buildBody((Body) garage.getRandCarPart(random, Garage.CATEGORY_BODY))
                .buildMotor((Motor) garage.getRandCarPart(random, Garage.CATEGORY_MOTORS))
                .buildTire((Tires) garage.getRandCarPart(random, Garage.CATEGORY_TIRES))
                .buildSpoiler((Spoiler) garage.getRandCarPart(random, Garage.CATEGORY_SPOILERS));
    }

}
