package org.hotel.Model;

public class Room {
    private int idRoom;
    private String roomName;
    private boolean booked;
    private int price;

    public Room(int idRoom, String roomName, boolean booked, int price) {
        this.idRoom = idRoom;
        this.roomName = roomName;
        this.booked = booked;
        this.price = price;
    }

    public Room() {
    }

    public int getIdRoom() {
        return idRoom;
    }

    public void setIdRoom(int idRoom) {
        this.idRoom = idRoom;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public boolean isBooked() {
        return booked;
    }

    public void setBooked(boolean booked) {
        this.booked = booked;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void info(){

        System.out.println(idRoom+", "+ roomName);
        System.out.println(price+" $");
        if(booked){
            System.out.println("This room is already booked");
        }
        else{
            System.out.println("This room is not booked ");
        }
        System.out.println("------------------------------------------------------------------");
    }
}
