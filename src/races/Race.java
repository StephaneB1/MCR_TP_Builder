package races;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.TimerTask;
import java.util.Timer;

public class Race extends JFrame {

    private final int WIDTH = 1600;
    private final int HEIGHT = 720;
    private RacePanel racePanel;
    private RaceDetailsPanel raceDetailsPanel;

    private int totalDistance;
    private ArrayList<Racer> racers;
    private Boolean abort = false;
    private Timer timer = new Timer();
    private int nbRacers;
    private int nbRacersFinished;

    public Race(int totalDistance, ArrayList<Racer> racers){
        this.totalDistance = totalDistance;
        this.racers = racers;
        this.nbRacers = this.racers.size();

        for(Racer racer : this.racers){
            racer.reset();
        }

        this.setSize(WIDTH, HEIGHT);
        this.setTitle("Race");
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(null);
        // Race panel has same width as the JFrame and 1/4 of his height
        this.racePanel = new RacePanel(WIDTH, HEIGHT / 4, racers, totalDistance);
        // PanelBottom for player stats in the current race has the rest of the windows
        this.raceDetailsPanel = new RaceDetailsPanel(this.racers, totalDistance);
        Border padding = BorderFactory.createEmptyBorder(20, 20, 40, 20);
        raceDetailsPanel.setBorder(padding);
        raceDetailsPanel.setSize(WIDTH, 3 * HEIGHT / 4);

        raceDetailsPanel.setLocation(0, HEIGHT / 4);

        this.add(racePanel);
        this.add(raceDetailsPanel);
        this.setVisible(true);

        // Don't forget to stop the race, otherwise it will continue to calculate racers positions
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                abort = true;
            }
        });


    }

    /**
     * Start the race, each "period time" we'll run a race tick, update all racer and race infos
     * This repeated task will be aborted when we quit the frame (top tight cross button)
     */
    public void start(){
        // Set up the repeated task that will update the subject states (seconds)
        TimerTask repeatedTask = new TimerTask() {
            public void run() {
                // Continue while not abort and all racers have not finished the race
                if(!abort && nbRacersFinished != nbRacers){
                    runOneTick();
                    racerFinishCheck();

                    racePanel.repaint();

                    raceDetailsPanel.updateLeaderBoard();
                    raceDetailsPanel.checkRacersCrash();
                }
                // Race finish -> stop the current timertask and display winner
                else{
                    // check we have winner, because we can stop the race when we quit the frame (top left cross icon)
                    if(!abort){
                        displayWinner();
                    }
                    this.cancel();
                }
           }
        };
        
        long delay  = 0;
        long period = 50;
        timer.scheduleAtFixedRate(repeatedTask, delay, period);
    }

    /**
     * Run a single tick of the race for all racers
     */
    private void runOneTick(){
        for(Racer racer : racers){
            if(!racer.hasFinished()){
                racer.runOneTick();
            }
        }
    }

    /**
     * Detect if a racer has finished the race to stop it (to be able to
     * wait the other opponents)
     */
    private void racerFinishCheck() {
        for(Racer racer : racers){
            if(!racer.hasFinished() && racer.getCurrentDistance() >= totalDistance){
                racer.setHasFinished(true);
                nbRacersFinished++;
            }
        }
    }

    /**
     * Display a popup to notify that the race is finished and display the final leaderboard
     */
    private void displayWinner() {
        StringBuilder sb = new StringBuilder("Leaderboard\n\n");
        for(int i = 0; i < racers.size(); ++i){
            sb.append(i + 1)
                .append(". ")
                .append(racers.get(i)
                .getName())
                .append("\n");
        }
        sb.append("\n");

        final Icon imgIcon = new ImageIcon("resources/winner_trophy.png");
        UIManager.put("OptionPane.messageFont", new Font("Arial", Font.BOLD, 16));
        JOptionPane.showMessageDialog(null, sb.toString(), "winner winner chicken dinner", JOptionPane.INFORMATION_MESSAGE, imgIcon);
    }

}
