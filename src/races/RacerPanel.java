package races;

import cars.CarDisplayer;
import controller.Controller;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
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

    public RacerPanel(Racer racer, String panelTitle) {
        this.racer = racer;

        BufferedImage warningImg = null;
        try {
            warningImg = ImageIO.read(new File("resources/warning.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }


        setLayout(null);
        setBorder(Controller.getPanelBorder(panelTitle));
        //leftPanel.setBackground(Color.RED);
        //Icon imgIcon = new ImageIcon(RaceDetailsPanel.class.getClassLoader().getResource("smoke_anim_1.gif"));
        //JLabel smokeAnim = new JLabel(imgIcon);
        //smokeAnim.setBounds(40, -170, 300, 500);
        //leftPanel.add(smokeAnim);
        CarDisplayer carPanel1 = new CarDisplayer(racer.getCar());
        carPanel1.setLocation(20, 150);

        lblWarningImg = new JLabel(new ImageIcon(warningImg));
        lblWarningImg.setBounds(420 ,20,lblWarningImg.getPreferredSize().width, lblWarningImg.getPreferredSize().height);
        lblWarningImg.setVisible(false);

        add(carPanel1);
        add(lblWarningImg);
    }

    public void checkCrash(){
        System.out.println(racer.getName() + " : " + racer.isCrashed());
        if(racer.isCrashed() && !animCrashedRunning){
            animCrashedRunning = true;

            repeatedTask = new TimerTask() {
                @Override
                public void run() {
                    if(RacerPanel.this.lblWarningImg.isVisible()){
                        RacerPanel.this.lblWarningImg.setVisible(false);
                    }else{
                        RacerPanel.this.lblWarningImg.setVisible(true);
                    }
                }
            };

            timerWarning = new Timer();
            timerWarning.scheduleAtFixedRate(repeatedTask, 0, 500);
        }else if(!racer.isCrashed() && animCrashedRunning){
            animCrashedRunning = false;
            lblWarningImg.setVisible(false);
            timerWarning.cancel();
            timerWarning.purge();
        }
    }


}
