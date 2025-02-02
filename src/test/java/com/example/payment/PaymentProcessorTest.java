package com.example.payment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class PaymentProcessorTest {
    private PaymentService paymentService;
    private DatabaseService databaseService;
    private EmailService emailService;
    private PaymentProcessor paymentProcessor;

    @BeforeEach
    void setUp() {
        paymentService = mock(PaymentService.class);
        databaseService = mock(DatabaseService.class);
        emailService = mock(EmailService.class);

        paymentProcessor = new PaymentProcessor("sk_test_123456", paymentService, databaseService, emailService);

    }

    @Test
    void successfulPaymentProcess() {
        PaymentApiResponse successResponse = mock(PaymentApiResponse.class);
        when(successResponse.isSuccess()).thenReturn(true);
        when(paymentService.charge(anyString(), anyDouble())).thenReturn(successResponse);

        boolean result = paymentProcessor.processPayment(100.0);
        assertTrue(result);
        verify(databaseService).savePayment(100.0,"SUCCESS");
        verify(emailService).sendPaymentConfirmation("user@example.com",100.0);
    }

    @Test
    @DisplayName("")
    void failedPaymentProcess() {
        PaymentApiResponse failedResponse = mock(PaymentApiResponse.class);
        when(failedResponse.isSuccess()).thenReturn(false);
        when(paymentService.charge(anyString(), anyDouble())).thenReturn(failedResponse);
        boolean result = paymentProcessor.processPayment(100.0);
        assertFalse(result);
        verify(databaseService, never()).savePayment(anyDouble(),anyString());
        verify(emailService, never()).sendPaymentConfirmation(anyString(),anyDouble());
    }

    @Test
    void processPayment() {
    }
}