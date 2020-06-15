package cars.parts;

import cars.Stats;

import java.awt.*;

/**
 * MCR PROJECT : Builder Design Pattern
 * Author      : Bottin Stéphane, Demarta Robin, Dessaules Loïc, Kot Chau-Ying
 *
 * Description : class for the spoiler of a car
 */
public class Spoiler extends CarPart {

    public Spoiler(String name, String imageName, Stats stats) {
        super(name, imageName, stats, new Point(55, 75));
    }

    @Override
    public CarPart clone() {
        return new Spoiler(this.name, this.imageName, this.stats);
    }
    @Override
    String getResourceFolder() {
        return "spoilers";
    }
}
