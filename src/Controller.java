
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

    // Car stats
    private final GridLayout statGrid = new GridLayout(1, 5);
    private final JPanel speedStatPanel = new JPanel(statGrid);
    private final JPanel maniabilityStatPanel = new JPanel(statGrid);
    private final JPanel resistanceStatPanel = new JPanel(statGrid);

    private Garage garage;
    private int currentCategory;
    private int currentProduct;
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
        bodies.addProduct(new Body("Body-1", "bodyTemplate.png", new Stats().randomize()));
        bodies.addProduct(new Body("Body-2", "Body_1.png", new Stats()));
        // - Car motors
        GarageProduct motors = new GarageProduct("Motors");
        motors.addProduct(new Motor("Motor-1", "motorTemplate.png", new Stats().randomize(), new Point(80, 70)));
        // - Car tires
        GarageProduct tires = new GarageProduct("Tires");
        tires.addProduct(new Tires("Tires-1", "tiresTemplate.png", new Stats().randomize(), new Point(90, 130)));
        // - Car spoilers
        GarageProduct spoilers = new GarageProduct("Spoilers");
        spoilers.addProduct(new Spoiler("Spoiler-1", "spoilerTemplate.png", new Stats().randomize(), new Point(35, 35)));

        // Adding to the garage inventory
        garage.addToInventory(bodies);
        garage.addToInventory(motors);
        garage.addToInventory(tires);
        garage.addToInventory(spoilers);
    }

    private void startRace() {

        Car car1 = new Car( "Car1",
                new Body("Body", "bodyTemplate.png", new Stats().randomize()),
                new Motor("Motor", "motorTemplate.png", new Stats().randomize(), new Point(80, 70)),
                new Tires("Tires", "tiresTemplate.png", new Stats().randomize(), new Point(90, 130)),
                new Spoiler("Spoiler", "spoilerTemplate.png", new Stats().randomize(), new Point(35, 35)),
                Color.RED);
        Car car2 = new Car( "Car2",
                new Body("Body", "bodyTemplate.png", new Stats().randomize()),
                new Motor("Motor", "motorTemplate.png", new Stats().randomize(), new Point(80, 70)),
                new Tires("Tires", "tiresTemplate.png", new Stats().randomize(), new Point(90, 130)),
                new Spoiler("Spoiler", "spoilerTemplate.png", new Stats().randomize(), new Point(35, 35)),
                Color.BLUE);
        Car car3 = new Car( "Car3",
                new Body("Body", "bodyTemplate.png", new Stats().randomize()),
                new Motor("Motor", "motorTemplate.png", new Stats().randomize(), new Point(80, 70)),
                new Tires("Tires", "tiresTemplate.png", new Stats().randomize(), new Point(90, 130)),
                new Spoiler("Spoiler", "spoilerTemplate.png", new Stats().randomize(), new Point(35, 35)),
                Color.GREEN);
        Car car4 = new Car( "Car4",
                new Body("Body", "bodyTemplate.png", new Stats().randomize()),
                new Motor("Motor", "motorTemplate.png", new Stats().randomize(), new Point(80, 70)),
                new Tires("Tires", "tiresTemplate.png", new Stats().randomize(), new Point(90, 130)),
                new Spoiler("Spoiler", "spoilerTemplate.png", new Stats().randomize(), new Point(35, 35)),
                Color.ORANGE);



        // /!\ RACERS[0] IS ALWAYS THE PLAYER, NOT A BOT /!\
        Racer racer1 = new Racer("Player1", car1, Color.RED, false);
        Racer racer2 = new Racer("Player2", car2, Color.BLUE, false);
        Racer racer3 = new Racer("Player3", car2, Color.GREEN, false);
        Racer racer4 = new Racer("Player4", car2, Color.ORANGE, false);

        ArrayList<Racer> racers = new ArrayList<>();
        racers.add(racer1);
        racers.add(racer2);
        racers.add(racer3);
        racers.add(racer4);

        Race race = new Race(3000, racers);

        race.start();
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

    private void updateStats() {
        // TODO : refactor
        speedStatPanel.removeAll();
        maniabilityStatPanel.removeAll();
        resistanceStatPanel.removeAll();
        for(int i = 1; i <= 5; ++i) {
            JPanel stat = new JPanel();
            stat.setBackground(playerCarDisplayer.getStats().getSpeed() <= i ? Color.GRAY : Color.GREEN);
            speedStatPanel.add(stat);
        }
        for(int i = 1; i <= 5; ++i) {
            JPanel stat = new JPanel();
            stat.setBackground(playerCarDisplayer.getStats().getManiability() <= i ? Color.GRAY : Color.GREEN);
            maniabilityStatPanel.add(stat);
        }
        for(int i = 1; i <= 5; ++i) {
            JPanel stat = new JPanel();
            stat.setBackground(playerCarDisplayer.getStats().getResistance() <= i ? Color.GRAY : Color.GREEN);
            resistanceStatPanel.add(stat);
        }
        speedStatPanel.validate();
        maniabilityStatPanel.validate();
        resistanceStatPanel.validate();
        speedStatPanel.repaint();
        maniabilityStatPanel.repaint();
        resistanceStatPanel.repaint();
    }

    /*----------------------------------------------------------------*
     *                      JAVA SWING PANELS                         *
     *----------------------------------------------------------------*/
    private JPanel loadCarStatsPanel() {
        JPanel carStatsPanel = new JPanel();
        carStatsPanel.setOpaque(false);
        GridLayout statsListGrid = new GridLayout(3, 2);
        statsListGrid.setHgap(10);
        statsListGrid.setVgap(10);
        carStatsPanel.setLayout(statsListGrid);

        statGrid.setVgap(10);
        statGrid.setHgap(10);

        updateStats();

        carStatsPanel.add(new JLabel("SPEED"));
        carStatsPanel.add(speedStatPanel);
        carStatsPanel.add(new JLabel("MANIABILITY"));
        carStatsPanel.add(maniabilityStatPanel);
        carStatsPanel.add(new JLabel("RESISTANCE"));
        carStatsPanel.add(resistanceStatPanel);
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
        JButton randomCarButton = new JButton("Get random");
        JButton mountToCarButton = new JButton("Add to blueprint");
        JButton buildCarButton   = new JButton("Build car");
        randomCarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Random random = new Random();
                builder.buildBody((Body) getRandCarPart(random, Garage.CATEGORY_BODY))
                       .buildMotor((Motor) getRandCarPart(random, Garage.CATEGORY_MOTORS))
                       .buildTire((Tires) getRandCarPart(random, Garage.CATEGORY_TIRES))
                       .buildSpoiler((Spoiler) getRandCarPart(random, Garage.CATEGORY_SPOILERS));

                playerCarDisplayer.repaint();
            }
        });
        mountToCarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add car part to the player's car
                CarPart newPart = garage.getInventory().get(currentCategory).getProducts().get(currentProduct);
                System.out.println("Mounting new part : " + newPart.getName());
                switch(currentCategory) {
                    case Garage.CATEGORY_BODY:
                        builder.buildBody((Body) newPart);
                        break;
                    case Garage.CATEGORY_MOTORS:
                        builder.buildMotor((Motor) newPart);
                        break;
                    case Garage.CATEGORY_SPOILERS:
                        builder.buildSpoiler((Spoiler) newPart);
                        break;
                    case Garage.CATEGORY_TIRES:
                        builder.buildTire((Tires) newPart);
                        break;
                }

                playerCarDisplayer.repaint();

                // Update the stats grids
                updateStats();
            }
        });
        buildCarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // Build car
                try {
                    playerCar = builder.getCar();
                    playerCarDisplayer.setCar(playerCar);
                    playerCarDisplayer.repaint();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace(); // TODO affichage user
                }
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
        selectionPanel.add(productLeftButton, c);
        c.gridx = 1;
        selectionPanel.add(productLabel, c);
        c.gridx = 2;
        selectionPanel.add(productRightButton, c);
        c.gridy = 2;
        c.gridx = 0;
        c.ipady = 30; // make the button bigger
        selectionPanel.add(randomCarButton, c);
        c.gridx = 1;
        selectionPanel.add(mountToCarButton, c);
        c.gridx = 2;
        selectionPanel.add(buildCarButton, c);
        updateSelectionLabels(categoryLabel, productLabel, carPartPanel);
        builderPanel.add(carPartPanel);
        builderPanel.add(selectionPanel);

        return builderPanel;
    }

    private CarPart getRandCarPart(Random rand, int category) {
        return garage.getInventory().get(category).getProducts()
                .get(rand.nextInt(garage.getInventory().get(category)
                        .getProducts().size()));
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
