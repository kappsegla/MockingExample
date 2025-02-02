package com.example;

public class CartItem {
    private final String name;
    private final double price;
    private int quantity;

    public CartItem(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }
    public void increaseQuantity(int amount) {
        this.quantity+= amount;
    }

    public double totalPrice() {
        return price * quantity;
    }
}
