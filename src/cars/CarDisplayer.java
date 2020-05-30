package cars;

import carBuilder.CarBuilder;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class CarDisplayer extends JPanel {

    private Car car;
    private CarBuilder builder; // blueprint

    public CarDisplayer(Car car, CarBuilder builder) {
        this.car = car;
        this.builder = builder;

        this.setSize(450, 200);
    }

    public CarDisplayer(Car car){
        this(car, null);
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Stats getStats() {
        return (car != null ? car.getStats() : builder.getTempStats());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // display the car parts of the car
        if(car != null) {
            for(CarPart carPart : car.getCarParts()) {
                ((Graphics2D) g).setBackground(Color.BLUE);
                g.drawImage(carPart.getImage(), carPart.getXCoord(), carPart.getYCoord(), this);
            }
        }
        // Or the blueprint of the builder
        else if (builder != null) {
            for(CarPart carPart : builder.getCarParts()) {
                if(carPart != null) {
                    AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
                    ((Graphics2D) g).setComposite(ac);
                    g.drawImage(carPart.getImage(), carPart.getXCoord(), carPart.getYCoord(),this);
                }
            }
        }
    }


}
