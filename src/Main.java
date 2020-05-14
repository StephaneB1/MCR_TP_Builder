import carBuilder.CarBuilder;
import carBuilder.CarWithBody;
import carBuilder.EmtpyCar;
import cars.*;

import java.awt.*;

public class Main {

    public static void main(String[] args) {
        new Controller();

        EmtpyCar builder = Controller.createNewCar().setName("Test").setColor(Color.RED);
        CarWithBody carBody = builder
                .buildBody(new Body("Body", "bodyTemplate.png", 1, 1, 1, 1, 11));

        Car newCar = carBody
                .buildMotor(new Motor("Motor", "motorTemplate.png", 1, 1, 1, 1, 1, new Point()))
                .buildSpoiler(new Spoiler("Motor", "spoilerTemplate.png", 1, 1, 1, 1, 1, new Point()))
                .buildTire(new Tires("Tires", "tiresTemplate.png", 1, 1, 1, 1, 1, new Point()))
                .getCar();


        Car car = Controller.createNewCar()
                .setColor(Color.RED)
                .setName("Test")
                .buildBody(new Body("Body", "bodyTemplate.png", 1, 1, 1, 1, 11))
                .buildMotor(new Motor("Motor", "motorTemplate.png", 1, 1, 1, 1, 1, new Point()))
                .buildSpoiler(new Spoiler("Motor", "spoilerTemplate.png", 1, 1, 1, 1, 1, new Point()))
                .buildTire(new Tires("Tires", "tiresTemplate.png", 1, 1, 1, 1, 1, new Point()))
                .getCar();


    }

}
