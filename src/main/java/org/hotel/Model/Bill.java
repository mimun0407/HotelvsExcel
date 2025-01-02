package org.hotel.Model;

import java.util.Date;

public class Bill {
    private int idBill;
    private  Room room;
    private int price;
    private Date date;
    private Customer customer;

    public Bill() {
    }

    public Bill(int idBill, Room room, int price, Date date, Customer customer) {
        this.idBill = idBill;
        this.room = room;
        this.price = price;
        this.date = date;
        this.customer = customer;
    }

    public int getIdBill() {
        return idBill;
    }

    public void setIdBill(int idBill) {
        this.idBill = idBill;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
