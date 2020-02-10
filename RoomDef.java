package com.meraj.spring.domain;

import com.meraj.spring.domain.strategy.DiscountStrategy;
import com.meraj.util.RoomType;

public class RoomDef implements IRoomDef {

    double price;

    RoomType type;

    public RoomDef(double price, RoomType type) {
        this.price = price;
        this.type = type;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public RoomType getRoomType() {
        return type;
    }

    @Override
    public void applyDiscount(DiscountStrategy strategy) {
        price = strategy.applyDiscount(price);
    }

    @Override
    public String toString() {
        return "Room [price=" + price + "$, type=" + type + "]";
    }

}
