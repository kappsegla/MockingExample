package com.example.exercise2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

public class ShoppingCartTest {
    //FIXME Add the following test cases for ShoppingCartTest:
    //  - Add items
    //  - Delete items
    //  - Calculate total sum for all items
    //  - Apply sales discount
    //  - Mange/handle item stock updates

    ShoppingCart shoppingCart = new ShoppingCart();

    @Test
    @DisplayName("Adding Item to shopping cart")
    void addingItemToShoppingCart() {
        shoppingCart.add("apple");
        assertThatList(shoppingCart.items).contains("apple");

    }

}
