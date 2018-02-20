/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;


/**
 *
 * @author yevgeniy
 */
public class Appointment {

    public Appointment(
            Customer customer,
            int appointmentId,
            String title,
            String description,
            String location,
            String contact,
            String url,
            ZonedDateTime startDate,
            ZonedDateTime endDate
    ) {
        this.customer = customer;
        this.appointmentId = appointmentId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contact = contact;
        this.url = url;
        this.startDate = startDate;
        this.endDate = endDate;
        //this.startDateStr =  DateFormat.getDateTimeInstance().format(startDate);
        //System.out.println("HERE HERE" + startDateStr);
    }


    @Override
    public boolean equals(Object o) {

        if (o == this) return true;
        if (!(o instanceof Appointment)) {
            return false;
        }
        Appointment appointment = (Appointment) o;
        return appointment.appointmentId == appointmentId;
    }
    
    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getCustomerName() {
        return customer.getCustomerName();
    }
    
    public String getStartDateFormatted() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm").withZone(ZoneId.systemDefault());

        String formatDateTime = startDate.format(formatter);
        return formatDateTime;
    }

    public String getEndDateFormatted() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm");

        String formatDateTime = endDate.format(formatter);
        return formatDateTime;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ZonedDateTime getStartDate() {
        return startDate;
    }

    public ZonedDateTime getEndDate() {
        return endDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    int appointmentId;
    int customerId;

    String title;
    String description;
    String location;
    String contact;
    String url;

    ZonedDateTime startDate;
    ZonedDateTime endDate;
    String startDateStr;
    Customer customer;
}
