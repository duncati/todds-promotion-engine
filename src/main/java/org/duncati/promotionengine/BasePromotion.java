package org.duncati.promotionengine;

import java.math.BigInteger;

public abstract class BasePromotion {

    private final Items items;

    public BasePromotion(Items items) {
        this.items=items;
    }

    public abstract BigInteger getPrice();

    public Items getItems() {
        return items;
    }

    public int apply(Items items) {
        Items itemsCopy=new Items(items);
        int applyCount=0;
        while (true) {
            for (String sku: getItems().getSkus()) {
                if (itemsCopy.removeItem(sku, getItems().getCount(sku))!=getItems().getCount(sku)) {
                    return applyCount;
                }
            }
            applyCount++;
        }
    }

    @Override
    public String toString() {
        return getItems()+" = "+getPrice();
    }
}