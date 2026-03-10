package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {

    @Test
    void testCreatePayment() {
        Order order = new Order("1", java.util.List.of(createProduct()), 1708560000L, "Safira");
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        Payment payment = new Payment(order, "VOUCHER_CODE", "SUCCESS", paymentData);

        assertNotNull(payment.getId());
        assertEquals("VOUCHER_CODE", payment.getMethod());
        assertEquals("SUCCESS", payment.getStatus());
        assertEquals(order, payment.getOrder());
        assertEquals("ESHOP1234ABC5678", payment.getPaymentData().get("voucherCode"));
    }

    @Test
    void testSetStatus() {
        Order order = new Order("1", java.util.List.of(createProduct()), 1708560000L, "Safira");
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        Payment payment = new Payment(order, "VOUCHER_CODE", "SUCCESS", paymentData);
        payment.setStatus("REJECTED");

        assertEquals("REJECTED", payment.getStatus());
    }

    private Product createProduct() {
        Product product = new Product();
        product.setProductId("p1");
        product.setProductName("Sabun");
        product.setProductQuantity(1);
        return product;
    }
}