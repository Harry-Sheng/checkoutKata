package com.checkoutkata;

import java.util.HashMap;
import java.util.Map;

import com.checkoutkata.rules.PricingRule;

/**
 * CheckoutEngine calculates the total price of scanned items based on
 * configured pricing rules.
 * 
 * It maintains an internal count of scanned items and applies the appropriate
 * PricingRule for each item type.
 */
public class CheckoutEngine {

    private final Map<Character, PricingRule> pricingRules;
    private final Map<Character, Integer> itemToQuantityMap;

    /**
     * Constructor for CheckoutEngine.
     *
     * @param pricingRules a map where the key is the item code
     *                     and the value is the pricing rule to apply.
     */
    public CheckoutEngine(Map<Character, PricingRule> pricingRules) {
        this.itemToQuantityMap = new HashMap<>();
        this.pricingRules = pricingRules;
    }

    /**
     * Scans a string of items, updating the internal count of each item.
     *
     * @param items a string where each character represent an item to scan.
     */
    public void scan(String items) {
        if (items == null || items.isEmpty()) {
            return;
        }

        for (char item : items.toCharArray()) {
            itemToQuantityMap.put(item, itemToQuantityMap.getOrDefault(item, 0) + 1);
        }
    }

    /**
     * Calculates the total price for all scanned items using their respective pricing rules.
     * 
     * @return The total checkout price.
     */
    public int getTotal() {
        int total = 0;

        for (Map.Entry<Character, Integer> entry : itemToQuantityMap.entrySet()) {
            Character item = entry.getKey();
            int quantity = entry.getValue();
            PricingRule rule = pricingRules.get(item);
            total += rule.calculatePrice(quantity);
        }

        return total;
    }

}
