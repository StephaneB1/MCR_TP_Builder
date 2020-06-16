package controller;

import cars.CarBuilder;
import cars.*;
import cars.parts.*;
import garage.Garage;
import garage.GarageProduct;
import races.Race;
import races.Racer;
import utils.Utils;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

import static java.lang.System.exit;

/**
 * MCR PROJECT : Builder Design Pattern
 * Author      : Bottin Stéphane, Demarta Robin, Dessaules Loïc, Kot Chau-Ying
 *
 * Description : Singleton class handling the main core of the app
 *                  - Opening the builder panel to build cars
 *                  - Display the opponents of a race
 *                  - Starting a race
 */
public class Controller extends JFrame {

    private static Controller instance;

    // Screen display
    private static final int SCREEN_WIDTH  = 900;
    private static final int SCREEN_HEIGHT = 600;

    // Race default values
    private static final int DEFAULT_DISTANCE = 3000;
    private static final int MIN_DISTANCE     = 500;
    private static final int MAX_DISTANCE     = 5000;
    private static final int STEP_DISTANCE    = 100;
    private static final int MIN_OPPONENTS    = 2;

    // Garage containing all the car parts and paint jobs
    private Garage garage;

    // Opponents
    private ArrayList<Racer> racers;
    private JPanel racersPanel;
    private JLabel totalRacersLabel;
    private JScrollPane opponentsScroll;
    private JButton addOpponentButton;
    private boolean autoGeneration = false;
    private ArrayList<Color> playerColors = new ArrayList<>(Arrays.asList(
        new Color(255,66,66),
        new Color(71, 126, 255),
        new Color(68, 255, 122),
        new Color(255, 179, 91),
        new Color(255, 131, 189),
        new Color(117, 40, 255),
        new Color(93, 255, 230),
        new Color(124, 32, 32),
        new Color(35, 63, 127),
        new Color(33, 125, 60),
        new Color(128, 90, 46),
        new Color(130, 67, 96),
        new Color(51, 18, 112),
        new Color(46, 126, 114)
    ));

    // Console information
    private JLabel console;

