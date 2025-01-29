package com.example.exercise2;

import java.util.HashSet;

public class ShoppingCart {
    HashSet<CartItem> items = new HashSet<>();

    public void addItem(String itemName, int quantity, double price) {
        if(itemName == null || itemName.trim().isEmpty()) {
            throw new IllegalArgumentException("Item name cannot be empty or null");
        }
        if(items.stream().anyMatch(i -> i.itemName().equalsIgnoreCase(itemName))) {
            throw new IllegalArgumentException("Item name already exists");
        }

        if(!(quantity >= 1)) {
            throw new IllegalArgumentException("Quantity cannot be less than one");
        }

        if(!(price > 0)) {
            throw new IllegalArgumentException("Item price cannot be less than zero");
        }
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

    public CartItem getItem(String itemName) {
        return items.stream()
                .filter(cartItem -> cartItem.itemName().equals(itemName))
                .findFirst().orElseThrow(() -> new IllegalArgumentException("No item found"));
    }

    public double totalSum() {
        var priceMultipliedQt = items.stream()
                .map(cartItem -> cartItem.price() * cartItem.quantity()).toList();

        return priceMultipliedQt.stream().reduce(0.0, Double::sum);
    }

    public void applySaleToItem(String itemName, double discount) {
        var item = getItem(itemName);
        deleteItem(itemName);
        var newPrice = item.price() - (item.price() * discount);
        addItem(itemName, item.quantity(), newPrice);
    }

    public void updateItemQuantity(String itemName, int quantity) {
        var item = getItem(itemName);
        var newQuantity = item.quantity() + quantity;
        if (newQuantity < 0) {
            throw new IllegalArgumentException("Item cannot have negative quantity");
        }

        if (newQuantity == 0) deleteItem(itemName);
        else {
            deleteItem(itemName);
            addItem(itemName, newQuantity, item.price());
        }
    }
}
