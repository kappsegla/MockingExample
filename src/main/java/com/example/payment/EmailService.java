package com.example.payment;

public interface EmailService {
    void sendPaymentConfirmation(String email, double amount);
}
