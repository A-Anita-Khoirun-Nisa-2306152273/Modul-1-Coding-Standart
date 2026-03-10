package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PaymentServiceImplTest {

    private PaymentService paymentService;
    private Order order;

    @BeforeEach
    void setUp() {
        PaymentRepository paymentRepository = new PaymentRepository();
        paymentService = new PaymentServiceImpl(paymentRepository);

        Product product = new Product();
        product.setProductId("p1");
        product.setProductName("Sabun");
        product.setProductQuantity(1);

        order = new Order("1", List.of(product), 1708560000L, "Safira");
    }

    @Test
    void testAddPaymentWithValidVoucher() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        Payment payment = paymentService.addPayment(order, "VOUCHER_CODE", paymentData);

        assertEquals("SUCCESS", payment.getStatus());
        assertEquals(OrderStatus.SUCCESS.getValue(), order.getStatus());
    }

    @Test
    void testAddPaymentWithInvalidVoucher() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "SALAH");

        Payment payment = paymentService.addPayment(order, "VOUCHER_CODE", paymentData);

        assertEquals("REJECTED", payment.getStatus());
        assertEquals(OrderStatus.FAILED.getValue(), order.getStatus());
    }

    @Test
    void testSetStatusSuccess() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "SALAH");

        Payment payment = paymentService.addPayment(order, "VOUCHER_CODE", paymentData);
        paymentService.setStatus(payment, "SUCCESS");

        assertEquals("SUCCESS", payment.getStatus());
        assertEquals(OrderStatus.SUCCESS.getValue(), order.getStatus());
    }

    @Test
    void testSetStatusRejected() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        Payment payment = paymentService.addPayment(order, "VOUCHER_CODE", paymentData);
        paymentService.setStatus(payment, "REJECTED");

        assertEquals("REJECTED", payment.getStatus());
        assertEquals(OrderStatus.FAILED.getValue(), order.getStatus());
    }

    @Test
    void testGetPayment() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        Payment payment = paymentService.addPayment(order, "VOUCHER_CODE", paymentData);
        Payment found = paymentService.getPayment(payment.getId());

        assertNotNull(found);
        assertEquals(payment.getId(), found.getId());
    }

    @Test
    void testGetAllPayments() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        paymentService.addPayment(order, "VOUCHER_CODE", paymentData);

        assertEquals(1, paymentService.getAllPayments().size());
    }

    @Test
    void testAddPaymentCashOnDeliveryValid() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("address", "Jl. Mawar No. 1");
        paymentData.put("deliveryFee", "10000");

        Payment payment = paymentService.addPayment(order, "CASH_ON_DELIVERY", paymentData);

        assertEquals("SUCCESS", payment.getStatus());
    }

    @Test
    void testAddPaymentCashOnDeliveryInvalid() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("address", "");
        paymentData.put("deliveryFee", "10000");

        Payment payment = paymentService.addPayment(order, "CASH_ON_DELIVERY", paymentData);

        assertEquals("REJECTED", payment.getStatus());
    }
}