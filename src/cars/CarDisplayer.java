package cars;

import carBuilder.CarBuilder;

import javax.swing.*;
import java.awt.*;

public class CarDisplayer extends JPanel {

    private static final int WIDTH  = 450;
    private static final int HEIGHT = 200;


    private Car car;
    private CarBuilder builder; // blueprint
    private double ratio; // image ratio [0.0, 1.0]

    public CarDisplayer(Car car, CarBuilder builder) {
        this(car, builder, 1);
    }

    public CarDisplayer(Car car, CarBuilder builder, double ratio) {
        this.car     = car;
        this.builder = builder;
        this.ratio   = ratio;

        this.setSize(WIDTH, HEIGHT);
        setBackground(Color.WHITE);
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
                if(ratio != 1) {
                    Image sizedImage = carPart.getImage().getScaledInstance(
                            (int) (carPart.getImage().getWidth() * ratio),
                            (int) (carPart.getImage().getHeight() * ratio),
                            Image.SCALE_DEFAULT);
                    g.drawImage(sizedImage, (int) (carPart.getXCoord() * ratio), (int) (carPart.getYCoord() * ratio), this);
                    if (carPart.isDuplicateOnX){
                        g.drawImage(sizedImage, (int) ((carPart.getXCoord() + carPart.getDuplicateDistanceX())*ratio),  (int) (carPart.getYCoord() * ratio) ,this);
                    }
                } else {
                    g.drawImage(carPart.getImage(), carPart.getXCoord(), carPart.getYCoord(), this);
                    if (carPart.isDuplicateOnX){
                        g.drawImage(carPart.getImage(), carPart.getXCoord() + carPart.getDuplicateDistanceX(), carPart.getYCoord() ,this);
                    }
                }
            }
        }
        // Or the blueprint of the builder
        else if (builder != null) {
            for(CarPart carPart : builder.getCarParts()) {
                if(carPart != null) {
                    g.drawImage(carPart.getImage(), carPart.getXCoord(), carPart.getYCoord(),this);
                    if (carPart.isDuplicateOnX){
                        g.drawImage(carPart.getImage(), carPart.getXCoord() + carPart.getDuplicateDistanceX(), carPart.getYCoord() ,this);
                    }
                }
            }
        }
    }

}
