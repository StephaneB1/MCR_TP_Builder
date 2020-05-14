package carBuilder;

import cars.*;

import java.awt.*;

public class CarBuilder implements EmtpyCar, CarWithBody {

    private Motor motor;
    private Body body;
    private Tires tire;
    private Spoiler spoiler;
    private String name;
    private Color color;

    public CarBuilder(String name, Color color) {
        this.name = name;
        this.color = color;
        motor = null;
        body = null;
        tire = null;
        spoiler = null;
    }

    @Override
    public CarWithBody buildMotor(Motor motor) {
        this.motor = motor;
        return this;
    }

    @Override
    public CarWithBody buildBody(Body body) {
        this.body = body;
        return this;
    }

    @Override
    public CarWithBody buildTire(Tires tires) {
        this.tire = tires;
        return this;
    }

    @Override
    public CarWithBody buildSpoiler(Spoiler spoiler) {
        this.spoiler = spoiler;
        return this;
    }

    @Override
    public Car getCar() {
        if(motor != null && motor != null &&  tire != null) {
            return new Car(name, body, motor, tire, spoiler, color);
        }else{
            throw new IllegalArgumentException("Missing some part");
        }
    }
}
