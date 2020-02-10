package com.meraj.spring.domain;

import java.util.ArrayList;
import java.util.List;

import com.meraj.util.HotelData;
import com.meraj.util.RoomFeatures;

public class ReservedRoom implements IReservedRoom {

    private IRoomDef roomDef;

    private String roomNo;

    private List<RoomFeatures> features;

    protected ReservedRoom(IRoomDef roomDef, String roomNo) {
        this.roomDef = roomDef;
        this.roomNo = roomNo;
        features = new ArrayList<>();
    }

    public IRoomDef getRoomDef() {
        return roomDef;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public List<RoomFeatures> getFeatures() {
        return features;
    }

    public double getDayCost() {
        double cost = HotelData.rooms.get(roomDef.getRoomType()).getPrice();
        return cost;
    }

    @Override
    public String toString() {
        return "ReservedRoom [roomDef=" + roomDef + ", roomNo=" + roomNo + ", features included=" + features + "]";
    }

}
