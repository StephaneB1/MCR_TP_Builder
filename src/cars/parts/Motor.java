package cars.parts;

import cars.Stats;

import java.awt.*;

/**
 * MCR PROJECT : Builder Design Pattern
 * Author      : Bottin Stéphane, Demarta Robin, Dessaules Loïc, Kot Chau-Ying
 *
 * Description : class for the motor of a car
 */
public class Motor extends CarPart {

    public Motor(String name, String imageName, Stats stats) {
        super(name, imageName, stats, new Point(85, 40));
    }

    @Override
    public CarPart clone() {
        return new Motor(this.name, this.imageName, this.stats);
    }

    @Override
    String getResourceFolder() {
        return "motors";
    }

}
