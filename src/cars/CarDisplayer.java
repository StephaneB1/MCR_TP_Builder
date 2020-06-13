package cars;

import cars.parts.CarPart;

import javax.swing.*;
import java.awt.*;

public class CarDisplayer extends JPanel {

    private static final int WIDTH  = 450;
    private static final int HEIGHT = 200;

    private Car car;
    private double ratio; // image ratio [0.0, 1.0]

    public CarDisplayer(Car car) {
        this(car, 1.0);
    }

    public CarDisplayer(Car car, double ratio) {
        this.car = car;
        this.ratio = ratio;
        setOpaque(false);
        setSize(WIDTH, HEIGHT);
    }

    public Stats getStats() {
        return car.getStats();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for(CarPart carPart : car.getCarParts()) {
            if(carPart != null) {
                carPart.drawPart(g, ratio, this);
            }
        }

    }
}
