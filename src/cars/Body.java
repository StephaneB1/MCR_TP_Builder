package cars;

public class Body extends CarPart {

    public Body(String name, String imagePath, double acceleration, double weight, double adherence, double maniability, double resistance) {
        super(name, imagePath, acceleration, weight, adherence, maniability, resistance);
    }

    @Override
    public String getCategory() {
        return "Body";
    }

}
