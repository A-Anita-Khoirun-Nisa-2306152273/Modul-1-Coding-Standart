package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public Payment addPayment(Order order, String method, Map<String, String> paymentData) {
        String status = determineInitialStatus(method, paymentData);
        Payment payment = new Payment(order, method, status, paymentData);
        paymentRepository.save(payment);

        if ("SUCCESS".equals(status)) {
            order.setStatus(OrderStatus.SUCCESS.getValue());
        } else if ("REJECTED".equals(status)) {
            order.setStatus(OrderStatus.FAILED.getValue());
        }

        return payment;
    }

    @Override
    public Payment setStatus(Payment payment, String status) {
        payment.setStatus(status);

        if ("SUCCESS".equals(status)) {
            payment.getOrder().setStatus(OrderStatus.SUCCESS.getValue());
        } else if ("REJECTED".equals(status)) {
            payment.getOrder().setStatus(OrderStatus.FAILED.getValue());
        }

        return paymentRepository.save(payment);
    }

    @Override
    public Payment getPayment(String paymentId) {
        return paymentRepository.findById(paymentId);
    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    private String determineInitialStatus(String method, Map<String, String> paymentData) {
        if ("VOUCHER_CODE".equals(method)) {
            return isValidVoucher(paymentData.get("voucherCode")) ? "SUCCESS" : "REJECTED";
        }

        if ("CASH_ON_DELIVERY".equals(method)) {
            String address = paymentData.get("address");
            String deliveryFee = paymentData.get("deliveryFee");
            return isFilled(address) && isFilled(deliveryFee) ? "SUCCESS" : "REJECTED";
        }

        throw new IllegalArgumentException("Unknown payment method");
    }

    private boolean isFilled(String value) {
        return value != null && !value.isEmpty();
    }

    private boolean isValidVoucher(String voucherCode) {
        if (voucherCode == null || voucherCode.length() != 16) {
            return false;
        }

        if (!voucherCode.startsWith("ESHOP")) {
            return false;
        }

        int digitCount = 0;
        for (char c : voucherCode.toCharArray()) {
            if (Character.isDigit(c)) {
                digitCount++;
            }
        }

        return digitCount == 8;
    }
}