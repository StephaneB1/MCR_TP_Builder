package cars.parts;

import cars.Stats;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

/**
 * MCR PROJECT : Builder Design Pattern
 * Author      : Bottin Stéphane, Demarta Robin, Dessaules Loïc, Kot Chau-Ying
 *
 * Description : abstract class that defines a car part
 */
public abstract class CarPart implements Cloneable {

    protected String name;

    // Graphic display
    protected String imageName;
    protected BufferedImage image;
    protected Point relCoord;
    protected Color color;

    // Stats
    protected Stats stats;

    public CarPart(String name, String imageName, Stats stats, Point relCoord) {
        this.name = name;
        this.stats = stats;
        this.imageName = imageName;
        this.relCoord = relCoord;
        this.color = Color.WHITE;

        try {
            image = ImageIO.read(new File("resources/cars/" +
                    getResourceFolder() + "/" + imageName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Enable to clone a part so that the garage keeps the originals
     * @return clone of the car part
     */
    public abstract CarPart clone();

    /**
     * Get the resource folder for the car parts' images
     * @return resource folder path
     */
    abstract String getResourceFolder();

    /**
     * Get the stats
     * @return stats
     */
    public Stats getStats() {
        return stats;
    }

    /**
     * Get the name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the color of the part
     * @param color : color to paint
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Get the color
     * @return color
     */
    public Color getColor() {
        return color;
    }

    /**
     * Get the tinted version of the car part
     * @param color : color to paint the car part
     * @return tinted buffered image
     */
    public BufferedImage getTintedImage(Color color) {
        return tintImage(image, color);
    }

    /**
     * Draws the car part on a given graphic
     * @param g        : graphic
     * @param ratio    : ratio of the car part
     */
    public void drawPart(Graphics g, double ratio) {
        if (ratio < 1) {
            // draw a resized image anchored at the center
            Image sizedImage = tintImage(image, color).getScaledInstance(
                    (int) (image.getWidth() * ratio),
                    (int) (image.getHeight() * ratio),
                    Image.SCALE_DEFAULT);
            g.drawImage(sizedImage, (int) (relCoord.x * ratio), (int) (relCoord.y * ratio), null);
        } else {
            g.drawImage(tintImage(image, color), relCoord.x, relCoord.y, null);
        }
    }

    BufferedImage tintImage(BufferedImage loadImg, Color color) {
        BufferedImage result = new BufferedImage(loadImg.getWidth(),
                loadImg.getHeight(), BufferedImage.TYPE_INT_ARGB);
        for(int i = 0; i < loadImg.getWidth(); ++i) {
            for(int j = 0; j < loadImg.getHeight(); ++j) {
                Color pixelColor = new Color(loadImg.getRGB(i, j) , true);

                Color tintedColor = new Color(
                        getGrayScaleTint(pixelColor.getRed(), color.getRed()),
                        getGrayScaleTint(pixelColor.getGreen(), color.getGreen()),
                        getGrayScaleTint(pixelColor.getBlue(), color.getBlue())
                );

                if(pixelColor.getAlpha() != 0) {
                    result.setRGB(i, j, tintedColor.getRGB());
                }
            }
        }

        return result;
    }

    private int getGrayScaleTint(int original, int tint) {
        double whitePercentage = original / 255.0;
        double blackPercentage = 1 - whitePercentage;

        return (int) (original * blackPercentage + tint * whitePercentage);
    }

}
