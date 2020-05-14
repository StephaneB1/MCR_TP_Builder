package carBuilder;

import cars.Body;

import java.awt.*;

public interface EmtpyCar{

    CarWithBody buildBody(Body body);

    EmtpyCar setName(String name);

    EmtpyCar setColor(Color color);
}
