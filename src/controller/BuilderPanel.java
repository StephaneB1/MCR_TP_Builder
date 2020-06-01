package controller;

import carBuilder.CarBuilder;
import cars.*;
import garage.Garage;
import garage.GarageProduct;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class BuilderPanel extends JPanel {

    private Garage garage;
    private CarDisplayer displayer;
    private CarBuilder builder;
    private Car car;
    private StatsPanel statsPanel;
    private int currentCategory;
    private int currentProduct;
    private int currentColor;

    public BuilderPanel(Garage garage, CarBuilder builder, CarDisplayer displayer,
                        Car car, StatsPanel statsPanel) {
        this.garage     = garage;
        this.builder    = builder;
        this.displayer  = displayer;
        this.car        = car;
        this.statsPanel = statsPanel;
        setupPanel();
    }

    private void setupPanel() {

        setOpaque(true);
        setLayout(new GridLayout(2, 1));
        // Car Part display
        JPanel carPartPanel = new JPanel();
        carPartPanel.setOpaque(false);
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
        JLabel colorLabel   = new JLabel(garage.getPaintJobs().get(currentColor).getName());
        colorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JButton categoryLeftButton = new JButton("<");
        categoryLeftButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentCategory = getPrevious(currentCategory, garage.getInventory().size());
                currentProduct  = 0;
                updateSelectionLabels(categoryLabel, productLabel, colorLabel, carPartPanel);
            }
        });
        JButton categoryRightButton = new JButton(">");
        categoryRightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentCategory = getNext(currentCategory, garage.getInventory().size());
                currentProduct  = 0;
                updateSelectionLabels(categoryLabel, productLabel, colorLabel, carPartPanel);
            }
        });
        JButton productLeftButton = new JButton("<");
        productLeftButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentProduct = getPrevious(currentProduct, garage.getInventory().get(currentCategory).getProducts().size());
                updateSelectionLabels(categoryLabel, productLabel, colorLabel, carPartPanel);
            }
        });
        JButton productRightButton = new JButton(">");
        productRightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentProduct = getNext(currentProduct, garage.getInventory().get(currentCategory).getProducts().size());
                updateSelectionLabels(categoryLabel, productLabel, colorLabel, carPartPanel);
            }
        });
        JButton colorLeftButton = new JButton("<");
        colorLeftButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentColor = getPrevious(currentColor, garage.getPaintJobs().size());
                updateSelectionLabels(categoryLabel, productLabel, colorLabel, carPartPanel);
            }
        });
        JButton colorRightButton = new JButton(">");
        colorRightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentColor = getNext(currentColor, garage.getPaintJobs().size());
                updateSelectionLabels(categoryLabel, productLabel, colorLabel, carPartPanel);
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

                displayer.repaint();
            }
        });
        mountToCarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add car part to the player's car (by cloning a template from the garage)
                CarPart newPart = garage.getInventory().get(currentCategory).getProducts().get(currentProduct).clone();
                newPart.setColor(garage.getPaintJobs().get(currentColor).getColor());
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

                displayer.repaint();

                // Update the stats grids
                statsPanel.updateStats();
            }
        });
        buildCarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // Build car
                try {
                    car = builder.getCar();
                    displayer.setCar(car);
                    displayer.repaint();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace(); // TODO affichage user
                }
            }
        });
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
        selectionPanel.add(colorLeftButton, c);
        c.gridx = 1;
        selectionPanel.add(colorLabel, c);
        c.gridx = 2;
        selectionPanel.add(colorRightButton, c);
        c.gridy = 3;
        c.gridx = 0;
        //c.ipady = 30; // make the button bigger
        selectionPanel.add(randomCarButton, c);
        c.gridx = 1;
        selectionPanel.add(mountToCarButton, c);
        c.gridx = 2;
        selectionPanel.add(buildCarButton, c);
        updateSelectionLabels(categoryLabel, productLabel, colorLabel, carPartPanel);
        add(carPartPanel);
        add(selectionPanel);
    }

    private void updateSelectionLabels(JLabel categoryLabel, JLabel productLabel,
                                       JLabel colorLabel, JPanel carPartLabel) {
        GarageProduct category = garage.getInventory().get(currentCategory);
        CarPart product        = category.getProducts().get(currentProduct);
        PaintJob paintJob      = garage.getPaintJobs().get(currentColor);

        // Selection labels
        categoryLabel.setText(category.getProductLabel());
        productLabel.setText(product.getName());
        colorLabel.setText(paintJob.getName());

        // Product display with the chosen tinted color
        JLabel picLabel = new JLabel(new ImageIcon(product.getTintedImage(paintJob.getColor())));
        carPartLabel.removeAll();
        carPartLabel.add(picLabel);
        carPartLabel.repaint();
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
}
