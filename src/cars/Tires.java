package cars;

import java.awt.*;

public class Tires extends CarPart {

    private static String TIRES_PATH = "resources/cars/tires/";

    public Tires(String name, String image, Stats stats, Point relCoord) {
        super(name, TIRES_PATH + image, stats, relCoord);
    }

    @Override
    public String getCategory() {
        return "Tires";
    }

    @Override
    public int getLayerIndex() {
        return 1;
    }
}
