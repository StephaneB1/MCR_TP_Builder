package races;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.TimerTask;
import java.util.Timer;

public class Race extends JFrame {

    private final int WIDTH = 800;
    private final int HEIGHT = 800;
    private RacePanel racePanel;

    private int totalDistance;
    private ArrayList<Racer> racers;
    private Boolean isRunning = false;
    private Timer timer = new Timer();

    public Race(int totalDistance, ArrayList<Racer> racers){
        this.totalDistance = totalDistance;
        this.racers = racers;

        this.setSize(WIDTH, HEIGHT);
        this.setTitle("Car builder");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.racePanel = new RacePanel(1000, 200, racers, totalDistance);
        this.add(racePanel);
    }

    public void start(){
        isRunning = true;


        // Set up the repeated task that will update the subject states (seconds)
        TimerTask repeatedTask = new TimerTask() {
            public void run() {

                while(isRunning){
                    System.out.println(racers.get(1).getCurrentDistanceMeter());
                    // Check if there is a winner
                    for(Racer racer : racers){
                        if(racer.getCurrentDistanceMeter() >= totalDistance){
                            isRunning = false;
                            displayWinner();
                            break;
                        }
                    }

                    // there is no winner -> run one tick
                    for(Racer racer : racers){
                        racer.runOneTick(2);
                    }

                    racePanel.repaint();

                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
           }
        };

        long delay  = 0;
        long period = 10;
        timer.scheduleAtFixedRate(repeatedTask, delay, period);






    }

    public void stop() {

    }

    private void displayRace() {

    }

    private void displayWinner() {

    }

}
