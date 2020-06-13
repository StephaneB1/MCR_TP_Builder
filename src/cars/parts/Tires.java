package cars.parts;

import cars.Stats;

import java.awt.*;
import java.awt.image.ImageObserver;

public class Tires extends CarPart {

    public static String TIRES_PATH = "resources/cars/tires/";

    public Tires(String name, String image, Stats stats, Point relCoord, int duplicateDistance) {
        super(name, image, relCoord, true, duplicateDistance, stats );
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
        return new Tires(this.name, this.imagePath, this.stats, this.relCoord, this.duplicateDistance);
    }

    @Override
    public void drawPart(Graphics g, double ratio, ImageObserver observer, boolean simulation) {
        System.out.println("Drawing from Tires");
        if(simulation)
            return;

        super.drawPart(g, ratio, observer, false);
    }
}
