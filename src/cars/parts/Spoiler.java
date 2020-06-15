package cars.parts;

import cars.Stats;

import java.awt.*;

public class Spoiler extends CarPart {

    public Spoiler(String name, String imageName, Stats stats) {
        super(name, imageName, stats, new Point(55, 75));
    }

    @Override
    public String getCategory() {
        return "Spoiler";
    }

    @Override
    public int getLayerIndex() {
        return 1;
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
