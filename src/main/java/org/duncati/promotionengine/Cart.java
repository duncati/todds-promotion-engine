package org.duncati.promotionengine;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

// TODO: improve calculate method using better datastores for promotions and prices
public class Cart implements ICart {

    private final List<String> items=new ArrayList<>(); // list of non-discounted items (or all items before promotions are counted)
    private final List<IPromotion> promotedItems=new ArrayList<>(); // list of promotions in the cart (after calculate)
    private BigInteger total=BigInteger.ZERO; // cache of the cart's total value
    private boolean dirty=false; // flag to recompute total if cart contents are changed

    // TODO improve these:
    private static IPromotionRepository promotions; // this is a temporary hack to make the test work
    private static IPriceRepository prices; // this is a temporary hack to make the test work

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

    @Override
    public void removeItem(String sku, int quantity) {
        for (int i=0; i<quantity; i++) {
            if (items.remove(sku)) {
                setDirty();
            } else {
                // TODO report error, item not found in cart
            }
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

    private Collection<IPromotion> getPromotions() {
        // TODO implement this (and move it to another class)
        return promotions.getPromotions();
    }

    // TODO replace this hack with a factory or something
    public static void setPromotions(IPromotionRepository promotionsHack) {
        promotions=promotionsHack;
    }

    // TODO replace this hack with a factory or something
    public static void setPrices(IPriceRepository pricesHack) {
        prices=pricesHack;
    }

    private void calculate() {
        // TODO improve promotion plugin/factory interface:
        for (IPromotion promotion: getPromotions()) {
            int times=promotion.apply(items);
            for (int i=0; i<times; i++) {
                for (String item: promotion.getSkus()) {
                    items.remove(item);
                }
                promotedItems.add(promotion);
            }
        }
        total=BigInteger.ZERO;
        for (String item: items) {
            // TODO improve price database:
            total=total.add(prices.getPrice(item));
        }
        for (IPromotion promotion: promotedItems) {
            total=total.add(promotion.getPrice());
        }
        dirty=false;
    }
}