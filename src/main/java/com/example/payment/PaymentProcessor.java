package com.example.payment;

public class PaymentProcessor {
    private static final String API_KEY = "sk_test_123456";
    private final PaymentService paymentService;
    private final DatabaseService databaseService;
    private final EmailService emailService;

    public PaymentProcessor(String apiKey, PaymentService paymentService, DatabaseService databaseService, EmailService emailService) {
        this.paymentService = paymentService;
        this.databaseService = databaseService;
        this.emailService = emailService;
    }

    public boolean processPayment(double amount) {
        // Anropar extern betaltjänst direkt med statisk API-nyckel

        PaymentApiResponse response = paymentService.charge(API_KEY, amount);

        //Skriver till databas direkt
        if (response.isSuccess()) {
            databaseService.savePayment(amount, "SUCCESS");
            emailService.sendPaymentConfirmation("user@example.com", amount);



        }return response.isSuccess();
    }
}

//.executeUpdate("INSERT INTO payments (amount, status) VALUES (" + amount + ", 'SUCCESS')");

//if (response.isSuccess()) {
//        DatabaseConnection.getInstance()
//                    .executeUpdate("INSERT INTO payments (amount, status) VALUES (" + amount + ", 'SUCCESS')");
//        }
//
//                // Skickar e-post direkt
//                if (response.isSuccess()) {
//        EmailService.sendPaymentConfirmation("user@example.com", amount);
//        }
// Skickar e-post direkt
//        if (response.isSuccess()) {
//            EmailService.sendPaymentConfirmation("user@example.com", amount);
////PaymentApiResponse response = PaymentApi.charge(API_KEY, amount);