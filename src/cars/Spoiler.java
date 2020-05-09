package cars;

import java.awt.*;

public class Spoiler extends CarPart {

    public Spoiler(String name, String imagePath, double acceleration, double weight, double adherence, double maniability, double resistance, Color color) {
        super(name, imagePath, acceleration, weight, adherence, maniability, resistance, color);
    }

    @Override
    public String getCategory() {
        return "Spoiler";
    }

}
