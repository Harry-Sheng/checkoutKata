package com.checkoutkata.rules;

public class BuyNForYPricingRule implements PricingRule {

    private final int unitPrice;
    private final int bundleSize;
    private final int bundlePrice;

    public BuyNForYPricingRule(int unitPrice, int bundleSize, int bundlePrice) {
        this.unitPrice = unitPrice;
        this.bundleSize = bundleSize;
        this.bundlePrice = bundlePrice;
    }

    @Override
    public int calculatePrice(int quantity) {
        int bundles = quantity / bundleSize;
        int remainingItems = quantity % bundleSize;

        int totalPrice = (bundles * bundlePrice) + (remainingItems * unitPrice);

        return totalPrice;
    }
}
