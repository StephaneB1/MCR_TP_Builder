package cars;

public class Body extends CarPart {

    private static String BODY_PATH = "resources/cars/bodies/";

    public Body(String name, String image, double acceleration, double weight, double adherence, double maniability, double resistance) {
        super(name, BODY_PATH + image, acceleration, weight, adherence, maniability, resistance);
    }

    @Override
    public String getCategory() {
        return "Body";
    }

}
