package org.duncati.promotionengine;

import java.math.BigInteger;

public interface ICart {

    void addItem(String sku);
    void addItem(String sku, int quantity);
    void removeItem(String sku);
    void removeItem(String sku, int quantity);
    BigInteger getTotal();
}
