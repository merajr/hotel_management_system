package com.meraj.spring.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meraj.spring.domain.Guest;
import com.meraj.spring.domain.IReservedRoom;
import com.meraj.spring.domain.Reservation;
import com.meraj.spring.domain.RoomFactory;
import com.meraj.util.HotelData;
import com.meraj.util.RoomType;

@Service
public class ReservationService {

    @Autowired
    RoomsService roomService;

    @Autowired
    GuestsService guestService;

    public Optional<Reservation> addReservation(RoomType type, String name, String ccNo, LocalDate date, int noOfDays) {
        String roomNo = roomService.getRoomNoAvailableForDays(type, date, noOfDays);
        if (!roomNo.equals("")) {
            Optional<Guest> guest = guestService.registerGuest(name, ccNo);

            IReservedRoom resRoom = RoomFactory.getReservedRoom(HotelData.rooms.get(type), roomNo);
            System.out.println("So your room's per day cost is: $" + resRoom.getDayCost());

            double totalCost = noOfDays * resRoom.getDayCost();
            Reservation reservation = new Reservation(resRoom, date, date.plusDays(noOfDays), guest.get(), totalCost);
            saveReservation(reservation);
            return Optional.ofNullable(reservation);
        } else {
            System.out.println("No " + type + " room is available anymore.");
            return Optional.empty();
        }
    }

    private void saveReservation(Reservation reservation) {
        LocalDate date = reservation.getDateCheckin();
        while (date.isBefore(reservation.getDateCheckout())) {
            if (HotelData.liveReservations.containsKey(date)) {
                List<Reservation> reservations = HotelData.liveReservations.get(date);
                reservations.add(reservation);
            } else {
                List<Reservation> reservations = new ArrayList<>();
                reservations.add(reservation);
                HotelData.liveReservations.put(date, reservations);
            }
            date = date.plusDays(1);
        }
    }

    public void checkout(String roomNo, LocalDate checkinDate) {
        LocalDate now = LocalDate.now();
        now.minusDays(1);
        boolean ok = true;
        while (!now.isBefore(checkinDate)) {

            List<Reservation> reservations = HotelData.liveReservations.get(now);
            long count = reservations.stream().filter((x) -> x.getRoom().getRoomNo().equals(roomNo)).count();
            if (count == 0) {
                System.out.println("Reservation is not found for " + now);
                ok = false;
                break;
            } else {
                Reservation reservation =
                    reservations.stream().filter((x) -> x.getRoom().getRoomNo().equals(roomNo)).findFirst().get();
                reservations.remove(reservation);

                if (HotelData.reservationsHistory.containsKey(now)) {
                    List<Reservation> reservationsGone = HotelData.reservationsHistory.get(now);
                    reservationsGone.add(reservation);
                } else {
                    List<Reservation> reservationsGone = new ArrayList<>();
                    reservationsGone.add(reservation);
                    HotelData.reservationsHistory.put(now, reservationsGone);
                }
            }
            now = now.minusDays(1);
        }
        if (ok) {
            System.out.println("Checkout is completed. Thanks for your stay!");
        }
    }
}
