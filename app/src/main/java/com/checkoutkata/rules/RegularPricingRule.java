package com.checkoutkata.rules;

public class RegularPricingRule implements PricingRule {

    private final int unitPrice;

    public RegularPricingRule(int unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public int calculatePrice(int quantity) {
        return unitPrice * quantity;
    }
}
