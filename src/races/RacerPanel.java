package races;

import cars.CarDisplayer;
import controller.StatsPanel;
import utils.Utils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class RacerPanel extends JPanel {

    private Racer racer;
    private boolean animCrashedRunning = false;
    private JLabel lblSmokeAnim;

    private TimerTask repeatedTaskWarning;
    private Timer timerWarning;
    private JLabel lblWarningImg;

    private TimerTask repeatedTaskCrashBar;
    private Timer timerCrashBar;
    private JLabel lblCrashBar;

    public RacerPanel(Racer racer, String panelTitle) {
        this.racer = racer;

        setBackground(Color.WHITE);

        BufferedImage warningImg = null;
        try {
            warningImg = ImageIO.read(new File("resources/warning.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }


        setLayout(null);
        setBorder(Utils.getPanelBorder(panelTitle, Utils.getDefaultColor()));

        Icon imgIcon = new ImageIcon(RaceDetailsPanel.class.getClassLoader().getResource("smoke_anim.gif"));
        lblSmokeAnim = new JLabel(imgIcon);
        lblSmokeAnim.setBounds(50, -60, lblSmokeAnim.getPreferredSize().width, lblSmokeAnim.getPreferredSize().height);
        lblSmokeAnim.setVisible(false);

        CarDisplayer carPanel = new CarDisplayer(racer.getCar());
        carPanel.setLocation(20, 90);

        lblWarningImg = new JLabel(new ImageIcon(warningImg));
        lblWarningImg.setBounds(420 ,20,lblWarningImg.getPreferredSize().width, lblWarningImg.getPreferredSize().height);
        lblWarningImg.setVisible(false);

        StatsPanel statsPanel = new StatsPanel(carPanel, 1);
        statsPanel.setBackground(new Color(210, 0, 0));
        statsPanel.updateStats();
        statsPanel.setLocation(30, 340);
        statsPanel.setSize(440,120);

        // Crash timeout bar
        lblCrashBar = new JLabel();
        lblCrashBar.setBounds(420 ,90, lblCrashBar.getPreferredSize().width, lblCrashBar.getPreferredSize().height);
        lblCrashBar.setSize(80, 7);
        lblCrashBar.setOpaque(true);
        lblCrashBar.setBackground(Color.RED);
        lblCrashBar.setVisible(false);

        // Add components
        add(lblCrashBar);
        add(statsPanel);
        add(lblWarningImg);
        add(lblSmokeAnim);
        add(carPanel);
    }

    /**
     * Check if the current racer has crashed. While is crashing, we'll display smoke animation
     * as a GIF, and a flashing warning signal image.
     */
    public void checkCrash(){
        if(!racer.hasFinished()) {
            if (racer.isCrashed() && !animCrashedRunning) {
                beginCrashDisplay();
            } else if (!racer.isCrashed() && animCrashedRunning) {
                stopCrashDisplay();
            }
        }
    }

    /**
     * Displays crash UI elements and starts timer tasks
     */
    private void beginCrashDisplay() {
        animCrashedRunning = true;

        lblSmokeAnim.setVisible(true);
        lblCrashBar.setVisible(true);

        repeatedTaskWarning = new TimerTask() {
            @Override
            public void run() {
                // Reverse setVisible
                RacerPanel.this.lblWarningImg.setVisible(!RacerPanel.this.lblWarningImg.isVisible());
            }
        };
        timerWarning = new Timer();
        timerWarning.scheduleAtFixedRate(repeatedTaskWarning, 0, 500);

        repeatedTaskCrashBar = new TimerTask() {
            @Override
            public void run() {
                lblCrashBar.setSize((int)(80*racer.crashProgression()), lblCrashBar.getHeight());
            }
        };
        timerCrashBar = new Timer();
        timerCrashBar.scheduleAtFixedRate(repeatedTaskCrashBar, 0, 50);
    }

    /**
     * Hides UI elements of the crash and stops associated timers
     */
    private void stopCrashDisplay() {
        animCrashedRunning = false;
        // Hide crash elements
        lblSmokeAnim.setVisible(false);
        lblWarningImg.setVisible(false);
        lblCrashBar.setVisible(false);
        // Reset timers
        timerWarning.cancel();
        timerWarning.purge();
        timerCrashBar.cancel();
        timerCrashBar.purge();
        // Reset crash bar's width
        lblCrashBar.setSize(80, lblCrashBar.getHeight());
    }

}
