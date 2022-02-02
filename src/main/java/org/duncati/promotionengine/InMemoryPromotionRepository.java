package org.duncati.promotionengine;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class InMemoryPromotionRepository implements IPromotionRepository {

    final Set<IPromotion> promotions=new HashSet<>();

    @Override
    public Collection<IPromotion> getPromotions() {
        return Collections.unmodifiableCollection(promotions);
    }

    // future improvement: ensure that there are no other promotions with the same Sku list (or select the cheaper price)
    // TODO implement default IPromotion hashcode
    public void addPromotion(IPromotion promotion) {
        promotions.add(promotion);
    }

    // not actually needed, yet
    // TODO implement hashcode in IPromotion
    public void removePromotion(IPromotion promotion) {
        promotions.remove(promotion);
    }
}