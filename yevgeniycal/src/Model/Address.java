/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.Date;

/**
 *
 * @author yevgeniy
 */
public class Address {

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    int addressId;
    String address;
    String address2;
    City city;
    String postalCode;
    String phone;


    /**
     *
     * @param addressId
     * @param address
     * @param address2
     * @param city
     * @param postalCode
     * @param phone
     */
    public Address(int addressId, String address, String address2, City city, String postalCode, String phone) {
        this.addressId = addressId;
        this.address = address;
        this.address2 = address2;
        this.city = city;
        this.postalCode = postalCode;
        this.phone = phone;
    }
    
    @Override
    public String toString(){
        if (address2 != null && !address2.isEmpty()) {
            return phone + "\n" + address + "\n" + address2 + "\n" + city + ", " + postalCode;
        }
        else {
            return phone + "\n" + address + "\n" +  city + ", " +  postalCode;
        }
    }
}
