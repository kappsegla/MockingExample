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
    @DisplayName("Adding Item to shopping cart test")
    void addingItemToShoppingCartTest() {
        shoppingCart.addItem("apple");
        assertThatList(shoppingCart.items).contains("apple");

    }

    @Test
    @DisplayName("deleteItemInShoppingCartTest")
    void deleteItemInShoppingCartTest() {
        shoppingCart.addItem("apple");
        shoppingCart.addItem("pineapple");
        shoppingCart.deleteItem("apple");
        assertThatList(shoppingCart.items).containsExactly("pineapple");
        assertThatList(shoppingCart.items).size().isEqualTo(1);

    }

    @Test
    @DisplayName("Shopping Cart Items is of Length zero at Start Test")
    void shoppingCartItemsIsOfLengthZeroAtStartTest() {
        assertThat(shoppingCart.size()).isEqualTo(0);

    }

    @Test
    @DisplayName("getItemPriceTest")
    void getItemPriceTest() {
        shoppingCart.addItem("apple");
        assertThat(shoppingCart.getItemPrice("apple")).isEqualTo(5.00);

    }

}
