package cars.parts;

import cars.Stats;

import java.awt.*;

/**
 * MCR PROJECT : Builder Design Pattern
 * Author      : Bottin Stéphane, Demarta Robin, Dessaules Loïc, Kot Chau-Ying
 *
 * Description : class for the body of a car
 */
public class Body extends CarPart {

    public Body(String name, String imageName, Stats stats) {
        super(name, imageName, stats, new Point(50, 50));
    }

    @Override
    public CarPart clone() {
        return new Body(this.name, this.imageName, this.stats);
    }

    @Override
    String getResourceFolder() {
        return "bodies";
    }
}
