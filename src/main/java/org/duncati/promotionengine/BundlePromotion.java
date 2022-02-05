package org.duncati.promotionengine;

import java.math.BigDecimal;

/**
 * This promotion defines a static price for a given set of items, for example a single item A might cost 50
 * but 3 A's are being promoted (an advertised discount) as costing only 130.
 */
public class BundlePromotion extends BasePromotion {

    // simple flat fee promotion offering lower price for a set (bundle) of items

    private final BigDecimal price;

    /**
     * Creates a bundle promotion for the given items at the given price
     * @param items the items in this promotion
     * @param price the price for this promotion
     */
    public BundlePromotion(Items items, BigDecimal price) {
        super(items);
        this.price=price;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
