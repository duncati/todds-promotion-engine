package org.duncati.promotionengine;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

// TODO: improve calculate method using better datastores for promotions and prices
public class Cart implements ICart {

    private final Items items=new Items();
    private final List<BasePromotion> promotedItems=new ArrayList<>(); // list of promotions in the cart (after calculate)
    private BigInteger total=BigInteger.ZERO; // cache of the cart's total value
    private boolean dirty=false; // flag to recompute total if cart contents are changed

    // TODO improve this:
    private static IPromotionRepository promotions; // this is a temporary hack to make the test work

    public Cart() {
    }

    @Override
    public void addItem(String sku) {
        items.addItem(sku);
        setDirty();
    }

    @Override
    public void addItem(String sku, int quantity) {
        items.addItem(sku, quantity);
        setDirty();
    }

    @Override
    public void removeItem(String sku) {
        items.removeItem(sku);
        setDirty();
    }

    @Override
    public void removeItem(String sku, int quantity) {
        items.removeItem(sku, quantity);
        setDirty();
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

    private Collection<BasePromotion> getPromotions() {
        // TODO implement this (and move it to another class)
        return promotions.getPromotions();
    }

    // TODO replace this hack with a factory or something
    public static void setPromotions(IPromotionRepository promotionsHack) {
        promotions=promotionsHack;
    }

    private void calculate() {
        //System.out.println("calculate");
        // TODO improve promotion plugin/factory interface:
        for (BasePromotion promotion: getPromotions()) {
            int times=promotion.apply(items);
            //System.out.println("promotion "+promotion+" can be applied "+times+" times");
            for (int i=0; i<times; i++) {
                for (String sku: promotion.getItems().getSkus()) {
                    items.removeItem(sku, promotion.getItems().getCount(sku));
                }
                promotedItems.add(promotion);
            }
        }
        total=BigInteger.ZERO;
        for (String sku: items.getSkus()) {
            // TODO get this price from the interface via a factory, not the singleton
            total=total.add(InMemoryPriceRepository.INSTANCE.getPrice(sku).multiply(BigInteger.valueOf(items.getCount(sku))));
            //System.out.println("after "+items.getCount(sku)+" "+sku+"'s subtotal="+total);
        }
        for (BasePromotion promotion: promotedItems) {
            total=total.add(promotion.getPrice());
        }
        dirty=false;
    }
}