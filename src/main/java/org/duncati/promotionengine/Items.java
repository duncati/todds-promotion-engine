package org.duncati.promotionengine;

import java.util.*;

public class Items {

    // CAUTION: this class is (intentionally?) not thread safe

    // sku -> quantity
    private final Map<String, Integer> items=new HashMap<>();

    public Items() {
    }

    public Items(List<String> skus) {
        for (String sku: skus) {
            addItem(sku);
        }
    }

    public Items(Items items) {
        addItems(items);
    }

    public int getCount(String item) {
        return items.getOrDefault(item, 0);
    }

    public Set<String> getSkus() {
        return Collections.unmodifiableSet(items.keySet());
    }

    public void addItem(String item) {
        addItem(item, 1);
    }

    public void addItem(String item, int quantity) {
        if (items.containsKey(item)) {
            items.put(item, items.get(item)+quantity);
        } else {
            items.put(item, quantity);
        }
    }

    // returns true if the item was removed, false otherwise
    public boolean removeItem(String item) {
        return removeItem(item, 1)==1;
    }

    // returns number of items actually removed
    public int removeItem(String item, int quantity) {
        int itemCount=getCount(item);
        if (itemCount==quantity) {
            items.remove(item);
            return quantity;
        } else if (itemCount>quantity) {
            items.put(item, items.get(item)-quantity);
            return quantity;
        } else if (itemCount>0) {
            items.remove(item);
            return itemCount;
        }
        return 0;
    }

    public Set<Map.Entry<String, Integer>> entrySet() {
        return items.entrySet();
    }

    public void removeItems(Items itemsToRemove) {
        if (this==itemsToRemove) {
            // TODO handle this better
            System.err.println("Concurrent modification of Items is not allowed");
            return;
        }
        itemsToRemove.entrySet().forEach((entry) -> removeItem(entry.getKey(), entry.getValue()));
    }

    public void addItems(Items itemsToAdd) {
        if (this==itemsToAdd) {
            // TODO handle this better
            System.err.println("Concurrent modification of Items is not allowed");
            return;
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