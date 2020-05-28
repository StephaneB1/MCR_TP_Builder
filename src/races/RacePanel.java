package races;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class RacePanel extends JPanel {

    private ArrayList<Racer> racers;
    private Racer playerRacer;
    private int totalDistance;
    private int raceWidth;
    private int raceBeginX;
    private int raceBeginY;
    private final int RACER_SIZE = 28;

    private JLabel lblDistanceTraveled;
    private JLabel lblYou;

    public RacePanel(int width, int height, ArrayList<Racer> racers, int totalDistance) {
        this.setSize(width, height);
        this.playerRacer = racers.get(0);

        // Race width (black line) is the 70% of the windows (15% margin left, 15% margin right)
        this.raceWidth = width - (2 * (width * 15 / 100));
        this.raceBeginX = width * 15 / 100;

        this.racers = racers;
        this.totalDistance = totalDistance;


        this.raceBeginY = height * 40 / 100;
        this.lblDistanceTraveled = new JLabel(racers.get(0).getCurrentDistanceMeter() + " / " + totalDistance + " meters traveled", SwingConstants.CENTER);
        this.lblDistanceTraveled.setFont(new Font("Arial", Font.BOLD, 18));

        this.lblYou = new JLabel("You");
        this.add(lblDistanceTraveled);
        this.add(lblYou);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(4.0F));
        g2d.setColor(Color.BLACK);
        g2d.drawLine(raceBeginX, raceBeginY + (RACER_SIZE / 2), raceWidth + raceBeginX, raceBeginY + (RACER_SIZE / 2));

        for(Racer racer : racers){
            int distanceInPixel = (int)(racer.getCurrentDistanceMeter() * raceWidth / totalDistance);
            int racerPosX = (raceBeginX - RACER_SIZE / 2) + distanceInPixel;
            int racerPosY = raceBeginY;

            // Draw the "you" to display which racer is the current player racer
            if(racer.equals(playerRacer)){
               lblYou.setBounds(racerPosX, racerPosY + RACER_SIZE, lblYou.getPreferredSize().width, lblYou.getPreferredSize().height);
            }

            g2d.setColor(racer.getColor());
            g2d.fillOval(racerPosX, racerPosY, RACER_SIZE, RACER_SIZE);
        }

        this.lblDistanceTraveled.setText((int)racers.get(0).getCurrentDistanceMeter() + " / " + totalDistance + " meters traveled");
    }
}
