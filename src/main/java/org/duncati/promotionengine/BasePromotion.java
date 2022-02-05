package org.duncati.promotionengine;

import java.math.BigDecimal;

/**
 * The parent class of all promotions, this class stores the promotions items in an Items object. Subclasses must
 * define what price to return for their type of promotion.
 */
public abstract class BasePromotion {

    private final Items items;

    public BasePromotion(Items items) {
        this.items=items;
    }

    /**
     * Returns the price for this promotion.
     * @return the promotion's price
     * @throws DataNotFoundException Thrown if the backing repository fails to retrieve optional price information
     */
    public abstract BigDecimal getPrice() throws DataNotFoundException;

    /**
     * Returns this promotion's items.
     * @return the items in this promotion
     */
    public Items getItems() {
        return items;
    }

    /**
     * This method counts the number of times this promotion can be applied to the given cart items. For example, if
     * cartItems contains 12 A's and the promotion is for 5 A's then apply() returns 2. It does not modify the contents
     * of the passed in cartItems.
     * @param cartItems The items for example in a Cart that promotions could be applied to
     * @return the number of times this promotion can be applied to the given cart items
     */
    public int apply(Items cartItems) {
        int applyCount=Integer.MAX_VALUE;
        for (String sku : getItems().getSkus()) {
            applyCount=Math.min(applyCount, cartItems.getCount(sku)/getItems().getCount(sku));
        }
        return applyCount;
    }

    @Override
    public String toString() {
        try {
            return getItems()+" = "+getPrice();
        } catch (DataNotFoundException e) {
            return getItems()+" PROMOTION IS MISSING PRICE DATA";
        }
    }
}