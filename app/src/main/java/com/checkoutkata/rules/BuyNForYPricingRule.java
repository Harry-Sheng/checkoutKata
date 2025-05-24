package com.checkoutkata.rules;

public class BuyNForYPricingRule implements PricingRule{
    private int unitPrice;
    private int bundleSize;
    private int bundlePrice;

    public BuyNForYPricingRule(int unitPrice, int bundleSize, int bundlePrice) {
        this.unitPrice = unitPrice;
        this.bundleSize = bundleSize;
        this.bundlePrice = bundlePrice;
    }

    @Override
    public int calculatePrice(int quantity) {
        int totalPrice = 0;

        // Calculate the number of bundles and the remaining items
        int bundles = quantity / bundleSize;
        int remainingItems = quantity % bundleSize;

        // Calculate the total price for the bundles
        totalPrice += bundles * bundlePrice;

        // Calculate the total price for the remaining items
        totalPrice += remainingItems * unitPrice;

        return totalPrice;
    }
}
