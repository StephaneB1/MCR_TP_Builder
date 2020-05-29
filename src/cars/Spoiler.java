package cars;

import java.awt.*;

public class Spoiler extends CarPart {

    private static String SPOILER_PATH = "resources/cars/spoilers/";

    public Spoiler(String name, String image, Stats stats, Point relCoord) {
        super(name, SPOILER_PATH + image, stats, relCoord);
    }

    @Override
    public String getCategory() {
        return "Spoiler";
    }

    @Override
    public int getLayerIndex() {
        return 1;
    }
}
