package org.duncati.promotionengine;

import java.util.List;

public interface IPromotionPlugin {

    public int apply(List<String> items);
    public Promotion getPromotion();
}
