package utils;

import java.util.ArrayList;
import java.util.function.Function;

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

    /**
     * Calculates the average value of a list of objects, applying a given function on each one of them
     * @param list ArrayList of the objects to be averaged
     * @param funcInter function to apply on every item to calculate average
     * @param <T> type of object in the list
     * @return average value, type double
     */
    public static <T> double averageFunc(ArrayList<T> list, Function<T, Double> funcInter) {
        double average = 0;

        for(T s : list)
            if(s != null)
                average += funcInter.apply(s);

        return average / list.size();
    }
}
