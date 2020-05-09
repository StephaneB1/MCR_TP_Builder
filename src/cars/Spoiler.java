package cars;

public class Spoiler extends CarPart {

    public Spoiler(String name, String imagePath, double acceleration, double weight, double adherence, double maniability, double resistance) {
        super(name, imagePath, acceleration, weight, adherence, maniability, resistance);
    }

    @Override
    public String getCategory() {
        return "Spoiler";
    }

}
