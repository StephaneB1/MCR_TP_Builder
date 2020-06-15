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

    private JLabel lblPlayer1;
    private JLabel lblPlayer2;

    /**
     * MCR PROJECT : Builder Design Pattern
     * Author      : Bottin Stéphane, Demarta Robin, Dessaules Loïc, Kot Chau-Ying
     *
     * Description : RacePanel is the top Panel of the Race frame. We draw here the race line and all racers as
     * circles that will move by their distance increment.
     */
    public RacePanel(int width, int height, ArrayList<Racer> racers, int totalDistance) {
        this.setSize(width, height);
        setBackground(Color.WHITE);
        this.player1Racer = racers.get(0); // First racers always the player1
        this.player2Racer = racers.get(1); // Second racers always the player2

        // Race width (black line) is the 70% of the windows (15% margin left, 15% margin right)
        this.raceWidth = width - (2 * (width * 15 / 100));
        this.raceBeginX = width * 15 / 100;

        this.racers = racers;
        this.totalDistance = totalDistance;

        this.raceBeginY = height * 40 / 100;
        JLabel lblDistanceTraveled = new JLabel("Race distance: " + totalDistance + " meters", SwingConstants.CENTER);
        lblDistanceTraveled.setFont(new Font("Arial", Font.BOLD, 18));

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

        // Draw the race line
        g2d.setStroke(new BasicStroke(4.0F));
        g2d.setColor(Color.BLACK);
        g2d.drawLine(raceBeginX, raceBeginY + (RACER_SIZE / 2), raceWidth + raceBeginX, raceBeginY + (RACER_SIZE / 2));

        int racerPosX;
        int racerPosY;

        for(Racer racer : racers){
            // Racer is still running, update X pos
            if(!racer.hasFinished()){
                int distanceInPixel = (int)(racer.getCurrentDistance() * raceWidth / totalDistance);
                racerPosX = (raceBeginX - RACER_SIZE / 2) + distanceInPixel;
            }
            // Racer has finished, just wait at the end of the race
            else{
                racerPosX = raceWidth + raceBeginX - (RACER_SIZE / 2);
            }

            // All the time same Y pos
            racerPosY = raceBeginY;


            // Draw the P1, P2 to display which racer is the player1 or 2
            if(racer.equals(player1Racer)){
                lblPlayer1.setBounds(racerPosX + (RACER_SIZE / 2) - (lblPlayer1.getPreferredSize().width / 2), racerPosY + RACER_SIZE, lblPlayer1.getPreferredSize().width, lblPlayer1.getPreferredSize().height);
            }else if(racer.equals(player2Racer)){
                lblPlayer2.setBounds(racerPosX + (RACER_SIZE / 2) - (lblPlayer2.getPreferredSize().width / 2),  racerPosY + RACER_SIZE, lblPlayer2.getPreferredSize().width, lblPlayer2.getPreferredSize().height);
            }

            // Draw the circles
            g2d.setColor(racer.getColor());
            g2d.fillOval(racerPosX, racerPosY, RACER_SIZE, RACER_SIZE);
        }
    }
}
