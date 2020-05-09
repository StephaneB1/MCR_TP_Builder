import cars.*;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
        Body body1 = new Body("Body-1", "resources/cars/bodies/body-1.png", 2, 2, 2, 2, 2);
        Motor motor1 = new Motor("Motor-1", "resources/cars/motors/motor-1.png", 2, 2, 2, 2, 2);
        Tires tires1 = new Tires("Tires-1", "resources/cars/tires/tires-1.png", 2, 2, 2, 2, 2);
        Spoiler spoiler1 = new Spoiler("Spoiler-1", "resources/cars/spoilers/spoiler-1.png", 2, 2, 2, 2, 2);

        Car car = new Car("Car-1", body1, motor1, tires1, spoiler1, Color.RED);

        JFrame jFrame = new JFrame();
        jFrame.setTitle("Car builder");
        jFrame.setSize(800, 800);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setLayout(null);

        jFrame.add(car);
        car.setLocation(200, 200);

        jFrame.setVisible(true);
    }


}
