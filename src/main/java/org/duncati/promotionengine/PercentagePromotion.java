package org.duncati.promotionengine;

import java.math.BigDecimal;
import java.math.BigDecimal;

/**
 * An example of a percentage based promotion establishing the extensibility of the Promotion class structure.
 * The math here gets more complicated as this introduces rounding issues, so business rules need to be defined
 * to implement this correctly.
 */
public class PercentagePromotion extends BasePromotion {

    private final BigDecimal percentageOfPrice;

    /**
     * This applies a percentage discount off of the base price for all items contained herein. Note that this is a
     * percent _off_ or a discount. If an item costs 100 and we want 20% off set percentOff to .2.
     * Setting percentOff to 1 is 100$ off, or free. This class does not allow for the base price to go up (i.e.
     * percentOff cannot be <0).
     * @param items the items in this promotion
     * @param percentOff the percent discount to apply which must be >=0 and <=1
     */
    public PercentagePromotion(Items items, float percentOff) {
        super(items);
        if (percentOff<0 || percentOff >1) {
            throw new IllegalArgumentException("PercentagePromotion requires that percentOff is between 0 and 1, "+percentOff+" is invalid.");
        }
        this.percentageOfPrice=new BigDecimal(1f-percentOff);
    }

    // FIXME there's some obvious rounding questions here that must be answered before this class could be used correctly
    public BigDecimal getPrice() throws DataNotFoundException {
        BigDecimal total=BigDecimal.ZERO;
        for (String sku: getItems().getSkus()) {
            // TODO get this price from the interface via a factory, not the singleton
            // FIXME code duplication from Cart.calculate
            total=total.add(RepositoryFactory.INSTANCE.getRepository().getPrice(sku).multiply(BigDecimal.valueOf(getItems().getCount(sku))));
        }
        return total.multiply(percentageOfPrice);
    }
}