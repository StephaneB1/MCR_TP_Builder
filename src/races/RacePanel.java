package races;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class RacePanel extends JPanel {

    private ArrayList<Racer> racers;
    private Racer player1Racer;
    private Racer player2Racer;
    private int totalDistance;
    private int raceWidth;
    private int raceBeginX;
    private int raceBeginY;
    private final int RACER_SIZE = 28;

    private JLabel lblDistanceTraveled;
    private JLabel lblPlayer1;
    private JLabel lblPlayer2;

    public RacePanel(int width, int height, ArrayList<Racer> racers, int totalDistance) {
        this.setSize(width, height);
        this.player1Racer = racers.get(0);
        this.player2Racer = racers.get(1);

        // Race width (black line) is the 70% of the windows (15% margin left, 15% margin right)
        this.raceWidth = width - (2 * (width * 15 / 100));
        this.raceBeginX = width * 15 / 100;

        this.racers = racers;
        this.totalDistance = totalDistance;

        this.raceBeginY = height * 40 / 100;
        this.lblDistanceTraveled = new JLabel(racers.get(0).getCurrentDistance() + " / " + totalDistance + " meters traveled", SwingConstants.CENTER);
        this.lblDistanceTraveled.setFont(new Font("Arial", Font.BOLD, 18));

        this.lblPlayer1 = new JLabel("P1");
        this.lblPlayer2 = new JLabel("P2");
        this.lblPlayer1.setForeground(player1Racer.getColor());
        this.lblPlayer2.setForeground(player2Racer.getColor());
        this.add(lblDistanceTraveled);
        this.add(lblPlayer1);
        this.add(lblPlayer2);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(4.0F));
        g2d.setColor(Color.BLACK);
        g2d.drawLine(raceBeginX, raceBeginY + (RACER_SIZE / 2), raceWidth + raceBeginX, raceBeginY + (RACER_SIZE / 2));

        for(Racer racer : racers){
            int distanceInPixel = (int)(racer.getCurrentDistance() * raceWidth / totalDistance);
            int racerPosX = (raceBeginX - RACER_SIZE / 2) + distanceInPixel;
            int racerPosY = raceBeginY;

            // Draw the "you" to display which racer is the current player racer
            if(racer.equals(player1Racer)){
                lblPlayer1.setBounds(racerPosX + (RACER_SIZE / 2) - (lblPlayer1.getPreferredSize().width / 2), racerPosY + RACER_SIZE, lblPlayer1.getPreferredSize().width, lblPlayer1.getPreferredSize().height);
            }else if(racer.equals(player2Racer)){
                lblPlayer2.setBounds(racerPosX + (RACER_SIZE / 2) - (lblPlayer2.getPreferredSize().width / 2),  racerPosY + RACER_SIZE, lblPlayer2.getPreferredSize().width, lblPlayer2.getPreferredSize().height);
            }

            g2d.setColor(racer.getColor());
            g2d.fillOval(racerPosX, racerPosY, RACER_SIZE, RACER_SIZE);
        }

        this.lblDistanceTraveled.setText((int)racers.get(0).getCurrentDistance() + " / " + totalDistance + " meters traveled");
    }
}
