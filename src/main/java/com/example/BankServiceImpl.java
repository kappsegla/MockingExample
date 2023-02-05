package com.example;

public class BankServiceImpl implements BankService {
    boolean isPaid = false;
    @Override
    public void pay(String id, double amount) {
        isPaid = true;
    }
}
