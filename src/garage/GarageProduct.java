package garage;

import cars.parts.CarPart;

import java.util.ArrayList;

public class GarageProduct {

    private String productLabel;
    private ArrayList<CarPart> products;

    public GarageProduct(String productName) {
        this.productLabel = productName;
        products = new ArrayList<>();
    }

    public void addProduct(CarPart product) {
        products.add(product);
    }

    public ArrayList<CarPart> getProducts() {
        return products;
    }

    public String getProductLabel() {
        return productLabel;
    }

}
