package org.duncati.promotionengine;

import java.math.BigInteger;

/**
 * Defines the operations available for Carts which are to manage the items (sku's) in the cart and from those
 * items to discern the total price of the items in the cart.
 */
public interface ICart {

    /**
     * Adds this sku to the cart (or increases the quantity of this sku if one already exists)
     * @param sku the stop keeping unit
     */
    void addItem(String sku);

    /**
     * Adds this quantity of this sku to the cart (or increases the quantity of this sku if any already).
     * @param sku the stop keeping unit
     * @param quantity the number of units to add
     */
    void addItem(String sku, int quantity);

    /**
     * Removes one occurrence of this sku from the cart if one exists. If not, nothing happens.
     * This could return success/failure or throw if none exist but chose not to for this exercise.
     * @param sku the stop keeping unit
     */
    void removeItem(String sku);

    /**
     * Removes up to the given quantity of this sku from the cart (if there are not enough, this removes all).
     * If none are removed, nothing happens.
     * This could return success/failure or throw if not enough exist but chose not to for this exercise.
     * @param sku the stop keeping unit
     * @param quantity the number of units to remove
     */
    void removeItem(String sku, int quantity);


    /**
     * Calculates the total price of the items and promotions in this cart.
     * @return the total price of the cart
     * @throws DataNotFoundException any error encountered during price calculation (i.e. missing prices for sku)
     */
    BigInteger getTotal() throws DataNotFoundException;
}
