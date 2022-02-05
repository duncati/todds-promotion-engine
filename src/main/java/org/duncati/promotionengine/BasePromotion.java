package org.duncati.promotionengine;

import java.math.BigInteger;

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
    public abstract BigInteger getPrice() throws DataNotFoundException;

    /**
     * Returns this promotion's items.
     * @return the items in this promotion
     */
    public Items getItems() {
        return items;
    }

    /**
     * This method counts the number of times this promotion can be applied to the given cart items. For example, if
     * cartItems contains 12 A's and the promotion is for 5 A's apply() would return 2. It does _not_ modify the contents
     * of the passed in cartItems.
     * @param cartItems The items for example in a Cart that promotions could be applied to
     * @return
     */
    public int apply(Items cartItems) {
        Items cartItemsCopy=new Items(cartItems);
        int applyCount=0;
        while (true) {
            for (String sku: getItems().getSkus()) {
                if (cartItemsCopy.removeItem(sku, getItems().getCount(sku))!=getItems().getCount(sku)) {
                    return applyCount;
                }
            }
            applyCount++;
        }
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