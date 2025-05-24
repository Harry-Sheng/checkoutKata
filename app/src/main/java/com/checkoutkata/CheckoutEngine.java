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

    private final Map<String, PricingRule> itemToPricingRuleMap;
    private final Map<String, Integer> itemToQuantityMap;

    /**
     * Constructor for CheckoutEngine.
     *
     * @param pricingRules a map where the key is the item code
     *                     and the value is the pricing rule to apply.
     */
    public CheckoutEngine(Map<String, PricingRule> itemToPricingRuleMap) {
        this.itemToQuantityMap = new HashMap<>();
        this.itemToPricingRuleMap = itemToPricingRuleMap;
    }

    /**
     * Scans one item, updating the internal count of the item.
     *
     * @param item a string represent an item to scan.
     */
    public void scan(String item) {
        if (item == null || item.isEmpty()) {
            return;
        }

        if (!itemToPricingRuleMap.containsKey(item)) {
            throw new IllegalArgumentException("Item " + item + " is not recognised.");
        }
        
        itemToQuantityMap.put(item, itemToQuantityMap.getOrDefault(item, 0) + 1);
    }

    /**
     * Calculates the total price for all scanned items using their respective pricing rules.
     * 
     * @return The total checkout price.
     */
    public int getTotal() {
        int total = 0;

        for (Map.Entry<String, Integer> entry : itemToQuantityMap.entrySet()) {
            String item = entry.getKey();
            int quantity = entry.getValue();
            PricingRule rule = itemToPricingRuleMap.get(item);
            total += rule.calculatePrice(quantity);
        }

        return total;
    }

}
