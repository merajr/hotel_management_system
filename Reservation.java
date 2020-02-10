package com.meraj.spring.domain;

import java.time.LocalDate;

public class Reservation {

    IReservedRoom room;

    LocalDate dateCheckin;

    LocalDate dateCheckout;

    Guest guest;

    double totalCost;

    public Reservation(IReservedRoom room, LocalDate dateCheckin, LocalDate dateCheckout, Guest guest,
        double totalCost) {
        this.room = room;
        this.dateCheckin = dateCheckin;
        this.dateCheckout = dateCheckout;
        this.guest = guest;
        this.totalCost = totalCost;
    }

    public IReservedRoom getRoom() {
        return room;
    }

    public LocalDate getDateCheckin() {
        return dateCheckin;
    }

    public LocalDate getDateCheckout() {
        return dateCheckout;
    }

    public Guest getGuest() {
        return guest;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setRoom(IReservedRoom room) {
        this.room = room;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    @Override
    public String toString() {
        return "Reservation [room=" + room + ", dateCheckin=" + dateCheckin + ", dateCheckout=" + dateCheckout
            + ", guest=" + guest + ", totalCost=" + totalCost + "]";
    }

}
