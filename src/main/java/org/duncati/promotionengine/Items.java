package org.duncati.promotionengine;

import java.util.*;

/**
 * Stores the items for example in a cart. It stores the sku's in the cart as well as the number of items with that
 * sku in the cart. This is more efficient, especially for a large number of items, than a simple list of sku's that
 * allows duplicates. This class provides a simple set of methods for modifying its contents.
 * Note this class is not thread safe.
 */
public class Items {

    // maps the sku to the quantity of items with that sku
    private final Map<String, Integer> items=new HashMap<>();

    public Items() {
    }

    /**
     * This initializes this Items with the provided list of skus.
     * @param skus
     */
    public Items(List<String> skus) {
        for (String sku: skus) {
            addItem(sku);
        }
    }

    /**
     * A cloning constructor, this copies the input items into a new Items object.
     * @param items
     */
    public Items(Items items) {
        addItems(items);
    }

    /**
     * This returns the number of contained items matching the given sku if there are any, 0 otherwise.
     * @param sku the sku of the item
     * @return the number of sku's Items contains (or 0 if there are none)
     */
    public int getCount(String sku) {
        return items.getOrDefault(sku, 0);
    }

    /**
     * This returns an unmodifiable copy of the sku's contained herein.
     * @return an unmodifiable copy of sku's
     */
    public Set<String> getSkus() {
        return Collections.unmodifiableSet(items.keySet());
    }

    /**
     * This adds one occurrence of this sku.
     * @param sku the sku of the item to add
     */
    public void addItem(String sku) {
        addItem(sku, 1);
    }

    /**
     * This adds any number of a single sku to this instance.
     * @param sku the sku of the item to add
     * @param quantity the number of items with this sku to add
     */
    public void addItem(String sku, int quantity) {
        if (items.containsKey(sku)) {
            items.put(sku, items.get(sku)+quantity);
        } else {
            items.put(sku, quantity);
        }
    }

    /**
     * This removes one item matching this sku (if one exists).
     * @param sku the sku of the item to remove
     * @return returns true if the item was removed, false if there are no items of that sku.
     */
    public boolean removeItem(String sku) {
        return removeItem(sku, 1)==1;
    }

    /**
     * This removes the given quantity of items matching this sku (or as many as possible). If there are fewer items than
     * the quantity requests, this removes all of them -- returning the count of items successfully removed.
     * @param sku the sku of the item to remove
     * @param quantity the number of items with this sku to remove
     * @return returns the number of items actually removed (if one tries to remove 5 items and there are only 3, this returns 3)
     */
    public int removeItem(String sku, int quantity) {
        int itemCount=getCount(sku);
        if (itemCount==quantity) {
            items.remove(sku);
            return quantity;
        } else if (itemCount>quantity) {
            items.put(sku, items.get(sku)-quantity);
            return quantity;
        } else if (itemCount>0) {
            items.remove(sku);
            return itemCount;
        }
        return 0;
    }

    private Set<Map.Entry<String, Integer>> entrySet() {
        return items.entrySet();
    }

    /**
     * Convenience method, this removes the items defined in itemsToRemove from this Items. For example itemsToRemove
     * may contain 2 A's and 3 B's - exactly 2 A's and 3 B's will be removed from this instance (if possible, if there
     * are not enough A's or B's all A's/B's respectively will be removed). See removeItem documentation for more details.
     * @param itemsToRemove the Items to remove (a mapping of sku's and their quantities)
     */
    public void removeItems(Items itemsToRemove) {
        if (this==itemsToRemove) {
            throw new ConcurrentModificationException("Adding and removing items concurrently is not allowed");
        }
        itemsToRemove.entrySet().forEach((entry) -> removeItem(entry.getKey(), entry.getValue()));
    }

    /**
     * Convenience method, this adds all the items defined in itemsToAdd to this Items.
     * @param itemsToAdd the Items to remove (a mapping of sku's and their quantities)
     */
    public void addItems(Items itemsToAdd) {
        if (this==itemsToAdd) {
            throw new ConcurrentModificationException("Adding and removing items concurrently is not allowed");
        }
        itemsToAdd.entrySet().forEach((entry) -> addItem(entry.getKey(), entry.getValue()));
    }

    @Override
    public String toString() {
        StringBuilder buf=new StringBuilder();
        buf.append("Items: ");
        items.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach((entry) -> {
                    buf.append(entry.getValue());
                    buf.append(" ");
                    buf.append(entry.getKey());
                    buf.append(" ");
                });
        return buf.toString();
    }
}