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

    private Map<String, PricingRule> itemToPricingRuleMap;

    @BeforeEach
    public void setUp() {
        itemToPricingRuleMap = new HashMap<>();
        itemToPricingRuleMap.put("A", new BuyNForYPricingRule(50, 3, 130));
        itemToPricingRuleMap.put("B", new BuyNForYPricingRule(30, 2, 45));
        itemToPricingRuleMap.put("C", new RegularPricingRule(20));
        itemToPricingRuleMap.put("D", new RegularPricingRule(15));
    }

    /**
     * Calculates the total price for a given sequence of items using a string.
     * It uses the CheckoutEngine to scan each item and apply the pricing rules.
     *
     * @param items A string representing the items to be scanned. e.g "ABCD"
     * @return The total price calculated based on the pricing rules.
     */
    private int calculatePrice(String items) {
        CheckoutEngine checkoutEngine = new CheckoutEngine(itemToPricingRuleMap);

        for (char item : items.toCharArray()) {
            checkoutEngine.scan(String.valueOf(item));
        }

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
    public void testTotalForOneItemNoDiscounts() {
        assertEquals(50, calculatePrice("A"));
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
        CheckoutEngine checkoutEngine = new CheckoutEngine(itemToPricingRuleMap);

        assertEquals(0, checkoutEngine.getTotal());
        checkoutEngine.scan("A"); assertEquals(50, checkoutEngine.getTotal()); // 50
        checkoutEngine.scan("B"); assertEquals(80, checkoutEngine.getTotal()); // 50 + 30
        checkoutEngine.scan("A"); assertEquals(130, checkoutEngine.getTotal()); // 50 + 30 + 50
        checkoutEngine.scan("A"); assertEquals(160, checkoutEngine.getTotal()); // triggers A discount 130 + 30
        checkoutEngine.scan("B"); assertEquals(175, checkoutEngine.getTotal()); // triggers B discount 130 + 45
        checkoutEngine.scan("C"); assertEquals(195, checkoutEngine.getTotal()); // 130 + 45 + 20
    }

    @Test
    public void testScanNothingThenAddItem() {
        CheckoutEngine checkoutEngine = new CheckoutEngine(itemToPricingRuleMap);
        assertEquals(0, checkoutEngine.getTotal());
        checkoutEngine.scan("A");
        assertEquals(50, checkoutEngine.getTotal());
    }

    @Test
    public void testInvalidItemThrowsError() {
        CheckoutEngine checkoutEngine = new CheckoutEngine(itemToPricingRuleMap);
        assertThrows(IllegalArgumentException.class, () -> {
            checkoutEngine.scan("1");  // invalid
        });
    }

    @Test
    public void testItemWithoutRuleThrowsError() {
        CheckoutEngine checkoutEngine = new CheckoutEngine(itemToPricingRuleMap);
        assertThrows(IllegalArgumentException.class, () -> {
            checkoutEngine.scan("E");  // invalid
        });
        assertThrows(IllegalArgumentException.class, () -> {
            checkoutEngine.scan("a");  // invalid (should be case-sensitive)
        });
    }

    @Test
    public void testValidAndInvalidMixThrowsError() {
        CheckoutEngine checkoutEngine = new CheckoutEngine(itemToPricingRuleMap);
        checkoutEngine.scan("A"); assertEquals(50, checkoutEngine.getTotal()); //vaild
        assertThrows(IllegalArgumentException.class, () -> {
            checkoutEngine.scan("1");  // invalid
        });
        checkoutEngine.scan("B"); assertEquals(80, checkoutEngine.getTotal()); // vaild
    }
}
