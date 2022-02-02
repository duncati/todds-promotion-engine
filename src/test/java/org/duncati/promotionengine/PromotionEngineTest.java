package org.duncati.promotionengine;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;

import java.math.BigInteger;
import java.util.*;

public class PromotionEngineTest {


    @BeforeAll
    static void initializePrices() {
        System.out.println("Initializing prices");
        Map<String, BigInteger> prices=new HashMap<>();
        prices.put("A", BigInteger.valueOf(50));
        prices.put("B", BigInteger.valueOf(30));
        prices.put("C", BigInteger.valueOf(20));
        prices.put("D", BigInteger.valueOf(15));
        Cart.setPrices(new InMemoryPriceRepository(prices));
    }

    @BeforeAll
    static void initializePromotions() {
        System.out.println("Initializing promotions");
        InMemoryPromotionRepository promotions=new InMemoryPromotionRepository();
        promotions.addPromotion(new Promotion(Arrays.asList("A", "A", "A"), BigInteger.valueOf(130)));
        promotions.addPromotion(new Promotion(Arrays.asList("B", "B"), BigInteger.valueOf(45)));
        promotions.addPromotion(new Promotion(Arrays.asList("C", "D"), BigInteger.valueOf(30)));
        Cart.setPromotions(promotions);
    }

    @Test
    @DisplayName("Test of Scenario A (no promotions)")
    void testScenarioA() {
        ICart cart=new Cart();
        cart.addItem(("A"));
        cart.addItem(("B"));
        cart.addItem(("C"));
        Assertions.assertEquals(BigInteger.valueOf(100), cart.getTotal());
    }

    @Test
    @DisplayName("Test of Scenario B (3 promotions)")
    void testScenarioB() {
        ICart cart=new Cart();
        cart.addItem("A", 5);
        cart.addItem("B", 5);
        cart.addItem("C");
        Assertions.assertEquals(BigInteger.valueOf(370), cart.getTotal());
    }

    @Test
    @DisplayName("Test of Scenario C (4 promotions, including a multiSku promotion)")
    void testScenarioC() {
        ICart cart=new Cart();
        cart.addItem("A", 3);
        cart.addItem("B", 5);
        cart.addItem("C");
        cart.addItem("D");
        Assertions.assertEquals(BigInteger.valueOf(280), cart.getTotal());
    }
}