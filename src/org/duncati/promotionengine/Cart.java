package org.duncati.promotionengine;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

// TODO: calculate method, track prices, track promotions, etc.
public class Cart implements ICart {

    private final List<String> items=new ArrayList<>(); // list of non-discounted items (or all items before promotions are counted)
    private final List<Promotion> promotedItems=new ArrayList<>(); // list of promotions in the cart (after calculate)
    private BigInteger total=BigInteger.ZERO; // cache of the cart's total value
    private boolean dirty=false; // flag to recompute total if cart contents are changed

    public Cart() {
    }

    @Override
    public void addItem(String sku) {
        items.add(sku);
        setDirty();
    }

    @Override
    public void addItem(String sku, int quantity) {
        for (int i=0; i<quantity; i++) {
            items.add(sku);
        }
        setDirty();
    }

    @Override
    public void removeItem(String sku) {
        if (items.remove(sku)) {
            setDirty();
        } else {
            // TODO report error, item not found in cart
        }
    }

    private void setDirty() {
        dirty=true;
    }

    @Override
    public BigInteger getTotal() {
        if (dirty) {
            calculate();
        }
        return total;
    }

    private List<IPromotionPlugin> getPromotions() {
        // TODO implement this (and move it to another class)
        return null;
    }

    private void calculate() {
        // TODO promotion plugin/factory interface here:
        for (IPromotionPlugin plugin: getPromotions()) {
            int times=plugin.apply(items);
            Promotion promotion=plugin.getPromotion();
            for (int i=0; i<times; i++) {
                for (String item: promotion.getSkus()) {
                    items.remove(item);
                }
                promotedItems.add(promotion);
            }
        }
        total=BigInteger.ZERO;
        for (String item: items) {
            // TODO price database here:
            //total=total.add(getPrice(item));
        }
        for (Promotion promotion: promotedItems) {
            total=total.add(promotion.getPrice());
        }
        dirty=false;
    }
}