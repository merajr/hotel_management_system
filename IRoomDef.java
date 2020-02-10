package com.meraj.spring.domain;

import com.meraj.spring.domain.strategy.DiscountStrategy;
import com.meraj.util.RoomType;

public interface IRoomDef {

    double getPrice();

    RoomType getRoomType();

    void applyDiscount(DiscountStrategy strategy);

}
