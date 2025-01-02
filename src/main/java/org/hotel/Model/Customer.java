package org.hotel.Model;

public class Customer {
    private int idCustomer;
    private String CustomerName;

    public Customer(int idCustomer, String customerName) {
        this.idCustomer = idCustomer;
        CustomerName = customerName;
    }

    public Customer() {
    }

    public int getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(int idCustomer) {
        this.idCustomer = idCustomer;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }
    public void info(){
        System.out.println(idCustomer+" "+CustomerName);
    }
}
