package org.hotel.Model;

public class HotelSer {
    private int ser_id;
    private String ser_name;
    private int ser_price;

    public HotelSer(int ser_id, int ser_price, String ser_name) {
        this.ser_id = ser_id;
        this.ser_price = ser_price;
        this.ser_name = ser_name;
    }

    public HotelSer() {
    }

    public int getSer_id() {
        return ser_id;
    }

    public void setSer_id(int ser_id) {
        this.ser_id = ser_id;
    }

    public String getSer_name() {
        return ser_name;
    }

    public void setSer_name(String ser_name) {
        this.ser_name = ser_name;
    }

    public int getSer_price() {
        return ser_price;
    }

    public void setSer_price(int ser_price) {
        this.ser_price = ser_price;
    }
    public void info(){
        System.out.println(ser_id+", "+ser_name);
        System.out.println(ser_price+" $");
        System.out.println("--------------------------------------------");
    }
}
