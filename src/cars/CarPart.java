package cars;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public abstract class CarPart implements Displayable {

    private String name;

    // Graphic display
    private BufferedImage image;
    private Point relCoord;
    private Color color;

    // Stats
    private Stats stats;

    public CarPart(String name, String imagePath, Stats stats, Point relCoord) {
        this.name = name;
        this.stats = stats;
        this.relCoord = relCoord;
        this.color = Color.BLACK;
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
        return getTintedImage(image, color);
    }

    @Override
    public int getXCoord() {
        return relCoord.x;
    }

    @Override
    public int getYCoord() {
        return relCoord.y;
    }

    private BufferedImage getTintedImage(BufferedImage loadImg, Color color) {
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
        // 255 -> 1.0
        // 0   -> 0.0
        double whitePercentage = original / 255.0;

        return (int) ((original * (1 - whitePercentage)) + (tint * whitePercentage));
    }
}
