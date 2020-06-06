package utils;

import javax.swing.*;
import java.awt.*;
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


    public static JButton getIconJButton(String iconPath) {
        return getIconJButton(iconPath, 1.0);
    }

    public static JButton getIconJButton(String iconPath, double ratio) {


        JButton result = new JButton("", getSizedIcon(iconPath, ratio, Image.SCALE_SMOOTH));
        result.setBorderPainted(false);
        result.setContentAreaFilled(false);
        result.setFocusPainted(false);
        result.setOpaque(false);
        return result;
    }

    public static ImageIcon getSizedIcon(String path, double ratio, int scaleType) {
        ImageIcon imageIcon = new ImageIcon(path); // load the image to a imageIcon
        Image image = imageIcon.getImage(); // transform it
        Image newimg = image.getScaledInstance(
                (int) (image.getWidth(null) * ratio),
                (int) (image.getHeight(null) * ratio),
                scaleType); // scale it the smooth way
        imageIcon = new ImageIcon(newimg);  // transform it back

        return imageIcon;
    }


}
