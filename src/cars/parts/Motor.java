package cars.parts;

import cars.Stats;

import java.awt.*;

public class Motor extends CarPart {

    public Motor(String name, String imageName, Stats stats, Point relCoord) {
        super(name, imageName, stats, relCoord);
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
        return new Motor(this.name, this.imageName, this.stats, this.relCoord);
    }

    @Override
    String getResourceFolder() {
        return "motors";
    }

}
