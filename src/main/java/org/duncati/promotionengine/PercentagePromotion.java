package org.duncati.promotionengine;

import java.math.BigDecimal;
import java.math.BigInteger;

public class PercentagePromotion extends BasePromotion {
    // example of a percentage promotion, needs work FIXME

    private final BigDecimal percentageOfPrice;

    // percentOff is expected to be a float ranging between 0 and 1
    public PercentagePromotion(Items items, float percentOff) {
        super(items);
        if (percentOff<0 || percentOff >1) {
            // TODO throw configuration error
        }
        this.percentageOfPrice=new BigDecimal(1f-percentOff);
    }

    // there's some obvious rounding questions here that must be answered before this class could be used FIXME
    public BigInteger getPrice() {
        BigInteger total=BigInteger.ZERO;
        for (String sku: getItems().getSkus()) {
            // TODO get this price from the interface via a factory, not the singleton
            // FIXME code duplication from Cart.calculate
            total=total.add(RepositoryFactory.INSTANCE.getRepository().getPrice(sku).multiply(BigInteger.valueOf(getItems().getCount(sku))));
        }
        return new BigDecimal(total).multiply(percentageOfPrice).toBigInteger();
    }
}