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

    @Test
    void applyDiscountTest() {
        shoppingCart.addItem("1", "Apple", 100.0,1);
        shoppingCart.applyDiscount(10);
        assertThat(shoppingCart.calculateTotal()).isEqualTo(90.0);
    }
    @Test
    void calculateTotalTest() {
        shoppingCart.addItem("1", "Apple", 20.0, 2);
        shoppingCart.addItem("2", "Banana", 10.0, 3);

        double expectedTotal = 40.0 + 30.0;
        assertThat(shoppingCart.calculateTotal()).isEqualTo(expectedTotal);
    }
    @Test
    public void testIsEmpty() {
        ShoppingCart cart = new ShoppingCart();

        assertTrue(cart.isEmpty(), "Tom varukorg.");

        cart.addItem("1", "Apple", 1.5, 2);
        assertFalse(cart.isEmpty(), "Varukorgen inte tom, lagt till en produkt");

        cart.removeItem("1");
        assertTrue(cart.isEmpty(), "Varukorg borde vara tom igen.");
    }


}