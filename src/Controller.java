
import carBuilder.CarBuilder;
import carBuilder.EmtpyCar;
import cars.*;
import garage.Garage;
import garage.GarageProduct;
import races.Race;
import races.Racer;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import static java.lang.System.exit;

public class Controller extends JFrame {

    private final int SCREEN_WIDTH  = 1200;
    private final int SCREEN_HEIGHT = 600;

    // Car stats
    private final JLabel accelerationLabel = new JLabel();
    private final JLabel weightLabel       = new JLabel();
    private final JLabel adherenceLabel    = new JLabel();
    private final JLabel maniabilityLabel  = new JLabel();
    private final JLabel resistanceLabel   = new JLabel();

    private Garage garage;
    private int currentCategory;
    private int currentProduct;
    private Car playerCar;


    public Controller() {

        setupGarage();

        setTitle("MCR - Racers");

        // General app layout (mainPanel) :
        /*---------------------------------------------*
         * MCR - Racers                                *
         *---------------------------------------------*
         * BLUEPRINTS            | STATS               *
         * - Car preview         | - Car performances  *
         *                       |                     *
         *=============================================*
         * BUILDER               | RACE                *
         * - Car components      | - Race information  *
         *                       |                     *
         *---------------------------------------------*/

        JPanel mainPanel = new JPanel();
        mainPanel.setOpaque(false);
        GridLayout mainLayout = new GridLayout(2, 2);
        mainLayout.setHgap(10);
        mainLayout.setVgap(10);
        mainPanel.setLayout(mainLayout);

        // BLUEPRINTS
        playerCar = new Car();
        playerCar.setOpaque(false);
        // STATS
        JPanel carStatsPanel = loadCarStatsPanel();
        // BUILDER
        JPanel builderPanel = loadBuilderPanel();
        builderPanel.setBackground(Color.WHITE);
        // RACE
        JPanel racePanel = loadRacePanel();

        // Add panels with padding
        Border padding = BorderFactory.createEmptyBorder(20, 20, 20, 20);
        Border padding_small = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        mainPanel.setBorder(padding);
        carStatsPanel.setBorder(padding_small);
        builderPanel.setBorder(padding_small);
        racePanel.setBorder(padding_small);
        mainPanel.add(playerCar);
        mainPanel.add(carStatsPanel);
        mainPanel.add(builderPanel);
        mainPanel.add(racePanel);

        add(mainPanel);

        //Resizing frame
        setSize(SCREEN_WIDTH,SCREEN_HEIGHT);
        //position center of screen
        setLocationRelativeTo(null);
        //set visible
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    private void setupGarage() {
        garage = new Garage();

        // Products available in the garage (maybe add the point coordinate in the class)
        // - Car bodies
        GarageProduct bodies = new GarageProduct("Bodies");
        bodies.addProduct(new Body("Body-1", "bodyTemplate.png", 0, 1200, 0, 0, 50));
        bodies.addProduct(new Body("Body-2", "Body_1.png", 0, 1200, 0, 0, 50));
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

        Car car1 = new Car( "Car1",
                new Body("Body", "bodyTemplate.png", 1, 1, 1, 1, 1),
                new Motor("Motor", "motorTemplate.png",1,1,0.1,0.1,0.1, new Point(80, 70)),
                new Tires("Tires", "tiresTemplate.png",1,1,0.1,0.1,0.1, new Point(90, 130)),
                new Spoiler("Spoiler", "spoilerTemplate.png",1,1,0.2,0.5,1, new Point(35, 35)),
                Color.RED);
        Car car2 = new Car( "Car2",
                new Body("Body", "bodyTemplate.png", 0.5, 1, 1, 1, 11),
                new Motor("Motor", "motorTemplate.png",0.8,1,1,1,1, new Point(80, 70)),
                new Tires("Tires", "tiresTemplate.png",0.7,1,1,1,1, new Point(90, 130)),
                new Spoiler("Spoiler", "spoilerTemplate.png",1,1,1,1,1, new Point(35, 35)),
                Color.BLUE);



        // /!\ RACERS[0] IS ALWAYS THE PLAYER, NOT A BOT /!\
        Racer racer1 = new Racer("Player1", car1, Color.RED, true);
        Racer racer2 = new Racer("Player2", car2, Color.GREEN, true);

        ArrayList<Racer> racers = new ArrayList<>();
        racers.add(racer1);
        racers.add(racer2);

        Race race = new Race(30000, racers);

        race.start();
    }

    public static EmtpyCar createNewCar(){
        return new CarBuilder();
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

    /*----------------------------------------------------------------*
     *                      JAVA SWING PANELS                         *
     *----------------------------------------------------------------*/
    private JPanel loadCarStatsPanel() {
        JPanel carStatsPanel = new JPanel();
        carStatsPanel.setOpaque(false);
        carStatsPanel.setLayout(new GridLayout(5, 2));
        accelerationLabel.setText(playerCar.getAcceleration() + "m/s2");
        weightLabel.setText(playerCar.getWeight() + "kg");
        adherenceLabel.setText(playerCar.getAdherence() + "/10");
        maniabilityLabel.setText(playerCar.getManiability() + "/10");
        resistanceLabel.setText(playerCar.getResistance() + "%");
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
        return carStatsPanel;
    }

    private JPanel loadBuilderPanel() {
        JPanel builderPanel = new JPanel();
        builderPanel.setOpaque(true);
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
        JButton mountToCarButton = new JButton("Add to blueprint");
        JButton buildCarButton   = new JButton("Build car");
        mountToCarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add car part to the player's car
                playerCar.installCarPart(garage.getInventory().get(currentCategory).getProducts().get(currentProduct));
                playerCar.repaint();

                // Update the stats labels
                accelerationLabel.setText(playerCar.getAcceleration() + "m/s2");
                weightLabel.setText(playerCar.getWeight() + "kg");
                adherenceLabel.setText(playerCar.getAdherence() + "/10");
                maniabilityLabel.setText(playerCar.getManiability() + "/10");
                resistanceLabel.setText(playerCar.getResistance() + "%");
            }
        });
        buildCarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // Build car
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
        c.gridx = 1;
        c.ipady = 30; // make the button bigger
        selectionPanel.add(mountToCarButton, c);
        c.gridy = 2;
        c.gridx = 2;
        updateSelectionLabels(categoryLabel, productLabel, carPartPanel);
        selectionPanel.add(buildCarButton, c);
        builderPanel.add(carPartPanel);
        builderPanel.add(selectionPanel);

        return builderPanel;
    }

    private JPanel loadRacePanel() {
        JPanel racePanel = new JPanel();
        racePanel.setOpaque(false);
        racePanel.setLayout(new GridLayout(3, 2));
        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exit(0);
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

        return racePanel;
    }
}
