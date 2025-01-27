package com.example.exercise2;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    List<String> items = new ArrayList<>();

    public void addItem(String itemName) {
        items.add(itemName);
    }

    public void deleteItem(String itemName) {
        items.remove(itemName);
    }

    public int size() {
        return items.size();

    }

    public double getItemPrice(String apple) {
        return 5.0;
    }
}
