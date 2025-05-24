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

    private final Map<String, PricingRule> itemCodeToPricingRuleMap;
    private final Map<String, Integer> itemCodeToQuantityMap;

    /**
     * Constructor for CheckoutEngine.
     *
     * @param pricingRules a map where the key is the item code
     *                     and the value is the pricing rule to apply.
     */
    public CheckoutEngine(Map<String, PricingRule> itemCodeToPricingRuleMap) {
        this.itemCodeToQuantityMap = new HashMap<>();
        this.itemCodeToPricingRuleMap = itemCodeToPricingRuleMap;
    }

    /**
     * Scans one item, updating the internal count of the item.
     *
     * @param itemCode a string represent an item to scan.
     */
    public void scan(String itemCode) {
        if (itemCode == null || itemCode.isEmpty()) {
            return;
        }

        if (!itemCodeToPricingRuleMap.containsKey(itemCode)) {
            throw new IllegalArgumentException("Item code" + itemCode + " is not recognised.");
        }
        
        itemCodeToQuantityMap.put(itemCode, itemCodeToQuantityMap.getOrDefault(itemCode, 0) + 1);
    }

    /**
     * Calculates the total price for all scanned items using their respective pricing rules.
     * 
     * @return The total checkout price.
     */
    public int getTotal() {
        int total = 0;

        for (Map.Entry<String, Integer> entry : itemCodeToQuantityMap.entrySet()) {
            String itemCode = entry.getKey();
            int quantity = entry.getValue();
            PricingRule rule = itemCodeToPricingRuleMap.get(itemCode);
            total += rule.calculatePrice(quantity);
        }

        return total;
    }

}
