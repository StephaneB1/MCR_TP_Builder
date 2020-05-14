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

    public CarBuilder() {
        name = null;
        color = null;
        motor = null;
        body = null;
        tire = null;
        spoiler = null;
    }

    @Override
    public EmtpyCar setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public EmtpyCar setColor(Color color) {
        this.color = color;
        return this;
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
        if(motor != null && spoiler != null &&  tire != null && name != null && color != null) {
            return new Car(name, body, motor, tire, spoiler, color);
        }else{
            throw new IllegalArgumentException("Missing some part");
        }
    }
}
