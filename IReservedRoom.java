package com.meraj.spring.domain;

import java.util.List;

import com.meraj.util.RoomFeatures;

public interface IReservedRoom {

    public IRoomDef getRoomDef();

    public String getRoomNo();
    
    public List<RoomFeatures> getFeatures();
    
    public double getDayCost();
}
