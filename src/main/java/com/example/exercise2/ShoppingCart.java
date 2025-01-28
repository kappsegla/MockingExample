package com.example.exercise2;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    List<CartItem> items = new ArrayList<>();

    public void addItem(String itemName, int quantity, double price) {
        items.add(new CartItem(itemName, quantity, price));
    }

    public void deleteItem(String itemName) {
        items.stream().filter(cartItem -> cartItem.itemName().equals(itemName))
                .findFirst()
                .ifPresent(item -> items.remove(item));
    }

    public int size() {
        return items.size();

    }

    public double getItemPrice(String itemName) {
        return items.stream()
                .filter(cartItem -> cartItem.itemName().equals(itemName))
                .map(CartItem::price)
                .findFirst()
                .orElse(-1.0);
    }

    public double totalSum() {
        return items.stream()
                .map(CartItem::price)
                .reduce(0.0, Double::sum);
        }

    public void applySaleToItem(String itemName, double discount) {
    }
}
