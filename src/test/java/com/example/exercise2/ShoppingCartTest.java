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
        shoppingCart.addItem("apple", 10, 5.0);
        assertThat(shoppingCart.items).extracting("itemName", "quantity", "price")
                .contains(tuple("apple", 10, 5.0));

    }

    @Test
    @DisplayName("deleteItemInShoppingCartByNameTest")
    void deleteItemInShoppingCartByNameTest() {
        shoppingCart.addItem("pineapple", 10, 10.0);
        shoppingCart.addItem("apple", 10, 5.0);
        shoppingCart.deleteItem("apple");
        assertThatList(shoppingCart.items).extracting("itemName", "quantity", "price").contains(tuple("pineapple", 10, 10.0));
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
        shoppingCart.addItem("apple", 10, 5.0);
        shoppingCart.addItem("pineapple", 10, 10.0);
        assertThat(shoppingCart.getItemPrice("apple")).isEqualTo(5.0);

    }

}
