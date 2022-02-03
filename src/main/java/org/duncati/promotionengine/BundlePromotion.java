package org.duncati.promotionengine;

import java.math.BigInteger;

public class BundlePromotion extends BasePromotion {

    // simple flat fee promotion offering lower price for a set (bundle) of items

    private final BigInteger price;

    public BundlePromotion(Items items, BigInteger price) {
        super(items);
        this.price=price;
    }

    public BigInteger getPrice() {
        return price;
    }
}
