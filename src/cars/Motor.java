package cars;

import java.awt.*;

public class Motor extends CarPart {

    public Motor(String name, String imagePath, double acceleration, double weight, double adherence, double maniability, double resistance, Color color) {
        super(name, imagePath, acceleration, weight, adherence, maniability, resistance, color);
    }

    @Override
    public String getCategory() {
        return "Motor";
    }

}
