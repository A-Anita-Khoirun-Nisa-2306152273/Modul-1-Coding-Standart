package id.ac.ui.cs.advprog.eshop.model;

import java.util.Map;
import java.util.UUID;

public class Payment {
    private String id;
    private String method;
    private String status;
    private Map<String, String> paymentData;
    private Order order;

    public Payment(Order order, String method, String status, Map<String, String> paymentData) {
        this.id = UUID.randomUUID().toString();
        this.order = order;
        this.method = method;
        this.status = status;
        this.paymentData = paymentData;
    }

    public String getId() {
        return id;
    }

    public String getMethod() {
        return method;
    }

    public String getStatus() {
        return status;
    }

    public Map<String, String> getPaymentData() {
        return paymentData;
    }

    public Order getOrder() {
        return order;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}