package org.duncati.promotionengine;

import java.math.BigDecimal;
import java.util.Collection;

public interface IRepository {

    /**
     * This sets the price of this sku to the given price.
     * @param sku the stop keeping unit
     * @param price the price of this unit
     */
    void setPrice(String sku, BigDecimal price);

    /**
     * This returns the price of this sku to the given price.
     * @param sku the stop keeping unit
     * @return the price of this sku
     */
    BigDecimal getPrice(String sku) throws DataNotFoundException;

    /**
     * Returns a collection of all known promotions.
     * @return all promotions
     */
    Collection<BasePromotion> getPromotions();

    /**
     * Adds this promotion to the data store.
     * @param promotion the promotion to add
     */
    void addPromotion(BasePromotion promotion);

    /**
     * Removes this promotion to the data store.
     * @param promotion the promotion to remove
     */
    void removePromotion(BasePromotion promotion);
}