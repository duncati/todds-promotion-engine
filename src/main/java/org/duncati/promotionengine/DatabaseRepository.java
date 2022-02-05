package org.duncati.promotionengine;

import java.math.BigDecimal;
import java.util.Collection;

public class DatabaseRepository implements IRepository {

    @Override
    public void setPrice(String sku, BigDecimal price) {
        throw new UnsupportedOperationException("DatabaseRepository.setPrice is not implemented");
    }

    @Override
    public BigDecimal getPrice(String sku) {
        throw new UnsupportedOperationException("DatabaseRepository.getPrice is not implemented");
    }

    @Override
    public Collection<BasePromotion> getPromotions() {
        throw new UnsupportedOperationException("DatabaseRepository.getPromotions is not implemented");
    }

    @Override
    public void addPromotion(BasePromotion promotion) {
        throw new UnsupportedOperationException("DatabaseRepository.getPromotion is not implemented");
    }

    @Override
    public void removePromotion(BasePromotion promotion) {
        throw new UnsupportedOperationException("DatabaseRepository.removePromotion is not implemented");
    }
}
