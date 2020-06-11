package controller;

import carBuilder.CarBuilder;
import cars.*;
import garage.Garage;
import garage.GarageProduct;
import races.Race;
import races.Racer;
import utils.Utils;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import static java.lang.System.exit;

public class Controller extends JFrame {

    private final int SCREEN_WIDTH  = 900;
    private final int SCREEN_HEIGHT = 600;

    private Garage garage;

    private static Controller instance;

    private Car playerCar;
    private CarDisplayer playerCarDisplayer;
    private CarBuilder builder;
    private JLabel debug;

    // Opponents
    private ArrayList<Racer> racers;
    private JPanel opponentsPanel;
    private JLabel totalRacersLabel;
    JScrollPane opponentsScroll;


    public Controller() {

        setTitle("MCR - Racers");
        racers = new ArrayList<>();
        setupGarage();

        // General app layout (mainPanel) :
        /*---------------------------------------------*
         * CONSOLE MESSAGES                            *
         *---------------------------------------------*
         * OPPONENTS + RACE MENU                        *
         * - Car components / Race information         *
         *                                             *
         *---------------------------------------------*/

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBackground(Color.WHITE);
        // DEBUG / INFO
        debug = new JLabel("Welcome! Start by building your car or generate opponents and then start a race!");
        debug.setBorder(Utils.getPanelBorder("C O N S O L E", Utils.getDefaultColor()));
        debug.setFont(new Font("Consolas", Font.PLAIN, 14));
        // BLUEPRINTS
        //builder = new CarBuilder();
        //playerCarDisplayer = new CarDisplayer(playerCar, builder);
        //playerCarDisplayer.setBorder(getPanelBorder("B L U E P R I N T"));
        // STATS
        //StatsPanel carStatsPanel = new StatsPanel(playerCarDisplayer);
        //carStatsPanel.setBorder(getPanelBorder("C A R    S T A T I S T I C S"));
        // RACE
        //JPanel racePanel = loadRacePanel();
        //racePanel.setBorder(getPanelBorder("R A C E    I N F O R M A T I O N"));
        // OPPONENTS
        opponentsScroll = new JScrollPane();
        totalRacersLabel = new JLabel("0");
        updateOpponentsDisplay();
        opponentsScroll.setViewportView(opponentsPanel);
        opponentsScroll.setBorder(Utils.getPanelBorder("O P P O N E N T S", Utils.getDefaultColor()));
        opponentsScroll.setOpaque(false);
        // BUILDER
        //builderPanel = new BuilderPanel(garage, builder, playerCarDisplayer, playerCar, carStatsPanel, racePanel, opponentsPanel, debug);
        //builderPanel.setBorder(getPanelBorder("C A R    B U I L D E R"));

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
        mainPanel.add(debug, c);
        c.weighty = 0.9;
        c.gridy = 1;
        mainPanel.add(opponentsScroll, c);
        c.gridy = 2;
        c.weighty = 0.05;
        mainPanel.add(loadRacePanel(), c);

        add(mainPanel);

        //Resizing frame
        setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static Controller getInstance() {
        if(instance == null)
            instance = new Controller();

        return instance;
    }

    public void addRacer(Racer racer) {
        this.racers.add(racer);
        updateOpponentsDisplay();
    }

    private void updateOpponentsDisplay() {

        totalRacersLabel.setText(racers.size() + "");
        opponentsPanel = new JPanel(new GridBagLayout());
        opponentsPanel.setOpaque(false);
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(3,3,3,3); // padding
        c.gridy = 0;

        for(int i = 0; i < racers.size(); ++i) {
            // Layout
            c.gridx = i;

            // Car display with stats
            JPanel panel = new JPanel(new GridBagLayout());
            panel.setPreferredSize(new Dimension(250, 300));
            panel.setBorder(Utils.getPanelBorder("PLAYER " + (i+1), racers.get(i).getColor()));
            CarDisplayer carDisplayer = new CarDisplayer(racers.get(i).getCar(), null, 0.5);
            GridBagConstraints c1 = new GridBagConstraints();
            c1.fill = GridBagConstraints.BOTH;
            c1.insets = new Insets(3,3,3,3); // padding
            c1.weighty = 1;
            c1.gridy = 0;
            panel.add(carDisplayer, c1);
            c1.gridy = 1;
            panel.add(new StatsPanel(carDisplayer), c1);

            opponentsPanel.add(panel, c);
        }

        opponentsPanel.repaint();
        opponentsScroll.setViewportView(opponentsPanel);
        opponentsScroll.repaint();
    }

    public Garage getGarage() {
        return garage;
    }

    private void setupGarage() {
        garage = new Garage();

        // Products available in the garage (maybe add the point coordinate in the class)
        // - Car bodies
        GarageProduct bodies = new GarageProduct("Bodies");
        bodies.addProduct(new Body("Body-1", Body.BODY_PATH + "bodyTemplate.png", new Stats().randomize()));
        bodies.addProduct(new Body("Body-3", Body.BODY_PATH + "body-camouflage.png", new Stats()));
        // - Car motors
        GarageProduct motors = new GarageProduct("Motors");
        motors.addProduct(new Motor("Motor-1", Motor.MOTOR_PATH + "motorTemplate.png", new Stats().randomize(), new Point(80, 70)));
        motors.addProduct(new Motor("Motor-2", Motor.MOTOR_PATH + "motor-pipes.png", new Stats().randomize(), new Point(80, 70)));
        // - Car tires
        GarageProduct tires = new GarageProduct("Tires");
        tires.addProduct(new Tires("Sport Socks", Tires.TIRES_PATH + "tires-sport.png", new Stats().randomize(), new Point(90, 120)));
        tires.addProduct(new Tires("Flat Disks", Tires.TIRES_PATH + "tires-disk.png", new Stats().randomize(), new Point(90, 120)));
        // - Car spoilers
        GarageProduct spoilers = new GarageProduct("Spoilers");
        spoilers.addProduct(new Spoiler("Spoiler-1", Spoiler.SPOILER_PATH + "spoilerTemplate.png", new Stats().randomize(), new Point(35, 35)));
        spoilers.addProduct(new Spoiler("Back Street", Spoiler.SPOILER_PATH + "spoiler-street.png", new Stats().randomize(), new Point(35, 60)));

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

    private void startRace(ArrayList<Racer> racers, int distance) {
        new Race(distance, racers).start();
    }

    private JPanel loadRacePanel() {
        JPanel racePanel = new JPanel();
        racePanel.setOpaque(false);
        racePanel.setLayout(new GridBagLayout());

        JLabel totalRacerTitle = new JLabel("Total racers :");
        JLabel totalDistanceTitle  = new JLabel("Total distance (m) : ");
        SpinnerModel model = new SpinnerNumberModel(3000, 500, 50000, 100);
        JSpinner spinner = new JSpinner(model);
        JButton quitButton = Utils.getIconJButton("resources/GUI/quit.png", 0.1);
        quitButton.setPreferredSize(new Dimension(50, 50));
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exit(0);
            }
        });
        JButton restartButton = Utils.getIconJButton("resources/GUI/restart.png", 0.1);
        restartButton.setPreferredSize(new Dimension(50, 50));
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO
            }
        });
        JButton addOpponentButton = Utils.getIconJButton("resources/GUI/add-opponent.png", 0.1);
        addOpponentButton.setPreferredSize(new Dimension(50, 50));
        addOpponentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BuilderFrame newBuilder = new BuilderFrame();
                newBuilder.setVisible(true);
            }
        });
        JButton startButton = Utils.getIconJButton("resources/GUI/start_race.png", 0.1);
        startButton.setPreferredSize(new Dimension(50, 50));
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
