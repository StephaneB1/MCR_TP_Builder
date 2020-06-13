package cars.parts;

import cars.Stats;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

public class Tires extends CarPart {

    public static String TIRES_PATH = "resources/cars/tires/";

    private BufferedImage rotated;
    private boolean timerSet = false;

    public Tires(String name, String image, Stats stats, Point relCoord, int duplicateDistance) {
        super(name, image, relCoord, true, duplicateDistance, stats );
        rotated = rotateImageByDegrees(this.image, 0.0, null);
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
        if(simulation) {
            if(!timerSet) {
                // Delay would have to be divided by the speed
                Timer timer = new Timer(10, new ActionListener() {
                    private double angle = 0;
                    private double delta = 10.0;

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        angle += delta;
                        rotated = rotateImageByDegrees(image, angle, observer);
                        ((JPanel) observer).repaint();
                    }
                });
                timer.start();
                timerSet = true;
            }

            if (rotated != null) {
                Graphics2D g2d = (Graphics2D) g.create();
                int x = (image.getWidth() - rotated.getWidth()) / 2;
                int y = (image.getHeight() - rotated.getHeight()) / 2;
                g2d.drawImage(rotated, x, y, observer);
                g2d.dispose();
            }
        } else {
            super.drawPart(g, ratio, observer, false);
        }
    }

    public BufferedImage rotateImageByDegrees(BufferedImage img, double angle, ImageObserver observer) {

        double rads = Math.toRadians(angle);
        double sin = Math.abs(Math.sin(rads)), cos = Math.abs(Math.cos(rads));
        int w = img.getWidth();
        int h = img.getHeight();
        int newWidth = (int) Math.floor(w * cos + h * sin);
        int newHeight = (int) Math.floor(h * cos + w * sin);

        BufferedImage rotated = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = rotated.createGraphics();
        AffineTransform at = new AffineTransform();
        at.translate((newWidth - w) / 2, (newHeight - h) / 2);

        int x = w / 2;
        int y = h / 2;

        at.rotate(rads, x, y);
        g2d.setTransform(at);
        g2d.drawImage(img, 0, 0, observer);
        g2d.dispose();

        return rotated;
    }
}
