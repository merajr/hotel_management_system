package com.meraj.spring.domain;

import java.util.List;

import com.meraj.util.HotelData;
import com.meraj.util.RoomFeatures;

public enum RoomFactory {

        // singleton instance
        INSTANCE;

    public static IReservedRoom getReservedRoom(IRoomDef roomDef, String roomNo) {
        ReservedRoom room = new ReservedRoom(roomDef, roomNo);
        List<RoomFeatures> features = HotelData.roomFeatures.get(roomDef.getRoomType());
        if (features != null) {
            room.getFeatures().addAll(features);
        }
        return room;
    }

    public static ReservedRoomFeatured getReservedRoomWithAddonFeature(IReservedRoom room, RoomFeatures feature) {
        ReservedRoomFeatured newRoom = new ReservedRoomFeatured(room, feature);
        return newRoom;
    }
}
