package com.meraj.spring.domain;

import java.util.List;

import com.meraj.util.HotelData;
import com.meraj.util.RoomFeatures;

/*
 * Using decorator pattern, missing features can be added to a room.
 */
public class ReservedRoomFeatured extends ReservedRoom {

    private IReservedRoom room;

    private RoomFeatures feature;

    public ReservedRoomFeatured(IReservedRoom reservedRoom, RoomFeatures roomFeature) {
        super(reservedRoom.getRoomDef(), reservedRoom.getRoomNo());
        room = reservedRoom;
        feature = roomFeature;
        room.getFeatures().add(roomFeature);
    }

    @Override
    public IRoomDef getRoomDef() {
        return room.getRoomDef();
    }

    @Override
    public String getRoomNo() {
        return room.getRoomNo();
    }

    @Override
    public List<RoomFeatures> getFeatures() {
        return room.getFeatures();
    }

    @Override
    public double getDayCost() {
        double cost = room.getDayCost() + HotelData.featuresCosts.get(feature);
        return cost;
    }
}
