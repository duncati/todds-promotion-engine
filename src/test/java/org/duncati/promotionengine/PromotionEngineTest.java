package org.duncati.promotionengine;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;

import java.math.BigInteger;
import java.util.*;

public class PromotionEngineTest {

    @BeforeAll
    static void initializeRepository() {
        System.out.println("Initializing repository");
        RepositoryFactory.INSTANCE.setRepositoryType(RepositoryType.IN_MEMORY);
        IRepository repository=RepositoryFactory.INSTANCE.getRepository();
        repository.setPrice("A", BigInteger.valueOf(50));
        repository.setPrice("B", BigInteger.valueOf(30));
        repository.setPrice("C", BigInteger.valueOf(20));
        repository.setPrice("D", BigInteger.valueOf(15));
        repository.addPromotion(new BundlePromotion(new Items(Arrays.asList("A", "A", "A")), BigInteger.valueOf(130)));
        repository.addPromotion(new BundlePromotion(new Items(Arrays.asList("B", "B")), BigInteger.valueOf(45)));
        repository.addPromotion(new BundlePromotion(new Items(Arrays.asList("C", "D")), BigInteger.valueOf(30)));
    }

    @Test
    @DisplayName("Test of Scenario A (no promotions)")
    void testScenarioA() {
        ICart cart=new Cart();
        cart.addItem("A");
        cart.addItem("B");
        cart.addItem("C");
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