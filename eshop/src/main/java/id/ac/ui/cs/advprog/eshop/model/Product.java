package id.ac.ui.cs.advprog.eshop.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Product {
    private String productId;
    private String productName;
    private int productQuantity;

    public Product update(Product newProduct) {
        this.productName = newProduct.getProductName();
        this.productQuantity = newProduct.getProductQuantity();
        return this;
    }
}
