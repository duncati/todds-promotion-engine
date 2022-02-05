package org.duncati.promotionengine;

import java.math.BigInteger;
import java.util.*;

public class InMemoryRepository implements IRepository {

    private final Map<String, BigInteger> prices=new HashMap<>();
    private final Set<BasePromotion> promotions=new HashSet<>();

    @Override
    public void setPrice(String sku, BigInteger price) {
        prices.put(sku, price);
    }

    @Override
    public BigInteger getPrice(String sku) {
        BigInteger price=prices.get(sku);
        if (price==null) {
            throw new PromotionEngineException("No price found for sku "+sku);
        }
        return price;
    }

    @Override
    public Collection<BasePromotion> getPromotions() {
        return Collections.unmodifiableCollection(promotions);
    }

    // future improvement: ensure that there are no other promotions with the same Sku list (or select the cheaper price)
    @Override
    public void addPromotion(BasePromotion promotion) {
        promotions.add(promotion);
    }

    // not actually needed, yet
    @Override
    public void removePromotion(BasePromotion promotion) {
        promotions.remove(promotion);
    }
}