    public Controller() {

        racers = new ArrayList<>();
        setTitle("MCR - Bob The Racer");
        setupGarage();

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBackground(Color.WHITE);
        // Console format
        console = new JLabel("Welcome! Each player must first build their car, you can build a car by pressing on the (+) button down below!");
        console.setBorder(Utils.getPanelBorder("C O N S O L E", Utils.getDefaultColor()));
        console.setFont(new Font("Consolas", Font.PLAIN, 14));
        // Opponents
        opponentsScroll  = new JScrollPane();
        totalRacersLabel = new JLabel("0");
        updateOpponentsDisplay();
        opponentsScroll.setViewportView(racersPanel);
        opponentsScroll.setBorder(Utils.getPanelBorder("O P P O N E N T S", Utils.getDefaultColor()));
        opponentsScroll.setOpaque(false);

        // Add panels with padding
        Border padding = BorderFactory.createEmptyBorder(20, 20, 20, 20);
        mainPanel.setBorder(padding);
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(3,3,3,3); // padding
        c.weightx = 1;
        c.weighty = 0.05;
        c.gridx = 0;
        c.gridy = 0;
        mainPanel.add(console, c);
        c.weighty = 0.9;
        c.gridy = 1;
        mainPanel.add(opponentsScroll, c);
        c.gridy = 2;
        c.weighty = 0.05;
        mainPanel.add(loadRaceInformationPanel(), c);

        add(mainPanel);

        //Resizing frame
        setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Get the instance of the singleton class
     * @return only instance of Controller
     */
    public static Controller getInstance() {
        if(instance == null)
            instance = new Controller();

        return instance;
    }

    /**
     * Generate a new racer
     * @param car    : car of the racer
     * @param player : if the racer is a player or not
     */
    public Racer generateNewRacer(Car car, boolean player) {
        // Can't have more than 2 players
        if(player && racers.size() >= 2) {
            Utils.popupWarning("Sorry there's already 2 players.");
            return null;
        }

        return new Racer((player ?
                "Player " +  (racers.size() + 1) :
                "Bot " + (racers.size() - 1)), car,
                playerColors.get(racers.size() % playerColors.size()),
                true);
    }

    /**
     * Add a new racer to the list and update the display
     * @param racer : new racer to add
     */
    public void addNewRacer(Racer racer) {
        racers.add(racer);
        updateOpponentsDisplay();
    }

    /**
     * Updates the racers list display
     */
    private void updateOpponentsDisplay() {
        // Total racer information
        totalRacersLabel.setText(racers.size() + "");

        // Racers display
        racersPanel = new JPanel(new GridBagLayout());
        racersPanel.setBackground(Color.WHITE);
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(3,3,3,3); // padding
        c.gridy = 0;

        for(int i = 0; i < racers.size(); ++i) {
            // Layout
            c.gridx = i;

            // Car display with stats
            JPanel panel = new JPanel(new GridBagLayout());
            panel.setOpaque(false);
            panel.setPreferredSize(new Dimension(250, 280));
            panel.setBorder(Utils.getPanelBorder(racers.get(i).getName().toUpperCase(), racers.get(i).getColor()));
            CarDisplayer carDisplayer = new CarDisplayer(racers.get(i).getCar(), 0.45);
            GridBagConstraints c1 = new GridBagConstraints();
            c1.fill = GridBagConstraints.BOTH;
            c1.insets = new Insets(3,3,3,3); // padding
            c1.weighty = 1;
            c1.gridy = 0;
            panel.add(carDisplayer, c1);
            c1.gridy = 1;
            panel.add(new StatsPanel(carDisplayer, 0.5), c1);

            racersPanel.add(panel, c);
        }

        racersPanel.repaint();
        opponentsScroll.setViewportView(racersPanel);
        opponentsScroll.repaint();

        // Console information to guide the user on what to do next
        switch(racers.size()) {
            case 0:
                if(autoGeneration) {
                    addOpponentButton.setIcon(Utils.getSizedIcon("resources/GUI/add-opponent.png", 0.1, Image.SCALE_SMOOTH));
                    autoGeneration = false;
                }
                break;
            case 1:
                console.setText("Looking good! Alright now Player 2 it's your turn. Go make your dream car!");
                break;
            case 2:
                console.setText("Awesome, now that you're ready you can generate the opponents' cars by pressing on the (G) button below.");
                autoGeneration = true;
                addOpponentButton.setIcon(Utils.getSizedIcon("resources/GUI/add-opponent-auto.png", 0.1, Image.SCALE_SMOOTH));
                break;
            case 3:
                console.setText("That was easy right ? You can thank the Builder Design pattern for that.");
                break;
            case 4:
                console.setText("The competition is getting fierce! Add as many as you want and when you're ready you can start the race!");
                break;
        }
    }

    /**
     * Get the garage of the Controller
     * @return garage
     */
    public Garage getGarage() {
        return garage;
    }

    /**
     * Setup the garage of the Controller
     */
    private void setupGarage() {
        garage = new Garage();

        // Products available in the garage
        // - Car bodies
        GarageProduct bodies = new GarageProduct("Bodies");
        bodies.addProduct(new Body("Body-1", "Body_1.png", new Stats().randomize()));
        bodies.addProduct(new Body("Body-2", "Body_2.png", new Stats().randomize()));
        bodies.addProduct(new Body("Body-3", "Body_3.png", new Stats().randomize()));

        // - Car motors
        GarageProduct motors = new GarageProduct("Motors");
        motors.addProduct(new Motor("Motor-1", "Motor_1.png", new Stats().randomize()));
        motors.addProduct(new Motor("Motor-2", "Motor_2.png", new Stats().randomize()));
        motors.addProduct(new Motor("Motor-3", "Motor_3.png", new Stats().randomize()));

        // - Car tires
        GarageProduct tires = new GarageProduct("Tires");
        tires.addProduct(new Tires("Tires_1", "Tires_1.png", new Stats().randomize()));
        tires.addProduct(new Tires("Tires_2", "Tires_2.png", new Stats().randomize()));
        tires.addProduct(new Tires("Tires_3", "Tires_3.png", new Stats().randomize()));

        // - Car spoilers
        GarageProduct spoilers = new GarageProduct("Spoilers");
        spoilers.addProduct(new Spoiler("Spoiler-1", "Spoiler_1.png", new Stats().randomize()));
        spoilers.addProduct(new Spoiler("Spoiler-2", "Spoiler_2.png", new Stats().randomize()));
        spoilers.addProduct(new Spoiler("Spoiler-3", "Spoiler_3.png", new Stats().randomize()));

        // Adding to the garage inventory
        garage.addToInventory(bodies);
        garage.addToInventory(motors);
        garage.addToInventory(tires);
        garage.addToInventory(spoilers);

        // Adding the paint jobs
        garage.addPaintJob(new PaintJob("White Smoke", new Color(200, 200, 200)));
        garage.addPaintJob(new PaintJob("Red Lobster", new Color(231, 42, 29)));
        garage.addPaintJob(new PaintJob("Autumn Sprinkle", new Color(255, 153, 51)));
        garage.addPaintJob(new PaintJob("Squeezy Lemon", new Color(255, 255, 82)));
        garage.addPaintJob(new PaintJob("Apple Storm", new Color(51, 204, 51)));
        garage.addPaintJob(new PaintJob("Expired Mojito", new Color(0, 102, 0)));
        garage.addPaintJob(new PaintJob("Ocean Blue", new Color(0, 50, 205)));
        garage.addPaintJob(new PaintJob("Soft Blue", new Color(92, 255, 222)));
        garage.addPaintJob(new PaintJob("Pink Duster", new Color(255, 51, 204)));
        garage.addPaintJob(new PaintJob("Dark Knight", new Color(40, 40, 40)));
        garage.addPaintJob(new PaintJob("Grey Day", new Color(150, 150, 150)));

    }

    /**
     * Starts a race in a new panel
     * @param racers   : list of racers
     * @param distance : distance of the race
     */
    private void startRace(ArrayList<Racer> racers, int distance) {
        new Race(distance, racers).start();
    }

    /**
     * Loads the race information panel with the main buttons
     * @return race information panel
     */
    private JPanel loadRaceInformationPanel() {
        JPanel racePanel = new JPanel();
        racePanel.setOpaque(false);
        racePanel.setLayout(new GridBagLayout());

        // Race information
        JLabel totalRacerTitle = new JLabel("Total racers :");
        JLabel totalDistanceTitle  = new JLabel("Total distance (m) : ");
        SpinnerModel model = new SpinnerNumberModel(DEFAULT_DISTANCE,
                MIN_DISTANCE, MAX_DISTANCE, STEP_DISTANCE);
        JSpinner spinner = new JSpinner(model);

        // Main buttons
        JButton quitButton = Utils.getIconJButton("resources/GUI/quit.png", 0.1);
        quitButton.setPreferredSize(new Dimension(50, 50));
        JButton restartButton = Utils.getIconJButton("resources/GUI/restart.png", 0.1);
        restartButton.setPreferredSize(new Dimension(50, 50));
        addOpponentButton = Utils.getIconJButton("resources/GUI/add-opponent.png", 0.1);
        addOpponentButton.setPreferredSize(new Dimension(50, 50));
        JButton startButton = Utils.getIconJButton("resources/GUI/start_race.png", 0.1);
        startButton.setPreferredSize(new Dimension(50, 50));


        quitButton.addActionListener(e -> exit(0));

        restartButton.addActionListener(e -> {
            // Reset opponents and spinner value
            racers.clear();
            updateOpponentsDisplay();
            spinner.setValue(DEFAULT_DISTANCE);
        });

        addOpponentButton.addActionListener(e -> {
            if(autoGeneration) {
                CarBuilder builder = new CarBuilder();
                builder.buildRandomCar(garage);
                addNewRacer(generateNewRacer(builder.getCar(), false));
            } else {
                BuilderFrame newBuilder = new BuilderFrame();
                newBuilder.setVisible(true);
            }
        });

        startButton.addActionListener(actionEvent -> {
            if(racers.size() < MIN_OPPONENTS){
                Utils.popupWarning("Need at least two opponents to launch the race");
            }else{
                startRace(racers, (int) spinner.getValue());
            }
        });

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(3,3,3,3); // padding
        c.fill = GridBagConstraints.BOTH;
        c.anchor=GridBagConstraints.CENTER;
        // Race information
        c.gridx = 0;
        c.gridy = 0;
        racePanel.add(totalRacerTitle, c);
        c.gridy = 1;
        racePanel.add(totalDistanceTitle, c);
        c.gridx = 1;
        c.gridy = 0;
        racePanel.add(totalRacersLabel, c);
        c.gridy = 1;
        racePanel.add(spinner, c);
        // Empty center
        c.gridx = 2;
        c.gridy = 0;
        c.gridheight = 2;
        c.weightx = 1;
        JPanel transparent = new JPanel();
        transparent.setOpaque(false);
        racePanel.add(transparent, c);
        // Main buttons
        c.gridx = 3;
        c.gridy = 0;
        c.gridheight = 2;
        c.weightx = 0.1;
        racePanel.add(quitButton, c);
        c.gridx = 4;
        c.gridheight = 2;
        c.weightx = 0.1;
        racePanel.add(restartButton, c);
        c.gridx = 5;
        c.gridheight = 2;
        c.weightx = 0.1;
        racePanel.add(addOpponentButton, c);
        c.gridx = 6;
        c.gridheight = 2;
        c.weightx = 0.1;
        racePanel.add(startButton, c);

        return racePanel;
    }
}
