package cars;

public class Tires extends CarPart {

    private static String TIRES_PATH = "resources/cars/tires/";

    public Tires(String name, String image, double acceleration, double weight, double adherence, double maniability, double resistance) {
        super(name, TIRES_PATH + image, acceleration, weight, adherence, maniability, resistance);
    }

    @Override
    public String getCategory() {
        return "Tires";
    }

}
