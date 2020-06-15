package cars.parts;

import cars.Stats;

import java.awt.*;

public class Motor extends CarPart {

    public Motor(String name, String imageName, Stats stats) {
        super(name, imageName, stats, new Point(85, 40));
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
        return new Motor(this.name, this.imageName, this.stats);
    }

    @Override
    String getResourceFolder() {
        return "motors";
    }

}
