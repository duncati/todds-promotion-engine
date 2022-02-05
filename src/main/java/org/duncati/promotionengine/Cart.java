package org.duncati.promotionengine;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * This holds the items in the cart, calculates the total price and while doing so applies any provided promotions.
 * Note this class is not thread safe.
 */
public class Cart implements ICart {

    // holds and manages the items in the cart (allowing for various item management implementations to be written without
    // modifying this class)
    private final Items items=new Items();

    // cache of the cart's total value (managed by the dirty flag below)
    private BigInteger totalPrice=BigInteger.ZERO;

    // flag to recompute the total price if cart's contents are changed
    private boolean dirty=false;

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
        if (items.removeItem(sku, quantity)>0) {
            setDirty();
        }
    }

    private void setDirty() {
        dirty=true;
    }

    @Override
    public BigInteger getTotal() throws DataNotFoundException {
        if (dirty) {
            calculateTotal();
        }
        return totalPrice;
    }

    private Collection<BasePromotion> getPromotions() {
        return RepositoryFactory.INSTANCE.getRepository().getPromotions();
    }

    private BigInteger getPrice(String sku) throws DataNotFoundException {
        return RepositoryFactory.INSTANCE.getRepository().getPrice(sku);
    }

    /**
     * This runs through the following stages to compute the total price of the items in this cart:
     *    * make a copy of the cart's items (copy)
     *    * for each known promotion, count how many times that promotion can be applied
     *          remove the promoted items from the copy
     *          accumulate the promotion price to the total
     *    * for each item left in the copy, accumulate the price to the total
     * @throws DataNotFoundException thrown if the price data cannot be found (i.e. cannot be read from the repository)
     */
    private void calculateTotal() throws DataNotFoundException {
        Items itemsCopy=new Items(items);
        BigInteger price=BigInteger.ZERO;
        for (BasePromotion promotion: getPromotions()) {
            int timesPromotionCanBeApplied=promotion.apply(itemsCopy);
            for (String sku: promotion.getItems().getSkus()) {
                itemsCopy.removeItem(sku, timesPromotionCanBeApplied*promotion.getItems().getCount(sku));
            }
            price=price.add(promotion.getPrice().multiply(BigInteger.valueOf(timesPromotionCanBeApplied)));
        }
        for (String sku: itemsCopy.getSkus()) {
            price=price.add(getPrice(sku).multiply(BigInteger.valueOf(itemsCopy.getCount(sku))));
        }
        totalPrice=price;
        dirty=false;
    }
}