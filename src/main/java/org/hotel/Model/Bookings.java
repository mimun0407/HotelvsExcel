package org.hotel.Model;

import java.util.Date;

public class Bookings {
    private int idBooking;
    private Date date;
    private Room room;
    private Customer customer;
    private HotelSer hotelSer;

    public Bookings() {
    }

    public Bookings(int idBooking, Date date, Room room, Customer customer, HotelSer hotelSer) {
        this.idBooking = idBooking;
        this.date = date;
        this.room = room;
        this.customer = customer;
        this.hotelSer = hotelSer;
    }

    public int getIdBooking() {
        return idBooking;
    }

    public void setIdBooking(int idBooking) {
        this.idBooking = idBooking;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public HotelSer getHotelSer() {
        return hotelSer;
    }

    public void setHotelSer(HotelSer hotelSer) {
        this.hotelSer = hotelSer;
    }
}