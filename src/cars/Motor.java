package cars;

public class Motor extends CarPart {

    private static String MOTOR_PATH = "resources/cars/motors/";

    public Motor(String name, String image, double acceleration, double weight, double adherence, double maniability, double resistance) {
        super(name, MOTOR_PATH + image, acceleration, weight, adherence, maniability, resistance);
    }

    @Override
    public String getCategory() {
        return "Motor";
    }

}
