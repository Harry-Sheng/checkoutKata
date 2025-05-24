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
    public void testTotalPriceOfZeroItemWithRegularPricingRule() {
        assertEquals(0, calculatePrice(""));
    }

    @Test
    public void testTotalPriceOfOneItemWithRegularPricingRule() {
        assertEquals(20, calculatePrice("C"));
    }

    @Test
    public void testTotalPriceOfTwoItemWithRegularPricingRule() {
        assertEquals(35, calculatePrice("CD"));
    }

    @Test
    public void testTotalPriceOfTwoItemWithBuyXForYPricingRule() {
        assertEquals(45, calculatePrice("BB"));
    }

    @Test
    public void testTotalPriceOfThreeItemWithBuyXForYPricingRule() {
        assertEquals(130, calculatePrice("AAA"));
    }

}
