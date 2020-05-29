package cars;

import carBuilder.CarBuilder;

import javax.swing.*;
import java.awt.*;

public class CarDisplayer extends JPanel {

    private Car car;
    private CarBuilder builder;

    public CarDisplayer(Car car, CarBuilder builder) {
        this.car = car;
        this.builder = builder;

        this.setSize(450, 200);
    }

    public void setCar(Car car) {
        this.car = car;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        System.out.println("Repainting...");

        // display the car parts of the car
        if(car != null) {
            System.out.println("Car ok");
            for(CarPart carPart : car.getCarParts()) {
                g.drawImage(carPart.getImage(), carPart.getXCoord(), carPart.getYCoord(), this);
            }
        }
        // Or the blueprint of the builder
        else if (builder != null) {
            System.out.println("Blueprint painting : ok");
            for(CarPart carPart : builder.getCarParts()) {
                if(carPart != null) {
                    System.out.println("Painting : " + carPart.getName());
                    AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
                    ((Graphics2D) g).setComposite(ac);
                    g.drawImage(carPart.getImage(), carPart.getXCoord(), carPart.getYCoord(), this);
                }
            }
        }
    }

}
