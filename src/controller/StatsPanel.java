package controller;

import cars.CarDisplayer;
import cars.Stats;
import utils.Utils;

import javax.swing.*;
import java.awt.*;

public class StatsPanel extends JPanel {

    private static final int TOTAL_STAT_UNIT = 5;

    private final GridLayout statGrid = new GridLayout(1, TOTAL_STAT_UNIT);

    private final JPanel speedStatPanel = new JPanel(statGrid);
    private final JPanel maniabilityStatPanel = new JPanel(statGrid);
    private final JPanel resistanceStatPanel = new JPanel(statGrid);

    private CarDisplayer displayer;

    public StatsPanel(CarDisplayer displayer) {

        this.displayer = displayer;

        speedStatPanel.setBackground(Color.WHITE);
        maniabilityStatPanel.setBackground(Color.WHITE);
        resistanceStatPanel.setBackground(Color.WHITE);

        setOpaque(false);
        //GridLayout statsListGrid = new GridLayout(3, 2);
        //statsListGrid.setHgap(10);
        //statsListGrid.setVgap(10);
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(3,3,3,3); // padding
        c.anchor = GridBagConstraints.CENTER;

        statGrid.setVgap(10);
        statGrid.setHgap(10);

        updateStats();

        JLabel speedLabel       = new JLabel("SPEED");
        JLabel maniabilityLabel = new JLabel("MANIABILITY");
        JLabel resistanceLabel  = new JLabel("RESISTANCE");
        speedLabel.setFont(Utils.getDefaultFont(14));
        maniabilityLabel.setFont(Utils.getDefaultFont(14));
        resistanceLabel.setFont(Utils.getDefaultFont(14));

        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.2;
        add(speedLabel, c);
        c.gridx = 1;
        add(speedStatPanel, c);
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 0.2;
        add(maniabilityLabel, c);
        c.gridx = 1;
        c.gridy = 1;
        add(maniabilityStatPanel, c);
        c.gridx = 0;
        c.gridy = 2;
        c.weightx = 0.2;
        add(resistanceLabel, c);
        c.gridx = 1;
        c.gridy = 2;
        add(resistanceStatPanel, c);
    }

    public void updateStats() {
        if(displayer != null) {
            updateStat(speedStatPanel, displayer.getStats().getSpeed());
            updateStat(maniabilityStatPanel, displayer.getStats().getManiability());
            updateStat(resistanceStatPanel, displayer.getStats().getResistance());
        }
    }

    public void updateStats(Stats stat) {
        updateStat(speedStatPanel, stat.getSpeed());
        updateStat(maniabilityStatPanel, stat.getManiability());
        updateStat(resistanceStatPanel, stat.getResistance());
    }

    private void updateStat(JPanel statPanel, double statValue) {
        statPanel.removeAll();

        for(int i = 1; i <= TOTAL_STAT_UNIT; ++i) {
            JLabel stat = new JLabel(statValue <= i ?
                    Utils.getSizedIcon("resources/GUI/statOff_v2.png", 0.05, Image.SCALE_DEFAULT) :
                    Utils.getSizedIcon("resources/GUI/statOn_v2.png", 0.05, Image.SCALE_DEFAULT)
            );

            statPanel.add(stat);
        }

        statPanel.validate();
        statPanel.repaint();
    }

}
