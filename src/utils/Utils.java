package utils;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.function.Function;

public class Utils {

    public static Font DEFAULT_FONT = getDefaultFont(20);

    public static Font getDefaultFont(int size) {
        return new Font("Arial", Font.PLAIN, size);
    }

    public static Color getDefaultColor() {
        return new Color(50,50,50);
    }

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


    public static Border getPanelBorder(String title, Color color) {
        Border comp;
        Border lineBorder = new LineBorder(color);

        TitledBorder titledBorder = new TitledBorder(lineBorder, title, TitledBorder.TOP,
                TitledBorder.DEFAULT_POSITION, getDefaultFont(15), color);
        Border margin = BorderFactory.createEmptyBorder(10,10,10,10);
        comp = BorderFactory.createCompoundBorder(titledBorder, margin);
        return comp;
    }



    public static void popup(String iconPath, String title, String message) {
        final Icon imgIcon = new ImageIcon(iconPath);
        UIManager.put("OptionPane.messageFont", new Font("Arial", Font.BOLD, 16));
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE, imgIcon);
    }

    public static void popupWarning(String message) {
        popup("resources/warning-popup.png",
                "Warning", message);
    }



}
