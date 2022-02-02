package org.duncati.promotionengine;

import java.math.BigInteger;

public interface ICart {

    public void addItem(String sku);
    public void addItem(String sku, int quantity);
    public void removeItem(String sku);
    public void removeItem(String sku, int quantity);
    public BigInteger getTotal();
}
