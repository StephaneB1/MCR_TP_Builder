package controller;

import carBuilder.CarBuilder;
import cars.*;
import garage.Garage;
import garage.GarageProduct;
import races.Racer;
import utils.Utils;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class BuilderFrame extends JFrame {

    private final int SCREEN_WIDTH  = 900;
    private final int SCREEN_HEIGHT = 600;

    private static int totalBuilder = 0;

    // Reference to this frame to close is on action event
    private final BuilderFrame mainFrame = this;

    // Garage data
    private Garage garage;
    private int currentCategory;
    private int currentProduct;
    private int currentColor;

    // Blueprint
    private CarDisplayer displayer;
    private CarBuilder builder;
    private Car car;

    // Stats
    private StatsPanel statsPanel;
    private StatsPanel carPartStatsPanel;


    public BuilderFrame() {
        garage = Controller.getInstance().getGarage();

        setTitle("MCR - Racers : Car Builder #" + ++totalBuilder);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBackground(Color.WHITE);
        GridBagConstraints c2 = new GridBagConstraints();

        // BLUEPRINTS
        builder = new CarBuilder();
        displayer = new CarDisplayer(car, builder, 0.8);
        displayer.setBorder(Utils.getPanelBorder("B L U E P R I N T", Utils.getDefaultColor()));
        // STATS
        statsPanel = new StatsPanel(displayer, 1);
        statsPanel.setBorder(Utils.getPanelBorder("C A R    S T A T I S T I C S", Utils.getDefaultColor()));

        // BUILDER PANEL
        JPanel builderPanel = new JPanel();
        builderPanel.setLayout(new GridLayout(1, 2));
        builderPanel.setBorder(Utils.getPanelBorder("G A R A G E    P R O D U C T S", Utils.getDefaultColor()));
        builderPanel.setOpaque(false);
        // Car Part display with stats
        JPanel carPartPanel = new JPanel(new GridBagLayout());
        JPanel carPartDisplay = new JPanel(new GridBagLayout());
        carPartPanel.setOpaque(false);
        carPartDisplay.setOpaque(false);
        GridBagConstraints c1 = new GridBagConstraints();
        c1.insets = new Insets(3,3,3,3); // padding
        c1.anchor = GridBagConstraints.CENTER;
        c1.gridy = 0;
        c1.gridx = 0;
        c1.weighty = 0.9;
        carPartPanel.add(carPartDisplay, c1);
        c1.gridy = 1;
        c1.weighty = 0.1;
        carPartStatsPanel = new StatsPanel(null, 1);
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
        // Fonts
        categoryLabel.setFont(Utils.DEFAULT_FONT);
        productLabel.setFont(Utils.DEFAULT_FONT);
        colorLabel.setFont(Utils.DEFAULT_FONT);


        JButton categoryLeftButton = Utils.getIconJButton("resources/GUI/left-arrow.png");
        categoryLeftButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentCategory = getPrevious(currentCategory, garage.getInventory().size());
                currentProduct  = 0;
                updateSelectionLabels(categoryLabel, productLabel, colorLabel, carPartDisplay);
            }
        });
        JButton categoryRightButton = Utils.getIconJButton("resources/GUI/right-arrow.png");
        categoryRightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentCategory = getNext(currentCategory, garage.getInventory().size());
                currentProduct  = 0;
                updateSelectionLabels(categoryLabel, productLabel, colorLabel, carPartDisplay);
            }
        });
        JButton productLeftButton = Utils.getIconJButton("resources/GUI/left-arrow.png");
        productLeftButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentProduct = getPrevious(currentProduct, garage.getInventory().get(currentCategory).getProducts().size());
                updateSelectionLabels(categoryLabel, productLabel, colorLabel, carPartDisplay);
            }
        });
        JButton productRightButton = Utils.getIconJButton("resources/GUI/right-arrow.png");
        productRightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentProduct = getNext(currentProduct, garage.getInventory().get(currentCategory).getProducts().size());
                updateSelectionLabels(categoryLabel, productLabel, colorLabel, carPartDisplay);
            }
        });
        JButton colorLeftButton = Utils.getIconJButton("resources/GUI/left-arrow.png");
        colorLeftButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentColor = getPrevious(currentColor, garage.getPaintJobs().size());
                updateSelectionLabels(categoryLabel, productLabel, colorLabel, carPartDisplay);
            }
        });
        JButton colorRightButton = Utils.getIconJButton("resources/GUI/right-arrow.png");
        colorRightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentColor = getNext(currentColor, garage.getPaintJobs().size());
                updateSelectionLabels(categoryLabel, productLabel, colorLabel, carPartDisplay);
            }
        });
        JButton mountToCarButton = Utils.getIconJButton("resources/GUI/add-blueprint_v2.png", 0.4);
        JButton randomCarButton = Utils.getIconJButton("resources/GUI/random-button_v2.png", 0.4);
        JButton buildCarButton = Utils.getIconJButton("resources/GUI/build-car_v2.png", 0.4);
        randomCarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Utils.buildRandomCar(garage, builder);
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

                switch (currentCategory) {
                    case Garage.CATEGORY_BODY:
                        builder.buildBody((Body) newPart);
                        break;
                    case Garage.CATEGORY_MOTORS:
                        if (builder.buildMotor((Motor) newPart) == null) {
                            setNotBodyErrorMessage("a motor");
                        }
                        break;
                    case Garage.CATEGORY_SPOILERS:
                        if (builder.buildSpoiler((Spoiler) newPart) == null) {
                            setNotBodyErrorMessage("a spoiler");
                        }
                        break;
                    case Garage.CATEGORY_TIRES:
                        if (builder.buildTire((Tires) newPart) == null) {
                            setNotBodyErrorMessage("tires");
                        }
                        break;
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
                    mainFrame.dispose();
                    Controller.getInstance().addNewRacer(builder.getCar(), true);
                } else {
                    // TODO : Pop up
                    //debug.setForeground(Color.RED);
                    //debug.setText("Your blueprint is incomplete! You're missing some parts!");
                }
            }
        });
        c.insets = new Insets(3, 3, 3, 3); // padding
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
        c.gridwidth = 3;
        selectionPanel.add(mountToCarButton, c);
        c.gridy = 4;
        c.gridwidth = 3;
        selectionPanel.add(randomCarButton, c);
        c.gridy = 5;
        c.gridwidth = 3;
        selectionPanel.add(buildCarButton, c);
        updateSelectionLabels(categoryLabel, productLabel, colorLabel, carPartDisplay);
        builderPanel.add(carPartPanel);
        builderPanel.add(selectionPanel);

        carPartStatsPanel.updateStats(garage.getInventory().get(currentCategory).getProducts().get(currentProduct).getStats());

        c2.insets = new Insets(3,3,3,3); // padding
        c2.fill = GridBagConstraints.BOTH;
        c2.anchor = GridBagConstraints.CENTER;
        c2.gridy = 0;
        c2.gridx = 0;
        c2.weightx = 0.6;
        mainPanel.add(displayer, c2);
        c2.gridx = 1;
        c2.weightx = 0.2;
        mainPanel.add(statsPanel, c2);
        c2.gridx = 0;
        c2.gridy = 1;
        c2.gridwidth = 2;
        mainPanel.add(builderPanel, c2);

        Border padding = BorderFactory.createEmptyBorder(20, 20, 20, 20);
        mainPanel.setBorder(padding);
        add(mainPanel);

        //Resizing frame
        setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
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

    private int getNext(int current, int size) {
        return (current + 1) % size;
    }

    private int getPrevious(int current, int size) {
        return (current - 1 + size) % size;
    }

    private void setNotBodyErrorMessage(String part) {
        //debug.setForeground(Color.RED);
        //debug.setText("Can't add " + part + " without a body");
    }
}
