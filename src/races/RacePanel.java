package races;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class RacePanel extends JPanel {

    private ArrayList<Racer> racers;
    private int totalDistance;
    private final int RACE_WIDTH = 600;
    private final int RACE_BEGIN_X = 50;
    private final int RACE_BEGIN_Y = 50;
    private final int RACER_SIZE = 28;

    public RacePanel(int width, int height, ArrayList<Racer> racers, int totalDistance) {
        this.racers = racers;
        this.totalDistance = totalDistance;

        this.setSize(width, height);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(4.0F));
        g2d.setColor(Color.BLACK);
        g2d.drawLine(RACE_BEGIN_X, RACE_BEGIN_Y + (RACER_SIZE / 2), RACE_WIDTH + RACE_BEGIN_X, RACE_BEGIN_Y + (RACER_SIZE / 2));

        for(Racer racer : racers){
            int distanceInPixel = (int)(racer.getCurrentDistanceMeter() * RACE_WIDTH / totalDistance);
            g2d.setColor(racer.getColor());
            g2d.fillOval((int)(RACE_BEGIN_X + distanceInPixel), RACE_BEGIN_Y, RACER_SIZE, RACER_SIZE);
        }
    }
}
