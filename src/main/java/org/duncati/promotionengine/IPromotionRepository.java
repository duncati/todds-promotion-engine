package org.duncati.promotionengine;

import java.util.Collection;

public interface IPromotionRepository {

    Collection<BasePromotion> getPromotions();
}
