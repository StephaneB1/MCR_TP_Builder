package races;

import cars.Car;

import javax.swing.*;
import java.awt.*;

public class RaceDetailsPanel extends JPanel {

    private Car playerCar;

    public RaceDetailsPanel(Car playerCar){
        this.playerCar = playerCar;
        this.playerCar.setAlphaTransparency(1f);

        this.setLayout(new GridLayout(1, 2));

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(null);
        //leftPanel.setBackground(Color.RED);
        JLabel lblYourCar = new JLabel("Your car");
        lblYourCar.setVisible(true);
        lblYourCar.setFont(new Font("Arial", Font.BOLD, 18));
        lblYourCar.setBounds(260,20,lblYourCar.getPreferredSize().width,lblYourCar.getPreferredSize().height);
        leftPanel.add(lblYourCar);

        playerCar.setLocation(70,150);
        leftPanel.add(playerCar);


        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(Color.BLUE);
        rightPanel.add(new JLabel("RIGHT PANEL"));

        this.add(leftPanel);
        this.add(rightPanel);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

    }
}
