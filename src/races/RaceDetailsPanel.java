package races;

import cars.CarDisplayer;
import controller.Controller;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

public class RaceDetailsPanel extends JPanel {

    private ArrayList<Racer> racers;
    private int totalDistance;
    private Racer player1Racer;
    private Racer player2Racer;
    private ArrayList<RacerPanel> racersPanels;
    private ArrayList<JLabel> learerboardLabels;

    public RaceDetailsPanel(ArrayList<Racer> racers, int totalDistance){
        setBackground(Color.WHITE);
        this.racersPanels = new ArrayList<>();
        this.racers = racers;
        this.player1Racer = racers.get(0);
        this.player2Racer = racers.get(1);
        this.totalDistance = totalDistance;
        this.learerboardLabels = new ArrayList<>();

        this.setLayout(new GridLayout(1, 2));

        racersPanels.add(new RacerPanel(player1Racer, "P L A Y E R  1    C A R"));
        racersPanels.add(new RacerPanel(player2Racer, "P L A Y E R  2    C A R"));
        JPanel leaderBoardPanel = loadLeaderboardPanel();

        this.add(racersPanels.get(0));
        this.add(leaderBoardPanel);
        this.add(racersPanels.get(1));
    }

    private JPanel loadLeaderboardPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(null);
        panel.setBorder(Controller.getPanelBorder("L E A D E R  B O A R D"));
        //rightPanel.setBackground(Color.BLUE);
        int initialxPos = 150;
        int initialYPos = 80;
        int yIncrement = 0;
        for(int i = 0; i < racers.size(); ++i, yIncrement += 30){
            learerboardLabels.add(new JLabel((i+1) + ". " + racers.get(i).getName() + " - " + (int)(racers.get(i).getCurrentDistance() * 100 / totalDistance) + "%"));
            learerboardLabels.get(i).setFont(new Font("Arial", Font.BOLD, 18));
            // Add +100 to be able to display all the label text on the good size when leaderboard changes, etc...
            learerboardLabels.get(i).setBounds(initialxPos ,initialYPos + yIncrement,learerboardLabels.get(i).getPreferredSize().width + 100,learerboardLabels.get(i).getPreferredSize().height);
            panel.add(learerboardLabels.get(i));
        }

        return panel;
    }

    public void updateLeaderBoard() {
        racers.sort(Collections.reverseOrder());

        for(int i = 0; i < racers.size(); ++i){
            learerboardLabels.get(i).setForeground(racers.get(i).getColor());
            learerboardLabels.get(i).setText((i+1) + ". " + racers.get(i).getName() + " - " + (int)(racers.get(i).getCurrentDistance() * 100 / totalDistance) + "%");
            //System.out.println((i+1) + ". " + racers.get(i).getName() + " - " + racers.get(i).getCurrentDistance());
        }
        //System.out.println("");
    }

    public void checkRacersCrash(){
        for(RacerPanel racerPanel : racersPanels){
            racerPanel.checkCrash();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }


}
