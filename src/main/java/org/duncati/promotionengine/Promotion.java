package org.duncati.promotionengine;

import java.math.BigInteger;
import java.util.List;

public class Promotion implements IPromotion {

    private final List<String> skus;
    private final BigInteger price;

    public Promotion(List<String> skus, BigInteger price) {
        this.skus=skus;
        this.price=price;
    }

    public BigInteger getPrice() {
        return price;
    }

    public List<String> getSkus() {
        return skus;
    }

    public String toString() {
        return skus+" = "+price;
    }
}
