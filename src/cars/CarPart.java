package cars;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public abstract class CarPart implements Displayable, Cloneable {

    String name;

    // Graphic display
    String imagePath;
    BufferedImage image;
    Point relCoord;
    Color color;

    // Stats
    Stats stats;

    public CarPart(String name, String imagePath, Stats stats, Point relCoord) {
        this.name = name;
        this.stats = stats;
        this.imagePath = imagePath;
        this.relCoord = relCoord;
        this.color = Color.WHITE;
        try {
            BufferedImage in = ImageIO.read(new File(imagePath));

            image = new BufferedImage(
                    in.getWidth(), in.getHeight(), BufferedImage.TRANSLUCENT);

            Graphics2D g = image.createGraphics();
            g.drawImage(in, 0, 0, null);
            g.dispose();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public abstract String getCategory();

    public abstract CarPart clone();

    public Stats getStats() {
        return stats;
    }

    public String getName() {
        return name;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public BufferedImage getImage() {
        return tintImage(image, color);
    }

    @Override
    public BufferedImage getTintedImage(Color color) {
        return tintImage(image, color);
    }

    @Override
    public int getXCoord() {
        return relCoord.x;
    }

    @Override
    public int getYCoord() {
        return relCoord.y;
    }

    public BufferedImage tintImage(BufferedImage loadImg, Color color) {
        BufferedImage result = new BufferedImage(loadImg.getWidth(),
                loadImg.getHeight(), BufferedImage.TYPE_INT_ARGB);
        for(int i = 0; i < loadImg.getWidth(); ++i) {
            for(int j = 0; j < loadImg.getHeight(); ++j) {
                Color pixelColor = new Color(loadImg.getRGB(i, j) , true);

                Color tintedColor = new Color(getTint(pixelColor.getRed(), color.getRed()),
                        getTint(pixelColor.getGreen(), color.getGreen()),
                        getTint(pixelColor.getBlue(), color.getBlue()));

                if(pixelColor.getAlpha() != 0) {
                    result.setRGB(i, j, tintedColor.getRGB());
                }
            }
        }

        return result;
    }

    private int getTint(int original, int tint) {
        double whitePercentage = original / 255.0;

        return (int) ((original * (1 - whitePercentage)) + (tint * whitePercentage));
    }
}
