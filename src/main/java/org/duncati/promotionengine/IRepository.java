package org.duncati.promotionengine;

import java.math.BigInteger;
import java.util.Collection;

public interface IRepository {

    // sku price data
    void setPrice(String sku, BigInteger price);
    BigInteger getPrice(String sku);

    // promotion data
    Collection<BasePromotion> getPromotions();
    void addPromotion(BasePromotion promotion);
    void removePromotion(BasePromotion promotion);

}