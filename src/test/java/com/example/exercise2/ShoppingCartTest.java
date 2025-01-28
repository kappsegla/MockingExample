package com.example.exercise2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
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

    @Nested
    public class EmptyShoppingCart {
        @Test
        @DisplayName("Shopping Cart Items is of Length zero at Start Test")
        void shoppingCartItemsIsOfLengthZeroAtStartTest() {
            assertThat(shoppingCart.size()).isEqualTo(0);

        }

        @Test
        @DisplayName("Calculates sum for empty shopping cart Test")
        void calculatesSumForEmptyShoppingCartTest() {
            assertThat(shoppingCart.totalSum()).isEqualTo(0);

        }

        @Test
        @DisplayName("get price item minus one if item not found Test")
        void getPriceItemMinusOneIfItemNotFoundTest() {
            assertThat(shoppingCart.getItemPrice("")).isEqualTo(-1);

        }

        @Test
        @DisplayName("Apply Sales to empty shopping cart results in No item found exception test")
        void applySalesToEmptyShoppingCartResultsInNoItemFoundExceptionTest() {
            assertThatThrownBy(() -> shoppingCart.applySaleToItem("apple", 0.75)).
                    isInstanceOf(IllegalArgumentException.class).hasMessageContaining("No item found");

        }


    }

    @Nested
    class itemsInShoppingCart {

        @BeforeEach
        void setUp() {
            shoppingCart.addItem("pineapple", 10, 10.0);
            shoppingCart.addItem("apple", 10, 5.0);
            shoppingCart.addItem("kiwi", 1, 15.0);

        }

        @Test
        @DisplayName("Adding Item to shopping cart test")
        void addingItemToShoppingCartTest() {
            assertThat(shoppingCart.items).extracting("itemName", "quantity", "price")
                    .contains(tuple("apple", 10, 5.0));

        }

        @Test
        @DisplayName("Delete Item In Shopping Cart By Name Test")
        void deleteItemInShoppingCartByNameTest() {
            shoppingCart.deleteItem("apple");
            shoppingCart.deleteItem("kiwi");
            assertThatList(shoppingCart.items).extracting("itemName", "quantity", "price").contains(tuple("pineapple", 10, 10.0));
            assertThatList(shoppingCart.items).size().isEqualTo(1);

        }

        @Test
        @DisplayName("Get Item Price Test")
        void getItemPriceTest() {
            assertThat(shoppingCart.getItemPrice("apple")).isEqualTo(5.0);

        }

        @Test
        @DisplayName("Calculates total price for items in ShoppingCart Test")
        void calculatesTotalPriceForItemsInShoppingCartTest() {
            assertThat(shoppingCart.totalSum()).isEqualTo(30.0);

        }

        @Test
        @DisplayName("Apply Sales for item in ShoppingCart Test")
        void applySalesForItemInShoppingCartTest() {
            shoppingCart.applySaleToItem("pineapple", 0.25);
            assertThat(shoppingCart.getItemPrice("pineapple")).isEqualTo(7.5);

        }


    }

}
