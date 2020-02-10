package com.meraj.spring.domain.strategy;

public class NoDiscount implements DiscountStrategy {

    public NoDiscount() {}

    @Override
    public double applyDiscount(double price) {
        return price;
    }

}
