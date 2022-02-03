package org.duncati.promotionengine;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class InMemoryPromotionRepository implements IPromotionRepository {

    final Set<BasePromotion> promotions=new HashSet<>();

    @Override
    public Collection<BasePromotion> getPromotions() {
        return Collections.unmodifiableCollection(promotions);
    }

    // future improvement: ensure that there are no other promotions with the same Sku list (or select the cheaper price)
    // TODO implement default IPromotion hashcode
    public void addPromotion(BasePromotion promotion) {
        promotions.add(promotion);
    }

    // not actually needed, yet
    // TODO implement hashcode in IPromotion
    public void removePromotion(BasePromotion promotion) {
        promotions.remove(promotion);
    }
}