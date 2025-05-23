package com.checkoutkata;

public class RegularPricingRule {
    private int unitPrice;

    public RegularPricingRule(int unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int calculatePrice(int quantity) {
        return unitPrice * quantity;
    }

}
