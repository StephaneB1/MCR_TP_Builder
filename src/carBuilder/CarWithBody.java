package carBuilder;

import cars.*;

public interface CarWithBody {

    CarWithBody buildMotor(Motor motor);

    CarWithBody buildTire(Tires tires);

    CarWithBody buildSpoiler(Spoiler spoiler);

    Car getCar();
}
