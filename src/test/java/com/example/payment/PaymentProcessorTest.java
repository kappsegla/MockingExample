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
    void setUp() { //skapar mock objekt för dependencies
        paymentService = mock(PaymentService.class);
        databaseService = mock(DatabaseService.class);
        emailService = mock(EmailService.class);
        //skapar instanser av paymentprocessor med en api och mock dependecies
        paymentProcessor = new PaymentProcessor("sk_test_123456", paymentService, databaseService, emailService);

    }

    @Test
    void successfulPaymentProcess() { //ser till att lyckad betalning triggar databas och email

        //mockar en lyckad payment response
        PaymentApiResponse successResponse = mock(PaymentApiResponse.class);
        when(successResponse.isSuccess()).thenReturn(true);
        when(paymentService.charge(anyString(), anyDouble())).thenReturn(successResponse);
        //gör en payment på 100
        boolean result = paymentProcessor.processPayment(100.0);

        assertTrue(result);//assertion att payment ska lyckats
        //verifierar att payment sparas i databasen med success meddelandet
        verify(databaseService).savePayment(100.0,"SUCCESS");
        //verifierar att konfirmationsmail skickas
        verify(emailService).sendPaymentConfirmation("user@example.com",100.0);
    }

    @Test
    void failedPaymentProcess() { //om payment misslyckats, så skickas det ingen uppdatering till databas och emails.
        //skapar en mockad misslyckad payment respons
        PaymentApiResponse failedResponse = mock(PaymentApiResponse.class);
        when(failedResponse.isSuccess()).thenReturn(false);
        when(paymentService.charge(anyString(), anyDouble())).thenReturn(failedResponse);
        //skapar en payment på 100 som ska misslyckats
        boolean result = paymentProcessor.processPayment(100.0);
        assertFalse(result); //ser result är falskt, ska misslycka payment
        //ser till att ingen database blir ändrad med detta
         verify(databaseService, never()).savePayment(anyDouble(),anyString());
        verify(emailService, never()).sendPaymentConfirmation(anyString(),anyDouble());//ser till att ingen email ska skickas
    }

}