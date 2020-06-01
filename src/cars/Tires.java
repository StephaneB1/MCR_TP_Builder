package cars;

import java.awt.*;

public class Tires extends CarPart {

    public static String TIRES_PATH = "resources/cars/tires/";

    public Tires(String name, String image, Stats stats, Point relCoord) {
        super(name, image, stats, relCoord);
    }

    @Override
    public String getCategory() {
        return "Tires";
    }

    @Override
    public int getLayerIndex() {
        return 1;
    }

    @Override
    public CarPart clone() {
        return new Tires(this.name, this.imagePath, this.stats, this.relCoord);
    }
}
