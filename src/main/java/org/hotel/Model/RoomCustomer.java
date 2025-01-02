package org.hotel.Model;

public class RoomCustomer {
    private int id;
    private Customer customer;
    private Room room;

    public RoomCustomer(Customer customer, int id, Room room) {
        this.customer = customer;
        this.id = id;
        this.room = room;
    }

    public RoomCustomer() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

}