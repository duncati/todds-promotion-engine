package org.duncati.promotionengine;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class ItemsTest {

    @Test
    @DisplayName("Test adding and removing items (both single and multiple)")
    void testAddRemoveItems() {
        Items items=new Items();
        String sku="A";

        // test adding 1 item
        items.addItem(sku);
        Assertions.assertEquals(1, items.getCount(sku));

        // test adding multiple items
        items.addItem(sku, 9);
        Assertions.assertEquals(10, items.getCount(sku));

        // test removing 1 item
        items.removeItem(sku);
        Assertions.assertEquals(9, items.getCount(sku));

        // test removing multiple items
        items.removeItem(sku, 8);
        Assertions.assertEquals(1, items.getCount(sku));

        // test removing too many items
        items.removeItem(sku, 5);
        Assertions.assertEquals(0, items.getCount(sku));
    }
}