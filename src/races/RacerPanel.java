package races;

import cars.CarDisplayer;
import controller.Controller;
import controller.StatsPanel;
import utils.Utils;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class RacerPanel extends JPanel {

    private Racer racer;
    private boolean animCrashedRunning = false;
    private TimerTask repeatedTask;
    private Timer timerWarning;
    private JLabel lblWarningImg;
    private JLabel lblSmokeAnim;

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

        StatsPanel statsPanel = new StatsPanel(carPanel);
        statsPanel.setBackground(Color.RED);
        statsPanel.updateStats();
        statsPanel.setLocation(30, 340);
        statsPanel.setSize(440,120);

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
                animCrashedRunning = true;

                lblSmokeAnim.setVisible(true);

                repeatedTask = new TimerTask() {
                    @Override
                    public void run() {
                        if (RacerPanel.this.lblWarningImg.isVisible()) {
                            RacerPanel.this.lblWarningImg.setVisible(false);
                        } else {
                            RacerPanel.this.lblWarningImg.setVisible(true);
                        }
                    }
                };

                timerWarning = new Timer();
                timerWarning.scheduleAtFixedRate(repeatedTask, 0, 500);
            } else if (!racer.isCrashed() && animCrashedRunning) {
                animCrashedRunning = false;
                lblSmokeAnim.setVisible(false);
                lblWarningImg.setVisible(false);
                timerWarning.cancel();
                timerWarning.purge();
            }
        }
    }


}
