package races;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.TimerTask;
import java.util.Timer;

public class Race extends JFrame implements WindowListener {

    private final int WIDTH = 1200;
    private final int HEIGHT = 600;
    private RacePanel racePanel;

    private int totalDistance;
    private ArrayList<Racer> racers;
    private Boolean isRunning = false;
    private Timer timer = new Timer();

    public Race(int totalDistance, ArrayList<Racer> racers){
        this.totalDistance = totalDistance;
        this.racers = racers;

        this.setSize(WIDTH, HEIGHT);
        this.setTitle("Race");
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(null);
        // Race panel has same width as the JFrame and 1/4 of his height
        this.racePanel = new RacePanel(WIDTH, HEIGHT / 4, racers, totalDistance);
        // PanelBottom for player stats in the current race has the rest of the windows
        JPanel panelBottom = new JPanel();
        panelBottom.setSize(1200, 3 * HEIGHT / 4);
        panelBottom.setBackground(Color.red);
        panelBottom.setLocation(0, HEIGHT / 4);

        this.add(racePanel);
        this.add(panelBottom);
        this.setVisible(true);

        // Don't forget to stop the race, otherwise it will continue to calculate racers positions
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                isRunning = false;
            }
        });


    }

    public void start(){
        isRunning = true;

        // Set up the repeated task that will update the subject states (seconds)
        TimerTask repeatedTask = new TimerTask() {
            public void run() {

                while(isRunning){

                    racePanel.repaint();

                    // Run a tick
                    for(Racer racer : racers){
                        racer.runOneTick(2);
                    }

                    // Check if there is a winner
                    for(Racer racer : racers){
                        if(racer.getCurrentDistanceMeter() >= totalDistance){
                            isRunning = false;
                            displayWinner(racer);
                            break;
                        }
                    }


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

    private void displayWinner(Racer racerWinner) {
        JOptionPane.showMessageDialog(null, racerWinner.getName() + " won the race !", "winner winner chicken dinner", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void windowOpened(WindowEvent windowEvent) {

    }

    @Override
    public void windowClosing(WindowEvent windowEvent) {
        System.out.println("Windows closing");
        isRunning = false;
    }

    @Override
    public void windowClosed(WindowEvent windowEvent) {
        System.out.println("windows closed");
    }

    @Override
    public void windowIconified(WindowEvent windowEvent) {

    }

    @Override
    public void windowDeiconified(WindowEvent windowEvent) {

    }

    @Override
    public void windowActivated(WindowEvent windowEvent) {

    }

    @Override
    public void windowDeactivated(WindowEvent windowEvent) {

    }
}
