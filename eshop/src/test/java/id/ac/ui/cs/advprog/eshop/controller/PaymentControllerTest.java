package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.PaymentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PaymentController.class)
class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaymentService paymentService;

    private Payment createPayment() {
        Product product = new Product();
        product.setProductId("p1");
        product.setProductName("Sabun");
        product.setProductQuantity(1);

        Order order = new Order("order-1", List.of(product), 1708560000L, "Safira");
        return new Payment(order, "VOUCHER_CODE", "SUCCESS", Map.of("voucherCode", "ESHOP1234ABC5678"));
    }

    @Test
    void testPaymentDetailFormPage() throws Exception {
        mockMvc.perform(get("/payment/detail"))
                .andExpect(status().isOk())
                .andExpect(view().name("paymentDetailForm"));
    }

    @Test
    void testPaymentDetailPage() throws Exception {
        when(paymentService.getPayment("payment-1")).thenReturn(createPayment());

        mockMvc.perform(get("/payment/detail/payment-1"))
                .andExpect(status().isOk())
                .andExpect(view().name("paymentDetail"))
                .andExpect(model().attributeExists("payment"));
    }

    @Test
    void testPaymentAdminListPage() throws Exception {
        when(paymentService.getAllPayments()).thenReturn(List.of(createPayment()));

        mockMvc.perform(get("/payment/admin/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("paymentAdminList"))
                .andExpect(model().attributeExists("payments"));
    }

    @Test
    void testPaymentAdminDetailPage() throws Exception {
        when(paymentService.getPayment("payment-1")).thenReturn(createPayment());

        mockMvc.perform(get("/payment/admin/detail/payment-1"))
                .andExpect(status().isOk())
                .andExpect(view().name("paymentAdminDetail"))
                .andExpect(model().attributeExists("payment"));
    }

    @Test
    void testPaymentSetStatus() throws Exception {
        Payment payment = createPayment();
        when(paymentService.getPayment("payment-1")).thenReturn(payment);
        when(paymentService.setStatus(payment, "REJECTED")).thenReturn(payment);

        mockMvc.perform(post("/payment/admin/set-status/payment-1")
                        .param("status", "REJECTED"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/payment/admin/detail/payment-1"));

        verify(paymentService, times(1)).setStatus(payment, "REJECTED");
    }
}