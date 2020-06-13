package cars.parts;

import cars.Stats;

import java.awt.*;

public class Motor extends CarPart {

    public static String MOTOR_PATH = "resources/cars/motors/";

    public Motor(String name, String image, Stats stats, Point relCoord) {
        super(name, image, stats, relCoord);
    }

    @Override
    public String getCategory() {
        return "Motor";
    }

    @Override
    public int getLayerIndex() {
        return 1;
    }

    @Override
    public CarPart clone() {
        return new Motor(this.name, this.imagePath, this.stats, this.relCoord);
    }
}
