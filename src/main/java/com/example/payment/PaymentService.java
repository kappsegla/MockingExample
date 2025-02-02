package com.example.payment;

public interface PaymentService {
    PaymentApiResponse charge (String apikey,double amount);
}
