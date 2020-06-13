package cars.parts;

import cars.Stats;

import java.awt.*;

public class Spoiler extends CarPart {

    public static String SPOILER_PATH = "resources/cars/spoilers/";

    public Spoiler(String name, String image, Stats stats, Point relCoord) {
        super(name, image, stats, relCoord);
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
        return new Spoiler(this.name, this.imagePath, this.stats, this.relCoord);
    }
}
