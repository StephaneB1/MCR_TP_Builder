package races;

import utils.Utils;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.TimerTask;
import java.util.Timer;

/**
 * MCR PROJECT : Builder Design Pattern
 * Author      : Bottin Stéphane, Demarta Robin, Dessaules Loïc, Kot Chau-Ying
 *
 * Description : Race frame that will display the current Race with race line drew
 * and racer1 / racer2 with their details, and the leaderBoard
 */
public class Race extends JFrame {
    private static final int SCREEN_WIDTH = 1600;
    private static final int SCREEN_HEIGHT = 720;
    private RacePanel racePanel;
    private RaceDetailsPanel raceDetailsPanel;

    private int totalDistance;
    private ArrayList<Racer> racers;
    private Boolean abort = false;
    private Timer timer = new Timer();
    private int nbRacers;
    private int nbRacersFinished;

    private static final String WINNER_TROPHY_PATH = "resources/winner_trophy.png";

    public Race(int totalDistance, ArrayList<Racer> racers){
        this.totalDistance = totalDistance;


        if(racers.size() < 2){
            System.out.println("EEEERRRRROOOOORRRRR");
        }


        // Clone racers to be able to restart a race with new Racer all the time,
        // Also to run more than one race at the same time
        this.racers = new ArrayList<>(racers.size());
        for(Racer racer : racers){
            try {
                this.racers.add((Racer) racer.clone());
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
        this.nbRacers = this.racers.size();

        // Init the frame
        frameInitialisation();

        // Don't forget to stop the race when we close the frame, otherwise it will continue to calculate racers positions
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

                    // Updates
                    raceDetailsPanel.updateLeaderBoard(nbRacersFinished);
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

    private void frameInitialisation(){
        this.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        this.setTitle("Race");
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(null);
        // Race panel has same width as the JFrame and 1/4 of his height
        this.racePanel = new RacePanel(SCREEN_WIDTH, SCREEN_HEIGHT / 4, this.racers, totalDistance);
        // PanelBottom for race details has the rest of the windows
        this.raceDetailsPanel = new RaceDetailsPanel(this.racers, totalDistance);
        // Border
        Border padding = BorderFactory.createEmptyBorder(20, 20, 40, 20);
        this.raceDetailsPanel.setBorder(padding);

        this.raceDetailsPanel.setSize(SCREEN_WIDTH, 3 * SCREEN_HEIGHT / 4);
        this.raceDetailsPanel.setLocation(0, SCREEN_HEIGHT / 4);

        this.add(racePanel);
        this.add(raceDetailsPanel);
        this.setVisible(true);
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

        Utils.popup(WINNER_TROPHY_PATH, "winner winner chicken dinner", sb.toString());
    }

}