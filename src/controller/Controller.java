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
import javax.swing.border.CompoundBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import static java.lang.System.exit;

public class Controller extends JFrame {

    private final int SCREEN_WIDTH  = 1200;
    private final int SCREEN_HEIGHT = 800;

    private Garage garage;

    private Car playerCar;
    private CarDisplayer playerCarDisplayer;
    private CarBuilder builder;
    private JLabel debug;

    private BuilderPanel builderPanel;

    public Controller() {

        setTitle("MCR - Racers");
        setupGarage();

        // General app layout (mainPanel) :
        /*---------------------------------------------*
         * MCR - Racers                                *
         *---------------------------------------------*
         * DEBUG MESSAGES                              *
         *---------------------------------------------*
         * BLUEPRINTS            | STATS               *
         * - Car preview         | - Car performances  *
         *                       |                     *
         *=============================================*
         * BUILDER / OPPONENT + RACE                   *
         * - Car components / Race information         *
         *                                             *
         *---------------------------------------------*/

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBackground(Color.WHITE);
        // DEBUG / INFO
        debug = new JLabel("Welcome! Build your dream car and then you can start the race!");
        debug.setBorder(getPanelBorder("C O N S O L E"));
        debug.setFont(new Font("Consolas", Font.PLAIN, 14));
        // BLUEPRINTS
        builder = new CarBuilder();
        playerCarDisplayer = new CarDisplayer(playerCar, builder);
        playerCarDisplayer.setBorder(getPanelBorder("B L U E P R I N T"));
        // STATS
        StatsPanel carStatsPanel = new StatsPanel(playerCarDisplayer);
        carStatsPanel.setBorder(getPanelBorder("C A R    S T A T I S T I C S"));
        // RACE
        JPanel racePanel = loadRacePanel();
        racePanel.setBorder(getPanelBorder("R A C E    I N F O R M A T I O N"));
        // OPPONENTS
        JPanel opponentsPanel = new JPanel(new GridLayout(2, 2));
        opponentsPanel.setBorder(getPanelBorder("O P P O N E N T S"));
        opponentsPanel.setVisible(false);
        opponentsPanel.setOpaque(false);
        // BUILDER
        builderPanel = new BuilderPanel(garage, builder, playerCarDisplayer, playerCar, carStatsPanel, racePanel, opponentsPanel, debug);
        builderPanel.setBorder(getPanelBorder("C A R    B U I L D E R"));


        // Add panels with padding
        Border padding = BorderFactory.createEmptyBorder(20, 20, 20, 20);
        mainPanel.setBorder(padding);
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(3,3,3,3); // padding
        c.weightx = 1;
        c.weighty = 0.1;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        mainPanel.add(debug, c);
        c.weighty = 1;
        c.gridwidth = 1;
        c.gridy = 1;
        mainPanel.add(playerCarDisplayer, c);
        c.gridx = 1;
        c.weightx = 0.1;
        mainPanel.add(carStatsPanel, c);
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 2;
        mainPanel.add(builderPanel, c);
        racePanel.setVisible(false);
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 2;
        mainPanel.add(opponentsPanel, c);
        c.gridx = 1;
        mainPanel.add(racePanel, c);

        add(mainPanel);

        //Resizing frame
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(false);
        pack();
        setVisible(true);
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

    private void startRace(ArrayList<Racer> racers) {
/*
        Car car1 = new Car( "Car1",
                new Body("Body", "bodyTemplate.png", new Stats(5, 1, 1)),
                new Motor("Motor", "motorTemplate.png", new Stats(5, 1, 1), new Point(80, 70)),
                new Tires("Tires", "tiresTemplate.png", new Stats(5, 1, 1), new Point(90, 130)),
                new Spoiler("Spoiler", "spoilerTemplate.png", new Stats(5, 1, 1), new Point(35, 35)),
                Color.RED);
        Car car2 = new Car( "Car2",
                new Body("Body", "bodyTemplate.png", new Stats(2.5, 2.5, 2.5)),
                new Motor("Motor", "motorTemplate.png", new Stats(2.5, 2.5, 2.5), new Point(80, 70)),
                new Tires("Tires", "tiresTemplate.png", new Stats(2.5, 2.5, 2.5), new Point(90, 130)),
                new Spoiler("Spoiler", "spoilerTemplate.png", new Stats(2.5, 2.5, 2.5), new Point(35, 35)),
                Color.BLUE);
        Car car3 = new Car( "Car3",
                new Body("Body", "bodyTemplate.png", new Stats(1.5, 4, 4)),
                new Motor("Motor", "motorTemplate.png", new Stats(1.5, 4, 4), new Point(80, 70)),
                new Tires("Tires", "tiresTemplate.png", new Stats(1.5, 4, 4), new Point(90, 130)),
                new Spoiler("Spoiler", "spoilerTemplate.png", new Stats(1, 4, 4), new Point(35, 35)),
                Color.GREEN);
        Car car4 = new Car( "Car4",
                new Body("Body", "bodyTemplate.png", new Stats().randomize()),
                new Motor("Motor", "motorTemplate.png", new Stats().randomize(), new Point(80, 70)),
                new Tires("Tires", "tiresTemplate.png", new Stats().randomize(), new Point(90, 130)),
                new Spoiler("Spoiler", "spoilerTemplate.png", new Stats().randomize(), new Point(35, 35)),
                Color.ORANGE);

        // /!\ RACERS[0] IS ALWAYS THE PLAYER, NOT A BOT /!\
        Racer racer1 = new Racer("SpeedMaster", car1, Color.RED, false);
        Racer racer2 = new Racer("Balanced", car2, Color.BLUE, false);
        Racer racer3 = new Racer("Tank", car3, Color.GREEN, false);
        Racer racer4 = new Racer("Random", car4, Color.ORANGE, false);

        ArrayList<Racer> racers = new ArrayList<>();
        racers.add(racer1);
        racers.add(racer2);
        racers.add(racer3);
        racers.add(racer4);
*/
        Race race = new Race(1000, racers);

        race.start();
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
                startRace(builderPanel.getRacers());
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

    public static Border getPanelBorder(String title) {
        Border comp;
        TitledBorder titledBorder =BorderFactory.createTitledBorder(title);
        titledBorder.setTitleFont(Utils.getDefaultFont(15));
        Border margin = BorderFactory.createEmptyBorder(10,10,10,10);
        comp = BorderFactory.createCompoundBorder(titledBorder, margin);
        return comp;
    }
}
