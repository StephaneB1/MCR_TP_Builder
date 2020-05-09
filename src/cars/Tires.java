package cars;

import java.awt.*;

public class Tires extends CarPart {

    public Tires(String name, String imagePath, double acceleration, double weight, double adherence, double maniability, double resistance, Color color) {
        super(name, imagePath, acceleration, weight, adherence, maniability, resistance, color);
    }

    @Override
    public String getCategory() {
        return "Tires";
    }

}
