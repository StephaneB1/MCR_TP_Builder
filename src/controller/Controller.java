package controller;

import carBuilder.CarBuilder;
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
import java.util.Random;

import static java.lang.System.exit;

public class Controller extends JFrame {

    private final int SCREEN_WIDTH  = 1200;
    private final int SCREEN_HEIGHT = 600;

    private Garage garage;

    private Car playerCar;
    private CarDisplayer playerCarDisplayer;
    private CarBuilder builder;

    public Controller() {

        setTitle("MCR - Racers");
        setupGarage();

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
        builder = new CarBuilder();
        playerCarDisplayer = new CarDisplayer(playerCar, builder);
        playerCarDisplayer.setBorder(BorderFactory.createTitledBorder("B L U E P R I N T"));
        // STATS
        StatsPanel carStatsPanel = new StatsPanel(playerCarDisplayer);
        carStatsPanel.setBorder(BorderFactory.createTitledBorder("S T A T I S T I C S"));
        // BUILDER
        BuilderPanel builderPanel = new BuilderPanel(garage, builder, playerCarDisplayer, playerCar, carStatsPanel);
        builderPanel.setBorder(BorderFactory.createTitledBorder("C A R    B U I L D E R"));
        // RACE
        JPanel racePanel = loadRacePanel();
        racePanel.setBorder(BorderFactory.createTitledBorder("R A C E    I N F O R M A T I O N"));

        // Add panels with padding
        Border padding = BorderFactory.createEmptyBorder(20, 20, 20, 20);
        mainPanel.setBorder(padding);
        mainPanel.add(playerCarDisplayer);
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
        bodies.addProduct(new Body("Body-1", Body.BODY_PATH + "bodyTemplate.png", new Stats().randomize()));
        bodies.addProduct(new Body("Body-2", Body.BODY_PATH + "Body_1.png", new Stats()));
        // - Car motors
        GarageProduct motors = new GarageProduct("Motors");
        motors.addProduct(new Motor("Motor-1", Motor.MOTOR_PATH + "motorTemplate.png", new Stats().randomize(), new Point(80, 70)));
        // - Car tires
        GarageProduct tires = new GarageProduct("Tires");
        tires.addProduct(new Tires("Tires-1", Tires.TIRES_PATH + "tiresTemplate.png", new Stats().randomize(), new Point(90, 130)));
        tires.addProduct(new Tires("Tires-2", Tires.TIRES_PATH + "tiresTest.png", new Stats().randomize(), new Point(90, 130)));
        // - Car spoilers
        GarageProduct spoilers = new GarageProduct("Spoilers");
        spoilers.addProduct(new Spoiler("Spoiler-1", Spoiler.SPOILER_PATH + "spoilerTemplate.png", new Stats().randomize(), new Point(35, 35)));

        // Adding to the garage inventory
        garage.addToInventory(bodies);
        garage.addToInventory(motors);
        garage.addToInventory(tires);
        garage.addToInventory(spoilers);

        // Adding the paint jobs
        garage.addPaintJob(new PaintJob("Ocean Blue", Color.BLUE)); // TODO change better colors
        garage.addPaintJob(new PaintJob("Red Lobster", Color.RED)); // TODO change better colors
        garage.addPaintJob(new PaintJob("Apple Storm", Color.GREEN)); // TODO change better colors
        garage.addPaintJob(new PaintJob("Pink Duster", Color.PINK)); // TODO change better colors

    }

    private void startRace() {

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

        Race race = new Race(3000, racers);

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
