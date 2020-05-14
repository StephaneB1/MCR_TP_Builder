
import cars.*;
import garage.Garage;
import garage.GarageProduct;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller extends JFrame {

    private final int SCREEN_WIDTH  = 1200;
    private final int SCREEN_HEIGHT = 600;

    private final Color CAR_STATS_BG_COLOR     = Color.WHITE;
    private final Color SHOP_AND_RACE_BG_COLOR = Color.WHITE; //new Color(230, 230, 230);

    private Garage garage;
    private int currentCategory;
    private int currentProduct;
    private Car playerCar;

    public Controller() {

        setupGarage();
        playerCar = new Car();

        setTitle("MCR - Racers");

        // Car frame with statistics
        JPanel carPanel = new JPanel();
        carPanel.setBackground(CAR_STATS_BG_COLOR);
        carPanel.setLayout(new GridLayout(1,2));
        // -- Player car panel
        JPanel carPanelImage = playerCar;
        carPanelImage.setOpaque(false);
        carPanel.add(carPanelImage);
        // -- Player car stat's panel
        JPanel carStatsPanel = new JPanel();
        carStatsPanel.setOpaque(false);
        carStatsPanel.setLayout(new GridLayout(5, 2));
        final JLabel accelerationLabel = new JLabel(playerCar.getAcceleration() + "m/s2");
        final JLabel weightLabel       = new JLabel(playerCar.getWeight() + "kg");
        final JLabel adherenceLabel    = new JLabel(playerCar.getAdherence() + "/10");
        final JLabel maniabilityLabel  = new JLabel(playerCar.getManiability() + "/10");
        final JLabel resistanceLabel   = new JLabel(playerCar.getResistance() + "%");
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
        builderAndRacePanel.setBackground(SHOP_AND_RACE_BG_COLOR);
        GridLayout builderRaceLayout = new GridLayout(1, 2);
        builderRaceLayout.setHgap(50);
        builderAndRacePanel.setLayout(builderRaceLayout);
        // -- Builder panel (carPart images + selection interface)
        JPanel builderPanel = new JPanel();
        builderPanel.setOpaque(false);
        builderPanel.setLayout(new GridLayout(2, 1));
        JPanel carPartPanel = new JPanel();
        carPartPanel.setOpaque(false);
        JPanel selectionPanel = new JPanel();
        selectionPanel.setOpaque(false);
        selectionPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        // -- Selection labels
        JLabel categoryLabel = new JLabel(garage.getInventory().get(currentCategory).getProductLabel());
        categoryLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel productLabel = new JLabel(garage.getInventory().get(currentCategory).getProducts().get(currentProduct).getName());
        JButton categoryLeftButton = new JButton("<");
        categoryLeftButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentCategory = (currentCategory - 1 + garage.getInventory().size()) % garage.getInventory().size();
                currentProduct  = 0;
                updateSelectionLabels(categoryLabel, productLabel, carPartPanel);
            }
        });
        JButton categoryRightButton = new JButton(">");
        categoryRightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentCategory = (currentCategory + 1) % garage.getInventory().size();
                currentProduct  = 0;
                updateSelectionLabels(categoryLabel, productLabel, carPartPanel);
            }
        });
        JButton productLeftButton = new JButton("<");
        productLeftButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentProduct = (currentProduct - 1 + garage.getInventory().get(currentCategory).getProducts().size())
                        % garage.getInventory().get(currentCategory).getProducts().size();
                updateSelectionLabels(categoryLabel, productLabel, carPartPanel);
            }
        });
        JButton productRightButton = new JButton(">");
        productRightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentProduct = (currentProduct + 1) % garage.getInventory().get(currentCategory).getProducts().size();
                updateSelectionLabels(categoryLabel, productLabel, carPartPanel);
            }
        });
        productLabel.setHorizontalAlignment(SwingConstants.CENTER);
        c.insets = new Insets(3,3,3,3); // padding
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor=GridBagConstraints.CENTER;
        c.gridy = 0;
        c.gridx = 0;
        c.weightx = 0.2;
        selectionPanel.add(categoryLeftButton, c);
        c.gridx = 1;
        c.weightx = 0.6;
        selectionPanel.add(categoryLabel, c);
        c.gridx = 2;
        c.weightx = 0.2;
        selectionPanel.add(categoryRightButton, c);
        c.gridy = 1;
        c.gridx = 0;
        c.weightx = 0.2;
        selectionPanel.add(productLeftButton, c);
        c.gridx = 1;
        c.weightx = 0.6;
        selectionPanel.add(productLabel, c);
        c.gridx = 2;
        c.weightx = 0.2;
        selectionPanel.add(productRightButton, c);
        c.gridy = 2;
        c.gridx = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 3;
        c.ipady = 30; // make the button bigger
        JButton mountToCarButton = new JButton("Mount to car");
        mountToCarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add car part to the player's car
                playerCar.installCarPart(garage.getInventory().get(currentCategory).getProducts().get(currentProduct));
                carPanelImage.repaint();

                // Update the stats labels
                accelerationLabel.setText(playerCar.getAcceleration() + "m/s2");
                weightLabel.setText(playerCar.getWeight() + "kg");
                adherenceLabel.setText(playerCar.getAdherence() + "/10");
                maniabilityLabel.setText(playerCar.getManiability() + "/10");
                resistanceLabel.setText(playerCar.getResistance() + "%");
            }
        });
        selectionPanel.add(mountToCarButton, c);
        builderPanel.add(carPartPanel);
        builderPanel.add(selectionPanel);

        // Race info panel
        JPanel racePanel = new JPanel();
        racePanel.setOpaque(false);
        racePanel.setLayout(new GridLayout(3, 2));
        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        JButton startButton = new JButton("Start");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startRace();
            }
        });
        racePanel.add(new JLabel("Racers :"));
        racePanel.add(new JLabel("5"));
        racePanel.add(new JLabel("Total distance :"));
        racePanel.add(new JLabel("5km"));
        racePanel.add(quitButton);
        racePanel.add(startButton);

        builderAndRacePanel.add(builderPanel);
        builderAndRacePanel.add(racePanel);

        GridLayout gl = new GridLayout(2, 1);
        gl.setVgap(10);
        setLayout(gl);

        // Add panels with padding
        Border padding = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        carPanel.setBorder(padding);
        builderAndRacePanel.setBorder(padding);
        add(carPanel);
        add(builderAndRacePanel);

        //Resizing frame
        setSize(SCREEN_WIDTH,SCREEN_HEIGHT);
        //position center of screen
        setLocationRelativeTo(null);
        //set visible
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void updateSelectionLabels(JLabel categoryLabel, JLabel productLabel, JPanel productPanel) {
        GarageProduct category = garage.getInventory().get(currentCategory);
        CarPart product        = category.getProducts().get(currentProduct);

        // Selection labels
        categoryLabel.setText(category.getProductLabel());
        productLabel.setText(product.getName());

        // Product display
        JLabel picLabel = new JLabel(new ImageIcon(product.getImage()));
        productPanel.removeAll();
        productPanel.add(picLabel);
        productPanel.repaint();
    }

    private void setupGarage() {
        garage = new Garage();

        // Products available in the garage (maybe add the point coordinate in the class)
        // - Car bodies
        GarageProduct bodies = new GarageProduct("Bodies");
        bodies.addProduct(new Body("Body-1", "bodyTemplate.png", 0, 1200, 0, 0, 50));
        // - Car motors
        GarageProduct motors = new GarageProduct("Motors");
        motors.addProduct(new Motor("Motor-1", "motorTemplate.png", 3, 40, 2, 2, 2, new Point(80, 70)));
        // - Car tires
        GarageProduct tires = new GarageProduct("Tires");
        tires.addProduct(new Tires("Tires-1", "tiresTemplate.png", 0.1, 2, 2, 2, 2, new Point(90, 130)));
        // - Car spoilers
        GarageProduct spoilers = new GarageProduct("Spoilers");
        spoilers.addProduct(new Spoiler("Spoiler-1", "spoilerTemplate.png", 2, 2, 2, 2, 2, new Point(35, 35)));

        // Adding to the garage inventory
        garage.addToInventory(bodies);
        garage.addToInventory(motors);
        garage.addToInventory(tires);
        garage.addToInventory(spoilers);
    }

    private void startRace() {

    }

}
