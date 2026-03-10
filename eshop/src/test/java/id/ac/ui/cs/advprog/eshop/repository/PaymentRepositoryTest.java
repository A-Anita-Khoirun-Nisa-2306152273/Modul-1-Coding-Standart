package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PaymentRepositoryTest {

    private PaymentRepository paymentRepository;
    private Payment payment;

    @BeforeEach
    void setUp() {
        paymentRepository = new PaymentRepository();

        Product product = new Product();
        product.setProductId("p1");
        product.setProductName("Sabun");
        product.setProductQuantity(1);

        Order order = new Order("1", List.of(product), 1708560000L, "Safira");

        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        payment = new Payment(order, "VOUCHER_CODE", "SUCCESS", paymentData);
    }

    @Test
    void testSaveAndFindById() {
        paymentRepository.save(payment);

        Payment found = paymentRepository.findById(payment.getId());

        assertNotNull(found);
        assertEquals(payment.getId(), found.getId());
        assertEquals("VOUCHER_CODE", found.getMethod());
    }

    @Test
    void testFindByIdNotFound() {
        Payment found = paymentRepository.findById("tidak-ada");
        assertNull(found);
    }

    @Test
    void testFindAll() {
        paymentRepository.save(payment);

        assertEquals(1, paymentRepository.findAll().size());
    }

    @Test
    void testSaveUpdateStatus() {
        paymentRepository.save(payment);
        payment.setStatus("REJECTED");
        paymentRepository.save(payment);

        Payment found = paymentRepository.findById(payment.getId());
        assertEquals("REJECTED", found.getStatus());
    }
}