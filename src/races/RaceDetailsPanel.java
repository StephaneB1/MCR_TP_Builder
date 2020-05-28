package races;

import cars.Car;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class RaceDetailsPanel extends JPanel {

    private ArrayList<Racer> racers;
    private int totalDistance;
    private Racer playerRacer;
    private ArrayList<JLabel> learerboardLabels;

    public RaceDetailsPanel(ArrayList<Racer> racers, int totalDistance){
        this.racers = racers;
        this.playerRacer = racers.get(0);
        this.totalDistance = totalDistance;
        this.playerRacer.getCar().setAlphaTransparency(1f);
        this.learerboardLabels = new ArrayList<>();

        this.setLayout(new GridLayout(1, 2));

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(null);
        //leftPanel.setBackground(Color.RED);
        JLabel lblYourCar = new JLabel("Your car");
        lblYourCar.setVisible(true);
        lblYourCar.setFont(new Font("Arial", Font.BOLD, 20));
        lblYourCar.setBounds(260,20,lblYourCar.getPreferredSize().width,lblYourCar.getPreferredSize().height);
        leftPanel.add(lblYourCar);

        playerRacer.getCar().setLocation(70,150);
        leftPanel.add(playerRacer.getCar());


        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(null);
        //rightPanel.setBackground(Color.BLUE);
        JLabel lblLeaderBoard = new JLabel("Leaderboard");
        lblLeaderBoard.setFont(new Font("Arial", Font.BOLD, 20));
        lblLeaderBoard.setBounds(260,20,lblLeaderBoard.getPreferredSize().width,lblLeaderBoard.getPreferredSize().height);
        rightPanel.add(lblLeaderBoard);
        int initialxPos = 150;
        int initialYPos = 80;
        int yIncrement = 0;
        for(int i = 0; i < racers.size(); ++i, yIncrement += 30){
            learerboardLabels.add(new JLabel((i+1) + ". " + racers.get(i).getName() + " - " + (int)(racers.get(i).getCurrentDistanceMeter() * 100 / totalDistance) + "%"));
            learerboardLabels.get(i).setFont(new Font("Arial", Font.BOLD, 18));
            // Add +40 to be able to display all the label text when we pass from ...99% to ...100%
            learerboardLabels.get(i).setBounds(initialxPos ,initialYPos + yIncrement,learerboardLabels.get(i).getPreferredSize().width + 40,learerboardLabels.get(i).getPreferredSize().height);
            rightPanel.add(learerboardLabels.get(i));
        }


        this.add(leftPanel);
        this.add(rightPanel);
    }

    public void updateLeaderBoard() {
        racers.sort(Collections.reverseOrder());

        for(int i = 0; i < racers.size(); ++i){
            learerboardLabels.get(i).setForeground(racers.get(i).getColor());
            learerboardLabels.get(i).setText((i+1) + ". " + racers.get(i).getName() + " - " + (int)(racers.get(i).getCurrentDistanceMeter() * 100 / totalDistance) + "%");
            System.out.println((i+1) + ". " + racers.get(i).getName() + " - " + racers.get(i).getCurrentDistanceMeter());
        }
        System.out.println("");
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }


}
