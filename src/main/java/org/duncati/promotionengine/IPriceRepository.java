package org.duncati.promotionengine;

import java.math.BigInteger;

public interface IPriceRepository {

    void setPrice(String sku, BigInteger price);
    BigInteger getPrice(String sku);
}
