package controller;

import carBuilder.CarBuilder;
import cars.*;
import garage.Garage;
import garage.GarageProduct;
import races.Racer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class BuilderPanel extends JPanel {

    private static final int TOTAL_RACERS = 5;

    private Garage garage;
    private CarDisplayer displayer;
    private CarBuilder builder;
    private Car car;
    private StatsPanel statsPanel;
    private StatsPanel carPartStatsPanel;
    private JPanel racePanel;
    private JPanel opponentsPanel;
    private JLabel debug;
    private int currentCategory;
    private int currentProduct;
    private int currentColor;

    private ArrayList<Racer> racers;

    public BuilderPanel(Garage garage, CarBuilder builder, CarDisplayer displayer,
                        Car car, StatsPanel statsPanel, JPanel racePanel, JPanel opponentsPanel,
                        JLabel debug) {
        this.garage = garage;
        this.builder = builder;
        this.displayer = displayer;
        this.car = car;
        this.statsPanel = statsPanel;
        this.racePanel = racePanel;
        this.opponentsPanel = opponentsPanel;
        this.debug = debug;
        racers = new ArrayList<>();
        setupPanel();
    }

    private void setupPanel() {

        setOpaque(true);
        setLayout(new GridLayout(1, 2));
        // Car Part display with stats
        JPanel carPartPanel = new JPanel(new GridBagLayout());
        JPanel carPartDisplay = new JPanel(new GridBagLayout());
        carPartDisplay.setOpaque(false);
        GridBagConstraints c1 = new GridBagConstraints();
        c1.insets = new Insets(3,3,3,3); // padding
        c1.fill = GridBagConstraints.HORIZONTAL;
        c1.anchor=GridBagConstraints.CENTER;
        c1.gridy = 0;
        c1.gridx = 0;
        c1.weighty = 0.8;
        carPartPanel.add(carPartDisplay, c1);
        c1.gridy = 1;
        c1.weighty = 0.2;
        carPartStatsPanel = new StatsPanel(null);
        carPartPanel.add(carPartStatsPanel, c1);
        // Car Part selection display
        JPanel selectionPanel = new JPanel();
        selectionPanel.setOpaque(false);
        selectionPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        // -- Selection labels
        JLabel categoryLabel = new JLabel(garage.getInventory().get(currentCategory).getProductLabel());
        categoryLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel productLabel = new JLabel(garage.getInventory().get(currentCategory).getProducts().get(currentProduct).getName());
        productLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel colorLabel = new JLabel(garage.getPaintJobs().get(currentColor).getName());
        colorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JButton categoryLeftButton = getIconJButton("resources/GUI/left-arrow.png");
        categoryLeftButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentCategory = getPrevious(currentCategory, garage.getInventory().size());
                currentProduct  = 0;
                updateSelectionLabels(categoryLabel, productLabel, colorLabel, carPartDisplay);
            }
        });
        JButton categoryRightButton = getIconJButton("resources/GUI/right-arrow.png");
        categoryRightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentCategory = getNext(currentCategory, garage.getInventory().size());
                currentProduct  = 0;
                updateSelectionLabels(categoryLabel, productLabel, colorLabel, carPartDisplay);
            }
        });
        JButton productLeftButton = getIconJButton("resources/GUI/left-arrow.png");
        productLeftButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentProduct = getPrevious(currentProduct, garage.getInventory().get(currentCategory).getProducts().size());
                updateSelectionLabels(categoryLabel, productLabel, colorLabel, carPartDisplay);
            }
        });
        JButton productRightButton = getIconJButton("resources/GUI/right-arrow.png");
        productRightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentProduct = getNext(currentProduct, garage.getInventory().get(currentCategory).getProducts().size());
                updateSelectionLabels(categoryLabel, productLabel, colorLabel, carPartDisplay);
            }
        });
        JButton colorLeftButton = getIconJButton("resources/GUI/left-arrow.png");
        colorLeftButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentColor = getPrevious(currentColor, garage.getPaintJobs().size());
                updateSelectionLabels(categoryLabel, productLabel, colorLabel, carPartDisplay);
            }
        });
        JButton colorRightButton = getIconJButton("resources/GUI/right-arrow.png");
        colorRightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentColor = getNext(currentColor, garage.getPaintJobs().size());
                updateSelectionLabels(categoryLabel, productLabel, colorLabel, carPartDisplay);
            }
        });
        JButton randomCarButton = getIconJButton("resources/GUI/random-button.png");
        JButton mountToCarButton = getIconJButton("resources/GUI/add-blueprint.png");
        JButton buildCarButton = getIconJButton("resources/GUI/build-car.png");
        randomCarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                buildRandomCar(builder);
                displayer.repaint();
                statsPanel.updateStats();
            }
        });
        mountToCarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add car part to the player's car (by cloning a template from the garage)
                CarPart newPart = garage.getInventory().get(currentCategory).getProducts().get(currentProduct).clone();
                newPart.setColor(garage.getPaintJobs().get(currentColor).getColor());

                boolean success = true;
                switch (currentCategory) {
                    case Garage.CATEGORY_BODY:
                        builder.buildBody((Body) newPart);
                        break;
                    case Garage.CATEGORY_MOTORS:
                        if (builder.buildMotor((Motor) newPart) == null) {
                            setNotBodyErrorMessage("a motor");
                            success = false;
                        }
                        break;
                    case Garage.CATEGORY_SPOILERS:
                        if (builder.buildSpoiler((Spoiler) newPart) == null) {
                            setNotBodyErrorMessage("a spoiler");
                            success = false;
                        }
                        break;
                    case Garage.CATEGORY_TIRES:
                        if (builder.buildTire((Tires) newPart) == null) {
                            setNotBodyErrorMessage("tires");
                            success = false;
                        }
                        break;
                }

                if (success) {
                    debug.setForeground(Color.BLACK);
                    debug.setText("Added a new car part to the blueprint : " + newPart.getName() + " !");
                }

                displayer.repaint();

                // Update the stats grids
                statsPanel.updateStats();
            }
        });
        buildCarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // Build car
                if (builder.getCar() != null) {
                    // Building and displaying the opponents
                    generateRacers();
                    for (int i = 1; i < racers.size(); ++i) {
                        CarDisplayer carDisplayer = new CarDisplayer(racers.get(i).getCar(), null, 0.5);
                        opponentsPanel.add(carDisplayer);
                    }

                    // Changing the layout to start a race
                    setVisible(false);
                    racePanel.setVisible(true);
                    opponentsPanel.setVisible(true);
                    car = builder.getCar();
                    displayer.setCar(car);
                    displayer.repaint();
                    debug.setForeground(Color.BLACK);
                    debug.setText("Well done, it's a beauty. Now let's race!");
                } else {
                    debug.setForeground(Color.RED);
                    debug.setText("Your blueprint is incomplete! You're missing some parts!");
                }
            }
        });
        c.insets = new Insets(3, 3, 3, 3); // padding
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.CENTER;
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
        selectionPanel.add(colorLeftButton, c);
        c.gridx = 1;
        selectionPanel.add(colorLabel, c);
        c.gridx = 2;
        selectionPanel.add(colorRightButton, c);
        c.gridy = 3;
        c.gridx = 0;
        c.ipady = 20;
        selectionPanel.add(randomCarButton, c);
        c.gridx = 1;
        selectionPanel.add(mountToCarButton, c);
        c.gridx = 2;
        selectionPanel.add(buildCarButton, c);
        updateSelectionLabels(categoryLabel, productLabel, colorLabel, carPartDisplay);
        add(carPartPanel);
        add(selectionPanel);

        carPartStatsPanel.updateStats(garage.getInventory().get(currentCategory).getProducts().get(currentProduct).getStats());
    }

    private void updateSelectionLabels(JLabel categoryLabel, JLabel productLabel,
                                       JLabel colorLabel, JPanel carPartLabel) {
        GarageProduct category = garage.getInventory().get(currentCategory);
        CarPart product = category.getProducts().get(currentProduct);
        PaintJob paintJob = garage.getPaintJobs().get(currentColor);

        // Selection labels
        categoryLabel.setText(category.getProductLabel());
        productLabel.setText(product.getName());
        colorLabel.setText(paintJob.getName());

        // Product display with the chosen tinted color
        JLabel picLabel = new JLabel(new ImageIcon(product.getTintedImage(paintJob.getColor())));
        carPartLabel.removeAll();
        carPartLabel.add(picLabel);
        carPartLabel.repaint();
        carPartStatsPanel.updateStats(product.getStats());
    }

    private CarPart getRandCarPart(Random rand, int category) {
        CarPart result = garage.getInventory().get(category).getProducts()
                .get(rand.nextInt(garage.getInventory().get(category)
                        .getProducts().size())).clone();
        result.setColor(garage.getPaintJobs().get(rand.nextInt(garage.getPaintJobs().size())).getColor());
        return result;
    }

    private int getNext(int current, int size) {
        return (current + 1) % size;
    }

    private int getPrevious(int current, int size) {
        return (current - 1 + size) % size;
    }

    private void buildRandomCar(CarBuilder builder) {
        Random random = new Random();
        builder.buildBody((Body) getRandCarPart(random, Garage.CATEGORY_BODY))
                .buildMotor((Motor) getRandCarPart(random, Garage.CATEGORY_MOTORS))
                .buildTire((Tires) getRandCarPart(random, Garage.CATEGORY_TIRES))
                .buildSpoiler((Spoiler) getRandCarPart(random, Garage.CATEGORY_SPOILERS));
    }

    private void generateRacers() {
        // Add the player
        racers.add(new Racer("Player1", builder.getCar(), Color.RED, true));
        racers.add(new Racer("Player2", builder.getCar(), Color.BLUE, true));

        // Generate random racers
        CarBuilder opponentBuilder = new CarBuilder();
        for (int i = 1; i < TOTAL_RACERS; ++i) {
            buildRandomCar(opponentBuilder);
            racers.add(new Racer("Racer" + i, opponentBuilder.getCar(), Color.BLACK, true));
        }
    }

    public ArrayList<Racer> getRacers() {
        return racers;
    }

    private JButton getIconJButton(String iconPath) {
        JButton result = new JButton("", new ImageIcon(iconPath));
        result.setBorderPainted(false);
        result.setContentAreaFilled(false);
        result.setFocusPainted(false);
        result.setOpaque(false);
        return result;
    }


    private void setNotBodyErrorMessage(String part) {
        debug.setForeground(Color.RED);
        debug.setText("Can't add " + part + " without a body");
    }
}
