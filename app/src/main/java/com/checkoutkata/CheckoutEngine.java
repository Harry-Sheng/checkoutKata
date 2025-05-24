package com.checkoutkata;

import java.util.HashMap;
import java.util.Map;

import com.checkoutkata.rules.PricingRule;

public class CheckoutEngine {
    private Map<Character, PricingRule> pricingRules;
    private Map<Character, Integer> itemToQuantityMap;

    public CheckoutEngine(Map<Character, PricingRule> pricingRules) {
        this.itemToQuantityMap = new HashMap<>();
        this.pricingRules = pricingRules;
    }

    public void scan(String items) {
        if (items == null || items.isEmpty()) {
            return;
        }

        for (char item : items.toCharArray()) {
            itemToQuantityMap.put(item, itemToQuantityMap.getOrDefault(item, 0) + 1);
        }
    }

    public int getTotal() {
        int total = 0;

        for (Character item : itemToQuantityMap.keySet()) {
            int quantity = itemToQuantityMap.get(item);
            PricingRule rule = pricingRules.get(item);
            total += rule.calculatePrice(quantity);
        }

        return total;
    }

}
