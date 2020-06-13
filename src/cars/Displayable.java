package cars;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

public interface Displayable {

    BufferedImage getImage();
    BufferedImage getTintedImage(Color color);

    int getXCoord();
    int getYCoord();
    int getLayerIndex();

    void drawPart(Graphics g, double ratio, ImageObserver observer);

}
