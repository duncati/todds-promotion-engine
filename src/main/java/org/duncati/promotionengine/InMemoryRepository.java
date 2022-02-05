package org.duncati.promotionengine;

import java.math.BigDecimal;
import java.util.*;

public class InMemoryRepository implements IRepository {

    private final Map<String, BigDecimal> prices=new HashMap<>();
    private final Set<BasePromotion> promotions=new HashSet<>();

    @Override
    public void setPrice(String sku, BigDecimal price) {
        prices.put(sku, price);
    }

    @Override
    public BigDecimal getPrice(String sku) throws DataNotFoundException {
        BigDecimal price=prices.get(sku);
        if (price==null) {
            throw new DataNotFoundException("No price found for sku "+sku);
        }
        return price;
    }

    @Override
    public Collection<BasePromotion> getPromotions() {
        return Collections.unmodifiableCollection(promotions);
    }

    // a future improvement: ensure that there are no other promotions with the same Sku list (or select the cheaper
    // if there are more than one)
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