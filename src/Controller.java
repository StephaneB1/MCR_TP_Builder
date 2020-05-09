import cars.Car;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Controller extends JFrame {

    private Image carImg;


    public Controller() {

        //Car playerCar = new Car("src/ressources/cartest.png");

        setTitle("MCR - Racers");

        // Car frame with statistics
        JPanel carPanel = new JPanel();
        carPanel.setLayout(new GridLayout(1, 2));
        //carPanel.add(playerCar);
        carPanel.add(new JPanel());
        JPanel carStatsPanel = new JPanel();
        carStatsPanel.setLayout(new GridLayout(5, 2));
        // Set car statistics (later with car.getAcceleration() etc...)
        JLabel accelerationLabel = new JLabel("xxx m/s2");
        JLabel weightLabel = new JLabel("xxx kg");
        JLabel adherenceLabel = new JLabel("xxx ??");
        JLabel maniabilityLabel = new JLabel("xxx ??");
        JLabel resistanceLabel = new JLabel("xxx ??");
        carStatsPanel.add(new JLabel("acceleration :"));
        carStatsPanel.add(accelerationLabel);
        carStatsPanel.add(new JLabel("weight :"));
        carStatsPanel.add(weightLabel);
        carStatsPanel.add(new JLabel("adherence :"));
        carStatsPanel.add(adherenceLabel);
        carStatsPanel.add(new JLabel("maniability :"));
        carStatsPanel.add(maniabilityLabel);
        carStatsPanel.add(new JLabel("resistance :"));
        carStatsPanel.add(resistanceLabel);
        carPanel.add(carStatsPanel);

        // Builder and race UI
        JPanel builderAndRacePanel = new JPanel();
        builderAndRacePanel.setLayout(new GridLayout(1, 2));
        // Builder panel (carPart images + selection interface)
        JPanel builderPanel = new JPanel();
        builderPanel.setLayout(new GridLayout(2, 1));
        JPanel carPartPanel = new JPanel();
        JPanel selectionPanel = new JPanel();
        selectionPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        // Selection labels
        JLabel categoryLabel = new JLabel("Category");
        categoryLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel productLabel = new JLabel("Product");
        productLabel.setHorizontalAlignment(SwingConstants.CENTER);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor=GridBagConstraints.CENTER;
        c.gridy = 0;
        c.gridx = 0;
        c.weightx = 0.2;
        selectionPanel.add(new JButton("<"), c);
        c.gridx = 1;
        c.weightx = 0.6;
        selectionPanel.add(categoryLabel, c);
        c.gridx = 2;
        c.weightx = 0.2;
        selectionPanel.add(new JButton(">"), c);
        c.gridy = 1;
        c.gridx = 0;
        c.weightx = 0.2;
        selectionPanel.add(new JButton("<"), c);
        c.gridx = 1;
        c.weightx = 0.6;
        selectionPanel.add(productLabel, c);
        c.gridx = 2;
        c.weightx = 0.2;
        selectionPanel.add(new JButton(">"), c);
        c.gridy = 2;
        c.gridx = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 3;
        selectionPanel.add(new JButton("Mount to car"), c);
        builderPanel.add(carPartPanel);
        builderPanel.add(selectionPanel);

        // Race info panel
        JPanel racePanel = new JPanel();

        builderAndRacePanel.add(builderPanel);
        builderAndRacePanel.add(racePanel);

        setLayout(new GridLayout(2, 1));
        add(carPanel);
        add(builderAndRacePanel);

        //Resizing frame
        setSize(600,400);
        //position center of screen
        setLocationRelativeTo(null);
        //set visible
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
