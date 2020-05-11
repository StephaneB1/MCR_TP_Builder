package cars;

import java.awt.*;

public class Tires extends CarPart {

    private static String TIRES_PATH = "resources/cars/tires/";

    public Tires(String name, String image, double acceleration, double weight, double adherence, double maniability, double resistance, Point relCoord) {
        super(name, TIRES_PATH + image, acceleration, weight, adherence, maniability, resistance, relCoord);
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
