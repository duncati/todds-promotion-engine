package org.duncati.promotionengine;

import java.math.BigInteger;
import java.util.Map;

public interface IPriceRepository {

    public BigInteger getPrice(String sku);
}
