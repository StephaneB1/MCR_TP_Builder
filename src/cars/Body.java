package cars;

import java.awt.*;

public class Body extends CarPart {

    private static String BODY_PATH = "resources/cars/bodies/";

    public Body(String name, String image, double acceleration, double weight, double adherence, double maniability, double resistance) {
        super(name, BODY_PATH + image, acceleration, weight, adherence, maniability, resistance, new Point(50, 50));
    }

    @Override
    public String getCategory() {
        return "Body";
    }

    @Override
    public int getLayerIndex() {
        return 0;
    }
}
