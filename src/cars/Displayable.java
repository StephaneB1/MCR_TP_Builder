package cars;
import java.awt.*;
import java.awt.image.BufferedImage;

public interface Displayable {

    BufferedImage getImage();
    BufferedImage getTintedImage(Color color);

    int getXCoord();
    int getYCoord();
    int getLayerIndex();

}
