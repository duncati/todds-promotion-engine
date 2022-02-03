package org.duncati.promotionengine;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public enum InMemoryPriceRepository implements IPriceRepository {

    INSTANCE;

    private final Map<String, BigInteger> prices=new HashMap<>();

    @Override
    public void setPrice(String sku, BigInteger price) {
        prices.put(sku, price);
    }

    @Override
    public BigInteger getPrice(String sku) {
        // TODO handle non-existent skus
        return prices.get(sku);
    }
}