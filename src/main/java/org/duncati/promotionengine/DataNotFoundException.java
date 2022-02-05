package org.duncati.promotionengine;

/**
 * This is thrown if there is no data found (missing sku, missing price, missing promotion, etc.).
 */
public class DataNotFoundException extends PromotionEngineException {

    public DataNotFoundException(String msg) {
        super(msg);
    }
}
