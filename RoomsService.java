package com.meraj.spring.services;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.meraj.spring.domain.IRoomDef;
import com.meraj.spring.domain.Reservation;
import com.meraj.spring.domain.strategy.DiscountStrategy;
import com.meraj.spring.domain.strategy.PercentageDiscount;
import com.meraj.spring.domain.strategy.PriceDiscount;
import com.meraj.util.HotelData;
import com.meraj.util.RoomType;

@Service
public class RoomsService {

    public Optional<IRoomDef> getAvailableRoom(RoomType type, LocalDate date) {
        int countTotal = HotelData.roomsCount.get(type);
        Optional<IRoomDef> room = Optional.empty();
        if (countTotal > 0) {

            if (HotelData.liveReservations.containsKey(date)) {
                List<Reservation> reservations = HotelData.liveReservations.get(date);
                long count =
                    reservations.stream().filter((p) -> p.getRoom().getRoomDef().getRoomType().equals(type)).count();
                if (count < countTotal) {
                    room = Optional.ofNullable(HotelData.rooms.get(type));
                }
            } else {
                room = Optional.ofNullable(HotelData.rooms.get(type));
            }
        }

        return room;
    }

    public String getRoomNoAvailableForDays(RoomType type, LocalDate date, int noOfDays) {
        int countTotal = HotelData.roomsCount.get(type);
        int maxNo = Integer.MIN_VALUE;
        if (countTotal > 0) {
            LocalDate endDate = date.plusDays(noOfDays);
            if (HotelData.liveReservations.size() > 0) {
                while (date.isBefore(endDate)) {
                    if (HotelData.liveReservations.containsKey(date)) {
                        List<Reservation> reservations = HotelData.liveReservations.get(date);
                        int count = (int) reservations.stream()
                            .filter((p) -> p.getRoom().getRoomDef().getRoomType().equals(type)).count();
                        if (count < countTotal) {
                            if (count > maxNo) {
                                maxNo = count;
                            }
                        } else {
                            return "";
                        }
                    }
                    date = date.plusDays(1);
                }

            } else {
                return type.toString() + countTotal;
            }
        } else {
            return "";
        }
        return type.toString() + (countTotal - maxNo);
    }

    public void changePrice(double price) {
        PriceDiscount discount = new PriceDiscount(price);
        changePriceAll(discount);
    }

    public void changePricePercentage(int percentage) {
        PercentageDiscount discount = new PercentageDiscount(percentage);
        changePriceAll(discount);
    }

    private void changePriceAll(DiscountStrategy strategy) {
        Collection<IRoomDef> rooms = HotelData.rooms.values();
        for (IRoomDef room : rooms) {
            room.applyDiscount(strategy);
        }
    }

}
