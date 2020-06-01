package controller;

import cars.CarDisplayer;

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

        setOpaque(false);
        GridLayout statsListGrid = new GridLayout(3, 2);
        statsListGrid.setHgap(10);
        statsListGrid.setVgap(10);
        setLayout(statsListGrid);

        statGrid.setVgap(10);
        statGrid.setHgap(10);

        updateStats();

        add(new JLabel("SPEED"));
        add(speedStatPanel);
        add(new JLabel("MANIABILITY"));
        add(maniabilityStatPanel);
        add(new JLabel("RESISTANCE"));
        add(resistanceStatPanel);
    }

    public void updateStats() {
        updateStat(speedStatPanel, displayer.getStats().getSpeed());
        updateStat(maniabilityStatPanel, displayer.getStats().getManiability());
        updateStat(resistanceStatPanel, displayer.getStats().getResistance());
    }

    private void updateStat(JPanel statPanel, double statValue) {
        statPanel.removeAll();

        for(int i = 1; i <= TOTAL_STAT_UNIT; ++i) {
            JPanel stat = new JPanel();
            stat.setBackground(statValue <= i ? Color.GRAY : Color.GREEN);
            statPanel.add(stat);
        }

        statPanel.validate();
        statPanel.repaint();
    }

}
