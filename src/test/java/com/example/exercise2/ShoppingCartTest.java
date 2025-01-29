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
    public class emptyShoppingCartTests {
        @Test
        @DisplayName("Shopping Cart Items is of Length zero at Start Test")
        void shoppingCartItemsIsOfLengthZeroAtStartTest() {
            assertThat(shoppingCart.size()).isEqualTo(0);

        }

        @Test
        @DisplayName("Calculates sum for empty shopping cart Test")
        void calculatesSumForEmptyShoppingCartTest() {
            assertThat(shoppingCart.totalSum()).isEqualTo(0.0);

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

        @Test
        @DisplayName("Try to update quantity with empty shopping cart Throws Exception Test")
        void tryToUpdateQuantityWithEmptyShoppingCartThrowsExceptionTest() {
            assertThatThrownBy(() -> shoppingCart.updateItemQuantity("apple", 1))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("No item found");

        }


    }

    @Nested
    class itemsInShoppingCartTests {

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
            assertThat(shoppingCart.items).extracting("itemName", "quantity", "price").contains(tuple("pineapple", 10, 10.0));
            assertThat(shoppingCart.items).size().isEqualTo(1);

        }

        @Test
        @DisplayName("Get Item Price Test")
        void getItemPriceTest() {
            assertThat(shoppingCart.getItemPrice("apple")).isEqualTo(5.0);

        }

        @Test
        @DisplayName("Calculates total price for items in ShoppingCart Test")
        void calculatesTotalPriceForItemsInShoppingCartTest() {
            assertThat(shoppingCart.totalSum()).isEqualTo(165.0);

        }

        @Test
        @DisplayName("Apply Sales for item in ShoppingCart Test")
        void applySalesForItemInShoppingCartTest() {
            shoppingCart.applySaleToItem("pineapple", 0.25);
            assertThat(shoppingCart.getItemPrice("pineapple")).isEqualTo(7.5);

        }

        @Test
        @DisplayName("Items in shopping cart updates quantity additive test ")
        void itemsInShoppingCartUpdatesQuantityAdditiveTest() {
            shoppingCart.updateItemQuantity("kiwi", 1);
            assertThat(shoppingCart.getItem("kiwi"))
                    .extracting("itemName", "quantity", "price")
                    .contains("kiwi", 2, 15.0);

        }

        @Test
        @DisplayName("Items in shopping cart updates quantity subtractive test")
        void itemsInShoppingCartUpdatesQuantitySubtractiveTest() {
            shoppingCart.updateItemQuantity("apple", -1);
            assertThat(shoppingCart.getItem("apple"))
                    .extracting("itemName", "quantity", "price")
                    .containsExactly("apple", 9, 5.0);

        }

        @Test
        @DisplayName("Items quantity is negative results in Exception Test")
        void itemsQuantityIsNegativeResultsInExceptionTest() {
            assertThatThrownBy(() -> shoppingCart.updateItemQuantity("kiwi", -2))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("Item cannot have negative quantity");

        }

        @Test
        @DisplayName("Items quantity zero is remove from shopping cart Test")
        void itemsQuantityZeroIsRemoveFromShoppingCartTest() {
            shoppingCart.updateItemQuantity("kiwi", -1);
            assertThatThrownBy(() -> shoppingCart.getItem("kiwi"))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("No item found");

        }

    }

    @Nested
    class inputNormalizationTest {
        @BeforeEach
        void setUp() {
            shoppingCart.addItem("apple", 1, 1.0);
        }

        @Test
        @DisplayName("Empty item name when adding new item Throws Exception Test ")
        void emptyItemNameWhenAddingNewItemThrowsExceptionTest() {
            assertThatThrownBy(() -> shoppingCart.addItem("", 1, 1.0))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Item name cannot be empty or null");

        }

        @Test
        @DisplayName("Passing Null as item name Throws exception Test")
        void passingNullAsItemNameThrowsExceptionTest() {
            assertThatThrownBy(() -> shoppingCart.addItem(null, 0, 1.0))
                    .isInstanceOf(IllegalArgumentException.class).hasMessage("Item name cannot be empty or null");

        }

        @Test
        @DisplayName("Duplicate item name should not be added to shopping cart test")
        void duplicateItemNameShouldNotBeAddedToShoppingCartTest() {
            assertThatThrownBy(() -> shoppingCart.addItem("apple", 4, 2.0))
                    .isInstanceOf(IllegalArgumentException.class).hasMessage("Item name already exists");
            assertThat(shoppingCart.size()).isEqualTo(1);
            assertThat(shoppingCart.getItem("apple"))
                    .extracting("itemName", "quantity", "price")
                    .containsExactly("apple", 1, 1.0);

        }

        @Test
        @DisplayName("Partial overlap with item name in Shopping Cart is Allowed Test")
        void partialOverlapWithItemNameInShoppingCartIsAllowedTest() {
            shoppingCart.addItem("pple", 4, 2.0);
            assertThat(shoppingCart.size()).isEqualTo(2);

        }

        @Test
        @DisplayName("Item name case ignore test")
        void itemNameCaseIgnoreTest() {
            assertThatThrownBy(() -> shoppingCart.addItem("Apple", 4, 2.0))
                    .isInstanceOf(IllegalArgumentException.class).hasMessage("Item name already exists");

        }
    }

}
