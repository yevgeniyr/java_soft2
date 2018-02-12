/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;

/**
 *
 * @author yevgeniy
 */
public class Customer {

    public Customer() {
       
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String toString() {
        return getCustomerName();
    }
    
    public void setAppointments(ArrayList<Appointment> appointments) {
        this.appointments = appointments;
    }
    
    public ArrayList<Appointment> getAppointments() {
        return this.appointments;
    }
    
    public Customer(int customerId, String customerName, int active, Address address) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.active = active;
        this.address = address;
        this.appointments = appointments;
    }
    
    int customerId;
    String customerName;
    int active;
    int addressId;
    Address address;
    ArrayList<Appointment> appointments;
}
