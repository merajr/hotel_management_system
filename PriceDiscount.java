package com.meraj.spring.domain.strategy;

public class PriceDiscount implements DiscountStrategy {

    double discount;

    public PriceDiscount(double discountPrice) {
        discount = discountPrice;
    }

    @Override
    public double applyDiscount(double price) {
        if (discount > price) {
            System.out.println("Discount can't be applied, since room's price is too low.");
            return price;
        } else {
            return price - discount;
        }
    }

}
