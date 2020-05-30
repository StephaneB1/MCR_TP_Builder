package cars;

import java.awt.*;

public class PaintJob {

    String name;
    Color color;

    public PaintJob(String name, Color color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }
}
