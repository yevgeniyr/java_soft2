/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import Model.Appointment;
import Model.Customer;
import database.Database;
import java.net.URL;
import java.time.ZonedDateTime;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ListView;

/**
 * FXML Controller class
 *
 * @author yevgeniy
 */
public class EditAppointmentController extends AppointmentController implements Initializable {

    @FXML
    Button saveButton;

    Database db;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }

    @Override
    public void initData() {

        db = Database.getInstance();

        customersListView.getItems().addAll(db.getCustomers());
        setControlsFromAppointment();

        saveButton.setOnAction((event) -> {
            System.out.println("We are inside here editappoint setOnAction saveButton");
            Appointment appointment = getAppointmentFromControlls();
            if (appointment == null ) { return; }
            
            appointment.setAppointmentId(((Appointment) invocatorData).getAppointmentId());
            db.updateAppointment(
                    appointment.getAppointmentId(),
                    appointment.getCustomer().getCustomerId(),
                    appointment.getTitle(),
                    appointment.getDescription(),
                    appointment.getLocation(),
                    appointment.getContact(),
                    appointment.getUrl(),
                    appointment.getStartDate(),
                    appointment.getEndDate());

            //((CustomerController)invocator).addCustomerToListView(customer);
            System.out.println("removing with appointment id + " + appointment.getAppointmentId());
            ((AppointmentsController) invocator).removeAppointment(appointment);
            ((AppointmentsController) invocator).addAppointment(appointment);
            closeStage(event);

        });

    }

    private void setControlsFromAppointment() {
        Appointment appointment = (Appointment) invocatorData;
        System.out.println("were inside");
        if (appointment == null) {
            System.out.println("appointment null");
        }
        title.setText(appointment.getTitle());
        System.out.println("tilte is + " + appointment.getTitle());
        location.setText(appointment.getLocation());
        contact.setText(appointment.getContact());
        url.setText(appointment.getUrl());
        description.setText(appointment.getDescription());
        startMonth.setText(Integer.toString(appointment.getStartDate().getMonthValue()));
        endMonth.setText(Integer.toString(appointment.getEndDate().getMonthValue()));

        startDay.setText(Integer.toString(appointment.getStartDate().getDayOfMonth()));
        endDay.setText(Integer.toString(appointment.getEndDate().getDayOfMonth()));

        startYear.setText(Integer.toString(appointment.getStartDate().getYear()));
        endYear.setText(Integer.toString(appointment.getEndDate().getYear()));

        startHour.setText(Integer.toString(appointment.getStartDate().getHour()));
        endHour.setText(Integer.toString(appointment.getEndDate().getHour()));

        startMinutes.setText(Integer.toString(appointment.getStartDate().getMinute()));
        endMinutes.setText(Integer.toString(appointment.getEndDate().getMinute()));
        System.out.println("selecting customer with id " + appointment.getCustomer());
        customersListView.getSelectionModel().select(appointment.getCustomer());
        // customersListView.getSelectionModel().selectFirst();
    }

//   private Appointment getAppointmentFromControlls() {
//        if (! checkEmpty(title.getText(), "Title Name") ) {      
//            return null;
//        } 
//        
//        if (! checkEmpty(location.getText(), "Location") ) {      
//            return null;
//        }
//        
//        if (! checkEmpty(contact.getText(), "Contact") ) {      
//            return null;
//        }
//        
//        if (! checkEmpty(url.getText(), "URL") ) {      
//            return null;
//        }
//        
//                
//        if (!checkEmpty(startMonth.getText(), "Start month")) {
//            return null;
//        }
//        
//        if (!checkEmpty(startDay.getText(), "Start day")) {
//            return null;
//        }
//        
//         if (!checkEmpty(startYear.getText(), "Start year")) {
//            return null;
//        }
//         
//         if (!checkEmpty(startHour.getText(), "Start hour")) {
//            return null;
//        }
//         
//        if (!checkEmpty(startMinutes.getText(), "Start minutes")) {
//            return null;
//        }
//        
//        Customer customer = (Customer)customersListView.getSelectionModel().getSelectedItem();
//        
//        if (customer == null) {
//            showErrorDialog("Please select a customer from the list on the right"); 
//            return null;
//        }
//        
//        ZonedDateTime startDate = getZonedDateTimeFromControlls( startMonth,  startDay, startYear, 
//                startHour, startMinutes);
//
//        ZonedDateTime endDate = getZonedDateTimeFromControlls( endMonth, endDay,endYear, 
//                endHour, endMinutes);
//
//        Appointment appointment = new  Appointment(
//                customer, 
//                -1,
//                title.getText(), 
//                description.getText(), 
//                location.getText(),
//                contact.getText(),
//                url.getText(),
//                startDate, 
//                endDate );
//        return appointment;
//    }
}
