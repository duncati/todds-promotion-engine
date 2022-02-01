package org.duncati.promotionengine.test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PromotionEngineTest {

    @BeforeAll
    static void setPrices() {
        Map<String, BigInteger> prices=new HashMap<>();
        prices.put("A", BigInteger.valueOf(50));
        prices.put("B", BigInteger.valueOf(30));
        prices.put("C", BigInteger.valueOf(20));
        prices.put("D", BigInteger.valueOf(15));
    }

    @BeforeAll
    static void setPromotions() {
        Map<List<String>, BigInteger> promotions=new HashMap<>();
        promotions.put(Arrays.asList("A", "A", "A"), BigInteger.valueOf(130));
        promotions.put(Arrays.asList("B", "B"), BigInteger.valueOf(45));
        promotions.put(Arrays.asList("C", "D"), BigInteger.valueOf(30));
    }

    @Test
    @DisplayName("Test of Scenario A (no promotions)")
    static void testScenarioA() {
        Cart cart=new Cart();
        cart.addItem(("A"));
        cart.addItem(("B"));
        cart.addItem(("C"));
        Assertions.assertEquals(cart.getTotal(), (BigInteger.valueOf(100)));
    }

    @Test
    @DisplayName("Test of Scenario B (3 promotions)")
    static void testScenarioB() {
        Cart cart=new Cart();
        cart.addItem("A", 5);
        cart.addItem("B", 5);
        cart.addItem("C");
        Assertions.assertEquals(cart.getTotal(), (BigInteger.valueOf(370)));
    }

    @Test
    @DisplayName("Test of Scenario C (4 promotions, including a multiSku promotion)")
    static void testScenarioC() {
        Cart cart=new Cart();
        cart.addItem("A", 3);
        cart.addItem("B", 5);
        cart.addItem("C");
        cart.addItem("D");
        Assertions.assertEquals(cart.getTotal(), (BigInteger.valueOf(280)));
    }
}