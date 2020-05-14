package cars;

public interface Stats {

    /**
     * Acceleration factor (average) [0;1]
     * @return acceleration factor
     */
    public double getAcceleration();

    /**
     * Numeric value of the weight, expressed in kilograms (sum)
     * @return total weight
     */
    public double getWeight();

    /**
     * Adherence factor (average) [0;1]
     * @return adherence factor
     */
    public double getAdherence();

    /**
     * Maniability factor (average) [0;1]
     * @return maniability factor
     */
    public double getManiability();

    /**
     * Resistance factor (average) [0;1]
     * @return resistance factor
     */
    public double getResistance();

}
