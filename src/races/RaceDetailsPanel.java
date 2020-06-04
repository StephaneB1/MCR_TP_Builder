package races;

import cars.CarDisplayer;
import controller.Controller;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class RaceDetailsPanel extends JPanel {

    private ArrayList<Racer> racers;
    private int totalDistance;
    private Racer player1Racer;
    private Racer player2Racer;
    private ArrayList<JLabel> learerboardLabels;

    public RaceDetailsPanel(ArrayList<Racer> racers, int totalDistance){
        this.racers = racers;
        this.player1Racer = racers.get(0);
        this.player2Racer = racers.get(1);
        this.totalDistance = totalDistance;
        this.learerboardLabels = new ArrayList<>();

        this.setLayout(new GridLayout(1, 2));

        /* PLAYER 1 Panel */
        JPanel player1CarPanel = new JPanel();
        player1CarPanel.setLayout(null);
        player1CarPanel.setBorder(Controller.getPanelBorder("P L A Y E R  1    C A R"));
        //leftPanel.setBackground(Color.RED);
        //Icon imgIcon = new ImageIcon(RaceDetailsPanel.class.getClassLoader().getResource("smoke_anim_1.gif"));
        //JLabel smokeAnim = new JLabel(imgIcon);
        //smokeAnim.setBounds(40, -170, 300, 500);
        //leftPanel.add(smokeAnim);
        CarDisplayer carPanel1 = new CarDisplayer(player1Racer.getCar());
        carPanel1.setLocation(20, 150);
        player1CarPanel.add(carPanel1);

        /* LEADER BOARD Panel */
        JPanel leaderBoardPanel = new JPanel();
        leaderBoardPanel.setLayout(null);
        leaderBoardPanel.setBorder(Controller.getPanelBorder("L E A D E R  B O A R D"));
        //rightPanel.setBackground(Color.BLUE);
        JLabel lblLeaderBoard = new JLabel("Leaderboard");
        lblLeaderBoard.setFont(new Font("Arial", Font.BOLD, 20));
        lblLeaderBoard.setBounds(260,20,lblLeaderBoard.getPreferredSize().width,lblLeaderBoard.getPreferredSize().height);
        leaderBoardPanel.add(lblLeaderBoard);
        int initialxPos = 150;
        int initialYPos = 80;
        int yIncrement = 0;
        for(int i = 0; i < racers.size(); ++i, yIncrement += 30){
            learerboardLabels.add(new JLabel((i+1) + ". " + racers.get(i).getName() + " - " + (int)(racers.get(i).getCurrentDistance() * 100 / totalDistance) + "%"));
            learerboardLabels.get(i).setFont(new Font("Arial", Font.BOLD, 18));
            // Add +100 to be able to display all the label text on the good size when leaderboard changes, etc... 
            learerboardLabels.get(i).setBounds(initialxPos ,initialYPos + yIncrement,learerboardLabels.get(i).getPreferredSize().width + 100,learerboardLabels.get(i).getPreferredSize().height);
            leaderBoardPanel.add(learerboardLabels.get(i));
        }

        /* PLAYER 2 Panel */
        JPanel player2CarPanel = new JPanel();
        player2CarPanel.setLayout(null);
        player2CarPanel.setBorder(Controller.getPanelBorder("P L A Y E R  2    C A R"));
        //leftPanel.setBackground(Color.RED);

        //Icon imgIcon = new ImageIcon(RaceDetailsPanel.class.getClassLoader().getResource("smoke_anim_1.gif"));
        //JLabel smokeAnim = new JLabel(imgIcon);
        //smokeAnim.setBounds(40, -170, 300, 500);
        //leftPanel.add(smokeAnim);

        CarDisplayer carPanel2 = new CarDisplayer(player2Racer.getCar());
        carPanel2.setLocation(20, 150);
        player2CarPanel.add(carPanel2);


        this.add(player1CarPanel);
        this.add(leaderBoardPanel);
        this.add(player2CarPanel);
    }

    public void updateLeaderBoard() {
        racers.sort(Collections.reverseOrder());

        for(int i = 0; i < racers.size(); ++i){
            learerboardLabels.get(i).setForeground(racers.get(i).getColor());
            learerboardLabels.get(i).setText((i+1) + ". " + racers.get(i).getName() + " - " + (int)(racers.get(i).getCurrentDistance() * 100 / totalDistance) + "%");
            System.out.println((i+1) + ". " + racers.get(i).getName() + " - " + racers.get(i).getCurrentDistance());
        }
        System.out.println("");
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }


}
