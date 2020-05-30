package garage;

import cars.*;

import java.util.ArrayList;

public class Garage {

    // Inventory indexes
    public static final int CATEGORY_BODY = 0;
    public static final int CATEGORY_MOTORS = 1;
    public static final int CATEGORY_TIRES = 2;
    public static final int CATEGORY_SPOILERS = 3;
    public static final int CATEGORY_COLOR = 4;

    private ArrayList<GarageProduct> inventory;
    private ArrayList<PaintJob> paintJobs;

    public Garage() {
        inventory = new ArrayList<>();
        paintJobs = new ArrayList<>();
    }

    public void addToInventory(GarageProduct product) {
        inventory.add(product);
    }

    public ArrayList<GarageProduct> getInventory() {
        return inventory;
    }

    public void addPaintJob(PaintJob pj) {
        this.paintJobs.add(pj);
    }

    public ArrayList<PaintJob> getPaintJobs() {
        return paintJobs;
    }
}
