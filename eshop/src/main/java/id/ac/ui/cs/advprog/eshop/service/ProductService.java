package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import java.util.List;

public interface ProductService {
    public Product create(Product product);
    public void editProduct(String productId, Product product);
    public Product findById(String Id);
    public List<Product> findAll();
    public void deleteProduct(String Id);
}