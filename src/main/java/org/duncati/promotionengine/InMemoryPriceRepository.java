package org.duncati.promotionengine;

import java.math.BigInteger;
import java.util.Map;

public class InMemoryPriceRepository implements IPriceRepository {

    private final Map<String, BigInteger> prices;

    public InMemoryPriceRepository(Map<String, BigInteger> prices) {
        this.prices=prices;
    }

    @Override
    public BigInteger getPrice(String sku) {
        // TODO handle unknown skus
        return prices.get(sku);
    }
}