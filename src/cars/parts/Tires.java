package cars.parts;

import cars.Stats;

import java.awt.*;
import java.awt.image.ImageObserver;

public class Tires extends CarPart {

    private static int TIRE_DISTANCE = 210;

    public Tires(String name, String imageName, Stats stats) {
        super(name, imageName, stats, new Point(90, 120));
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
        return new Tires(this.name, this.imageName, this.stats);
    }

    @Override
    String getResourceFolder() {
        return "tires";
    }

    /**
     * Tires are displayed twice with the same source image
     */
    @Override
    public void drawPart(Graphics g, double ratio, ImageObserver observer) {
        super.drawPart(g, ratio, observer);
        if (ratio < 1) {
            Image sizedImage = tintImage(image, color).getScaledInstance(
                    (int) (image.getWidth() * ratio),
                    (int) (image.getHeight() * ratio),
                    Image.SCALE_DEFAULT);
            g.drawImage(sizedImage, (int) ((relCoord.x + TIRE_DISTANCE) * ratio),  (int) (relCoord.y * ratio), observer);
        } else {
            g.drawImage(tintImage(image, color), relCoord.x + TIRE_DISTANCE, relCoord.y, observer);
        }
    }
}
