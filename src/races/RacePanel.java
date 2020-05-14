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
    private final int RACER_SIZE = 25;

    public RacePanel(int width, int height, ArrayList<Racer> racers, int totalDistance) {
        this.racers = racers;
        this.totalDistance = totalDistance;

        this.setSize(width, height);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.BLACK);
        g.drawLine(RACE_BEGIN_X, RACE_BEGIN_Y + (RACER_SIZE / 2), RACE_WIDTH + RACE_BEGIN_X, RACE_BEGIN_Y + (RACER_SIZE / 2));

        for(Racer racer : racers){
            int distanceInPixel = (int)(racer.getCurrentDistanceMeter() * RACE_WIDTH / totalDistance);
            g.setColor(racer.getColor());
            g.fillOval((int)(RACE_BEGIN_X + distanceInPixel), RACE_BEGIN_Y, RACER_SIZE, RACER_SIZE);
        }
    }
}
