package cars;

import java.awt.*;

public class Body extends CarPart {

    private static String BODY_PATH = "resources/cars/bodies/";

    public Body(String name, String image, Stats stats) {
        super(name, BODY_PATH + image, stats, new Point(50, 50));
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
