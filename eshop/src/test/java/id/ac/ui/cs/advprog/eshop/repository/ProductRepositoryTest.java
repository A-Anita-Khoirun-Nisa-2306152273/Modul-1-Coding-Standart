package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest {
    private static final String UUID_PRODUCT_1 = "eb558e9f-1c39-460e-8860-71af6af63bd6";
    private static final String PRODUCT_NAME_SAMPO_BAMBANG = "Sampo Cap Bambang";

    private static final String FIXED_ID = "ID-Tetap";
    private static final String PRODUCT_NAME_KECAP_BANG_AW = "Kecap Bang Aw";

    private static final String PRODUCT_NAME_SAMPO_BIASA = "Sampo Biasa Aja";
    private static final String UUID_PRODUCT_EDIT = "eb668e9f-1c39-460e-8860-71af6af63bd6";

    @InjectMocks
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testCreateAndFind() {
        Product product = new Product();
        product.setProductId(UUID_PRODUCT_1);
        product.setProductName(PRODUCT_NAME_SAMPO_BAMBANG);
        product.setProductQuantity(100);
        productRepository.create(product);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product.getProductId(), savedProduct.getProductId());
        assertEquals(product.getProductName(), savedProduct.getProductName());
        assertEquals(product.getProductQuantity(), savedProduct.getProductQuantity());
    }

    @Test
    void testFindAllIfEmpty() {
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindAllIfMoreThanOneProduct() {
        Product product1 = new Product();
        product1.setProductId(UUID_PRODUCT_1);
        product1.setProductName(PRODUCT_NAME_SAMPO_BAMBANG);
        product1.setProductQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("a0f9de46-90b1-437d-a0bf-d0821dde9096");
        product2.setProductName("Sampo Cap Usep");
        product2.setProductQuantity(50);
        productRepository.create(product2);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product1.getProductId(), savedProduct.getProductId());
        savedProduct = productIterator.next();
        assertEquals(product2.getProductId(), savedProduct.getProductId());
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testEditProduct() {
        Product product = new Product();
        product.setProductId(UUID_PRODUCT_EDIT);
        product.setProductName(PRODUCT_NAME_SAMPO_BAMBANG);
        product.setProductQuantity(100);
        productRepository.create(product);

        Product updatedProduct = new Product();
        updatedProduct.setProductId(UUID_PRODUCT_EDIT);
        updatedProduct.setProductName(PRODUCT_NAME_SAMPO_BIASA);
        updatedProduct.setProductQuantity(400);

        Product result = productRepository.edit(updatedProduct);

        assertNotNull(result);
        assertEquals(PRODUCT_NAME_SAMPO_BIASA, result.getProductName());
        assertEquals(400, result.getProductQuantity());

        Iterator<Product> iterator = productRepository.findAll();
        assertTrue(iterator.hasNext());
        Product savedProduct = iterator.next();
        assertEquals(PRODUCT_NAME_SAMPO_BIASA, savedProduct.getProductName());
    }

    @Test
    void testEditProductNotFound() {
        Product product = new Product();
        product.setProductId("id-ghoib");
        product.setProductName("Barang");
        product.setProductQuantity(500);

        Product result = productRepository.edit(product);
        assertNull(result);
    }

    @Test
    void testEditProductQuantityOnly() {
        Product product = new Product();
        product.setProductId(FIXED_ID);
        product.setProductName(PRODUCT_NAME_KECAP_BANG_AW);
        product.setProductQuantity(5);
        productRepository.create(product);

        Product updateRequest = new Product();
        updateRequest.setProductId(FIXED_ID);
        updateRequest.setProductName(PRODUCT_NAME_KECAP_BANG_AW);
        updateRequest.setProductQuantity(100);

        productRepository.edit(updateRequest);

        Product result = productRepository.findById(FIXED_ID);
        assertEquals(100, result.getProductQuantity());
        assertEquals(PRODUCT_NAME_KECAP_BANG_AW, result.getProductName());
        assertEquals(FIXED_ID, result.getProductId());
    }

    @Test
    void testDeleteProductVerifyListIntegrity() {
        Product product1 = new Product();
        product1.setProductId("id-1");
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("id-2");
        productRepository.create(product2);

        Product product3 = new Product();
        product3.setProductId("id-3");
        productRepository.create(product3);

        productRepository.delete("id-3");

        assertNull(productRepository.findById("id-3"));
        assertNotNull(productRepository.findById("id-1"));
        assertNotNull(productRepository.findById("id-2"));

        int remainingProduct = 0;
        Iterator<Product> iterator = productRepository.findAll();
        while (iterator.hasNext()) {
            iterator.next();
            remainingProduct++;
        }
        assertEquals(2, remainingProduct);
    }

    @Test
    void testDeleteProductPositive() {
        Product product = new Product();
        product.setProductId(UUID_PRODUCT_1);
        product.setProductName(PRODUCT_NAME_SAMPO_BAMBANG);
        product.setProductQuantity(100);
        productRepository.create(product);

        productRepository.delete(product.getProductId());

        assertNull(productRepository.findById(product.getProductId()));
    }

    @Test
    void testDeleteProductNegativeNotFound() {
        Product product = new Product();
        product.setProductId("id-valid");
        product.setProductName("Sampo Mantap");
        productRepository.create(product);

        productRepository.delete("random-id");

        Iterator<Product> iterator = productRepository.findAll();
        assertTrue(iterator.hasNext());
    }
}