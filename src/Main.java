import carBuilder.CarBuilder;
import cars.Body;
import cars.Car;
import cars.Motor;
import cars.Spoiler;

import java.awt.*;

public class Main {

    public static void main(String[] args) {
        new Controller();

       /* Car builder = Controller.createNewCar("test", Color.RED)
                .buildBody(new Body("Body", "bodyTemplate.png", 1, 1, 1, 1, 11))
                .buildMotor(new Motor("Motor", "motorTemplate.png",1,1,1,1,1,new Point()))
                .buildSpoiler(new Spoiler("Motor", "spoilerTemplate.png",1,1,1,1,1,new Point()))
                .getCar();

        builder.getAcceleration();*/

    }

}
