package garage;

import cars.parts.CarPart;

import java.util.ArrayList;

/**
 * MCR PROJECT : Builder Design Pattern
 * Author      : Bottin Stéphane, Demarta Robin, Dessaules Loïc, Kot Chau-Ying
 *
 * Description : Garage product category
 */
public class GarageProduct {

    private String productLabel;
    private ArrayList<CarPart> products;

    public GarageProduct(String productName) {
        this.productLabel = productName;
        products = new ArrayList<>();
    }

    /**
     * Add a product to the garage product
     * @param product : product to add
     */
    public void addProduct(CarPart product) {
        products.add(product);
    }

    /**
     * Get the products
     * @return products
     */
    public ArrayList<CarPart> getProducts() {
        return products;
    }

    /**
     * Get the label
     * @return label
     */
    public String getProductLabel() {
        return productLabel;
    }

}
