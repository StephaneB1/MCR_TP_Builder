package utils;

public class Utils {
    /**
     * Calculates the average value of the given numbers.
     * @param values decimal numbers to be averaged
     * @return the average value
     */
    public static double average(double ... values) {
        return sum(values) / values.length;
    }

    /**
     * Calculates the sum value of the given numbers.
     * @param values decimal numbers to be summed
     * @return the sum
     */
    public static double sum(double ... values) {
        double sum = 0;

        for(double value : values)
            sum += value;

        return sum;
    }
}
