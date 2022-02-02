package org.duncati.promotionengine;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public interface IPromotion {

    public default int apply(Collection<String> items) {
        List<String> itemsCopy=new ArrayList<>(items);
        int applyCount=0;
        while (true) {
            for (String sku: getSkus()) {
                if (!itemsCopy.remove(sku)) {
                    return applyCount;
                }
            }
            applyCount++;
        }
    }

    public BigInteger getPrice();
    public Collection<String> getSkus();
}
