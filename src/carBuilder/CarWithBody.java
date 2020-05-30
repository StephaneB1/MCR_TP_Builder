package carBuilder;

import cars.*;

import java.awt.*;

public interface CarWithBody {

    CarWithBody setName(String name);

    CarWithBody setColor(Color color);

    CarWithBody buildMotor(Motor motor);

    CarWithBody buildTire(Tires tires);

    CarWithBody buildSpoiler(Spoiler spoiler);

    Car getCar();
}
