package cars.parts;

import cars.Stats;

import java.awt.*;

public class Tires extends CarPart {

    public Tires(String name, String imageName, Stats stats, Point relCoord, int duplicateDistance) {
        super(name, imageName, relCoord, true, duplicateDistance, stats );
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
        return new Tires(this.name, this.imageName, this.stats, this.relCoord, this.duplicateDistance);
    }

    @Override
    String getResourceFolder() {
        return "tires";
    }
}
