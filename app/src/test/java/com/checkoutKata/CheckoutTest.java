package com.checkoutKata;
import com.checkoutkata.CheckoutEngine;
import com.checkoutkata.rules.BuyNForYPricingRule;
import com.checkoutkata.rules.PricingRule;
import com.checkoutkata.rules.RegularPricingRule;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

import java.util.HashMap;
import java.util.Map;

public class CheckoutTest {

    private Map<Character, PricingRule> pricingRules;

    @BeforeEach
    public void setUp() {
        pricingRules = new HashMap<>();
        pricingRules.put('A', new BuyNForYPricingRule(50, 3, 130));
        pricingRules.put('B', new BuyNForYPricingRule(30, 2, 45));
        pricingRules.put('C', new RegularPricingRule(20));
        pricingRules.put('D', new RegularPricingRule(15));
    }

    private int calculatePrice(String items) {
        CheckoutEngine checkoutEngine = new CheckoutEngine(pricingRules);
        checkoutEngine.scan(items);
        return checkoutEngine.getTotal();
    }

    @Test
    public void testTotalForZeroItem() {
        assertEquals(0, calculatePrice(""));
    }

    @Test
    public void testTotalForOneItemWithRegularPricingRule() {
        assertEquals(20, calculatePrice("C"));
    }

    @Test
    public void testTotalForTwoItemWithRegularPricingRule() {
        assertEquals(35, calculatePrice("CD")); // 20 + 15
    }

    @Test
    public void testTotalForTwoItemWithBuyXForYPricingRule() {
        assertEquals(45, calculatePrice("BB"));
    }

    @Test
    public void testTotalForThreeItemsWithBuyXForYPricingRule() {
        assertEquals(130, calculatePrice("AAA")); 
    }

    @Test
    public void testTotalForMultipleItemsNoDiscounts() {
        assertEquals(80, calculatePrice("AB")); // 50 + 30
        assertEquals(115, calculatePrice("CDBA")); // 20 + 15 + 30 + 50
    }

    @Test
    public void testTotalForMultipleItemsWithDiscounts() {
        assertEquals(180, calculatePrice("AAAA")); // 130 + 50
        assertEquals(230, calculatePrice("AAAAA")); // 130 + 50 + 50
        assertEquals(260, calculatePrice("AAAAAA")); // 130 + 130
    }

    @Test
    public void testTotalForComplexMixedItems() {
        assertEquals(160, calculatePrice("AAAB")); // 130 + 30
        assertEquals(175, calculatePrice("AAABB")); // 130 + 45
        assertEquals(190, calculatePrice("AAABBD")); // 130 + 45 + 15
        assertEquals(190, calculatePrice("DABABA")); // 15 + 130 + 45
    }

    @Test
    public void testIncrementalTotalCalculation() {
        CheckoutEngine checkoutEngine = new CheckoutEngine(pricingRules);

        assertEquals(0, checkoutEngine.getTotal());
        checkoutEngine.scan("A"); assertEquals(50, checkoutEngine.getTotal()); // 50
        checkoutEngine.scan("B"); assertEquals(80, checkoutEngine.getTotal()); // 50 + 30
        checkoutEngine.scan("A"); assertEquals(130, checkoutEngine.getTotal()); // 50 + 30 + 50
        checkoutEngine.scan("A"); assertEquals(160, checkoutEngine.getTotal()); // triggers A discount 130 + 30
        checkoutEngine.scan("B"); assertEquals(175, checkoutEngine.getTotal()); // triggers B discount 130 + 45
        checkoutEngine.scan("C"); assertEquals(195, checkoutEngine.getTotal()); // 130 + 45 + 20
    }

}
