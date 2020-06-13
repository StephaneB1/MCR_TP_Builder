package cars.parts;

import cars.Stats;

import java.awt.*;
import java.awt.image.ImageObserver;

public class Spoiler extends CarPart {

    public static String SPOILER_PATH = "resources/cars/spoilers/";

    public Spoiler(String name, String image, Stats stats, Point relCoord) {
        super(name, image, stats, relCoord);
    }

    @Override
    public String getCategory() {
        return "Spoiler";
    }

    @Override
    public int getLayerIndex() {
        return 1;
    }

    @Override
    public CarPart clone() {
        return new Spoiler(this.name, this.imagePath, this.stats, this.relCoord);
    }


    @Override
    public void drawPart(Graphics g, double ratio, ImageObserver observer, boolean simulation) {
        System.out.println("Drawing from Spoiler");
        super.drawPart(g, ratio, observer, simulation);
    }
}
