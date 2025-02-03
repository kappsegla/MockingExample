package com.example.payment;

public class PaymentProcessor {
    private static final String API_KEY = "sk_test_123456"; //borde inte finnas i koden, borde vara gömd. exempelvis i environment variables
    private final PaymentService paymentService;
    private final DatabaseService databaseService;
    private final EmailService emailService;
/*
Gjorde en konstruktor för att lättare skapa mock-objekt för att göra koden mer testbar.
en konstruktor ger mig dependency injection, för att jag kan ersätta faktiska beroenden med låtsas/mock objekt.
 */
    public PaymentProcessor(String apiKey, PaymentService paymentService, DatabaseService databaseService, EmailService emailService) {
        this.paymentService = paymentService;
        this.databaseService = databaseService;
        this.emailService = emailService;
    }

    public boolean processPayment(double amount) {
        // Anropar extern betaltjänst direkt med statisk API-nyckel

        PaymentApiResponse response = paymentService.charge(API_KEY, amount);

        //lade ihop dessa för att det inte skulle repeteras när båda ändå används i samma veva
        if (response.isSuccess()) {
            databaseService.savePayment(amount, "SUCCESS");
            emailService.sendPaymentConfirmation("user@example.com", amount);



        }return response.isSuccess();
    }
}