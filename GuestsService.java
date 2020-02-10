package com.meraj.spring.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.meraj.spring.domain.Guest;
import com.meraj.util.HotelData;

@Service
public class GuestsService {

    public Optional<Guest> registerGuest(String name, String ccNo) {
        Guest guest = new Guest(name, ccNo);
        updateGuestData(guest);
        return Optional.ofNullable(guest);
    }

    private void updateGuestData(Guest g) {
        boolean found = HotelData.guests.stream().anyMatch((g1) -> g1.equals(g));
        if (!found) {
            HotelData.guests.add(g);
        }
    }
}
