package cars;

import java.awt.*;

public class Body extends CarPart {

    public static String BODY_PATH = "resources/cars/bodies/";

    public Body(String name, String image, Stats stats) {
        super(name, image, stats, new Point(50, 50));
    }

    @Override
    public String getCategory() {
        return "Body";
    }

    @Override
    public int getLayerIndex() {
        return 0;
    }

    @Override
    public CarPart clone() {
        return new Body(this.name, this.imagePath, this.stats);
    }
}
