package com.checkoutkata.rules;

public interface PricingRule {
    
    /**
     * Calculates the total price for a given quantity.
     *
     * @param quantity the number of items
     * @return the total price
     */
    int calculatePrice(int quantity);
} 
