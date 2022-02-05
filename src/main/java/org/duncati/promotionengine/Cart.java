package org.duncati.promotionengine;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Cart implements ICart {

    private final Items items=new Items();
    private final List<BasePromotion> promotedItems=new ArrayList<>(); // list of promotions in the cart (after calculate)
    private BigInteger total=BigInteger.ZERO; // cache of the cart's total value
    private boolean dirty=false; // flag to recompute total if cart contents are changed

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
        if (items.removeItem(sku)) {
            setDirty();
        }
    }

    @Override
    public void removeItem(String sku, int quantity) {
        if (items.removeItem(sku, quantity)==quantity) {
            setDirty();
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

    private Collection<BasePromotion> getPromotions() {
        return RepositoryFactory.INSTANCE.getRepository().getPromotions();
    }

    private BigInteger getPrice(String sku) {
        return RepositoryFactory.INSTANCE.getRepository().getPrice(sku);
    }

    private void calculate() {
        //System.out.println("calculate");
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
            total=total.add(getPrice(sku).multiply(BigInteger.valueOf(items.getCount(sku))));
            //System.out.println("after "+items.getCount(sku)+" "+sku+"'s subtotal="+total);
        }
        for (BasePromotion promotion: promotedItems) {
            total=total.add(promotion.getPrice());
        }
        dirty=false;
    }
}