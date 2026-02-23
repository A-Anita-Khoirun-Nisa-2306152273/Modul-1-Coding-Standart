package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl service;

    @Test
    void create_shouldGenerateId_andCallRepositoryCreate() {
        Product input = new Product();
        // repository akan mengembalikan object yang diberikan
        when(productRepository.create(any(Product.class))).thenAnswer(inv -> inv.getArgument(0));

        Product result = service.create(input);

        assertNotNull(result.getProductId(), "productId harus dibuat (UUID)");
        // Validasi format UUID (optional tapi bagus untuk memastikan benar-benar UUID)
        assertDoesNotThrow(() -> UUID.fromString(result.getProductId()));

        verify(productRepository, times(1)).create(any(Product.class));
    }

    @Test
    void editProduct_shouldSetId_andCallRepositoryEdit() {
        Product p = new Product();

        service.editProduct("abc", p);

        ArgumentCaptor<Product> captor = ArgumentCaptor.forClass(Product.class);
        verify(productRepository, times(1)).edit(captor.capture());

        Product edited = captor.getValue();
        assertEquals("abc", edited.getProductId(), "productId harus diset sesuai parameter");
    }

    @Test
    void deleteProduct_shouldCallRepositoryDelete() {
        service.deleteProduct("1");
        verify(productRepository, times(1)).delete("1");
    }

    @Test
    void findById_shouldReturnRepositoryResult() {
        Product expected = new Product();
        expected.setProductId("x");
        when(productRepository.findById("x")).thenReturn(expected);

        Product actual = service.findById("x");

        assertSame(expected, actual);
        verify(productRepository, times(1)).findById("x");
    }

    @Test
    void findAll_shouldCollectIteratorIntoList() {
        Product p1 = new Product();
        p1.setProductId("1");
        Product p2 = new Product();
        p2.setProductId("2");

        Iterator<Product> iterator = List.of(p1, p2).iterator();
        when(productRepository.findAll()).thenReturn(iterator);

        List<Product> result = service.findAll();

        assertEquals(2, result.size());
        assertEquals("1", result.get(0).getProductId());
        assertEquals("2", result.get(1).getProductId());
        verify(productRepository, times(1)).findAll();
    }
}