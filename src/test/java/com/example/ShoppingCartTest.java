package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
class ShoppingCartTest {
    private ShoppingCart shoppingCart;

    @BeforeEach
    void setUp() {
        shoppingCart = new ShoppingCart();
    }
    @Test
    void addItemToCartTest() {
        shoppingCart.addItem("1", "Apple", 10.0,2);
        assertThat(shoppingCart.calculateTotal()).isEqualTo(20.0);
    }

    @Test
    void removeItemFromCartTest() {
        shoppingCart.addItem("1", "Apple", 10.0,2);
        shoppingCart.removeItem("1");
        assertThat(shoppingCart.calculateTotal()).isEqualTo(0.0);
    }


}