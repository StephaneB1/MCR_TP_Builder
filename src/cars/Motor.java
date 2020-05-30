package cars;

import java.awt.*;

public class Motor extends CarPart {

    private static String MOTOR_PATH = "resources/cars/motors/";

    public Motor(String name, String image, Stats stats, Point relCoord) {
        super(name, MOTOR_PATH + image, stats, relCoord);
    }

    @Override
    public String getCategory() {
        return "Motor";
    }

    @Override
    public int getLayerIndex() {
        return 1;
    }
}
