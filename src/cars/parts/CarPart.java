package cars.parts;

import cars.Displayable;
import cars.Stats;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

public abstract class CarPart implements Displayable, Cloneable {

    String name;

    // Graphic display
    String imagePath;
    BufferedImage image;
    Point relCoord;
    Color color;

    boolean isDuplicateOnX;
    int duplicateDistance;

    // Stats
    Stats stats;

    public CarPart(String name, String imagePath, Point relCoord, boolean isDuplicateOnX, int duplicateDistance, Stats stats) {
        this.name = name;
        this.stats = stats;
        this.imagePath = imagePath;
        this.relCoord = relCoord;
        this.color = Color.WHITE;
        this.isDuplicateOnX = isDuplicateOnX;
        this.duplicateDistance = duplicateDistance;
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


    public CarPart(String name, String imagePath, Stats stats, Point relCoord) {
        this(name, imagePath, relCoord, false, 0, stats);
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
        return image;
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

    @Override
    public void drawPart(Graphics g, double ratio, ImageObserver observer, boolean simulation) {
        if (ratio < 1) {
            Image sizedImage = tintImage(image, color).getScaledInstance(
                    (int) (image.getWidth() * ratio),
                    (int) (image.getHeight() * ratio),
                    Image.SCALE_DEFAULT);
            g.drawImage(sizedImage, (int) (relCoord.x * ratio), (int) (relCoord.y * ratio), observer);
            if (isDuplicateOnX){
                g.drawImage(sizedImage, (int) ((relCoord.x + duplicateDistance) * ratio),  (int) (relCoord.y * ratio) ,observer);
            }
        } else {
            g.drawImage(tintImage(image, color), relCoord.x, relCoord.y, observer);
            if (isDuplicateOnX){
                g.drawImage(tintImage(image, color), relCoord.x + duplicateDistance, relCoord.y,observer);
            }
        }
    }

    public int getDuplicateDistanceX() {
        return duplicateDistance;
    }

    private BufferedImage tintImage(BufferedImage loadImg, Color color) {
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

    private int getHalfAlphaTint(int original, int tint) {
        return (int) (original + (tint - original) * 0.5);
    }

    private int getGrayScaleTint(int original, int tint) {
        double whitePercentage = original / 255.0;
        double blackPercentage = 1 - whitePercentage;

        return (int) (original * blackPercentage + tint * whitePercentage);
    }

    // Metallic paint job in case we add different textures (found the formula by mistake)
    private int getTintMetallic(int original, int tint) {
        double whitePercentage = original / 255.0;
        double blackPercentage = 1 - whitePercentage;

        return (int) (original * (((blackPercentage * tint)) % 1) + tint * whitePercentage) % 255;
    }
}
