package cars.parts;

import cars.Stats;

import java.awt.*;

public class Spoiler extends CarPart {

    public Spoiler(String name, String imageName, Stats stats, Point relCoord) {
        super(name, imageName, stats, relCoord);
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
        return new Spoiler(this.name, this.imageName, this.stats, this.relCoord);
    }
    @Override
    String getResourceFolder() {
        return "spoilers";
    }
}
