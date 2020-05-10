package cars;

public class Tires extends CarPart {

    public Tires(String name, String imagePath, double acceleration, double weight, double adherence, double maniability, double resistance) {
        super(name, imagePath, acceleration, weight, adherence, maniability, resistance);
    }

    @Override
    public String getCategory() {
        return "Tires";
    }

}
