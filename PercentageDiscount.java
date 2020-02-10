package com.meraj.spring.domain.strategy;

public class PercentageDiscount implements DiscountStrategy {

    private int percent;

    public PercentageDiscount(int percentage) {
        percent = percentage;
    }

    @Override
    public double applyDiscount(double price) {
        return price * (100 - percent) / 100;
    }

}
