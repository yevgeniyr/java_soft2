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
import java.time.ZoneId;
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
public class NewAppointmentController extends AppointmentController implements Initializable {

    @FXML
    Button addButton;
//    @FXML
//    TextField title;
//    @FXML
//    TextField location;
//    @FXML
//    TextField contact;
//    @FXML
//    TextField url;
//    @FXML
//    TextArea description;
//
//    @FXML
//    TextField startHour;
//    @FXML
//    TextField endHour;
//    @FXML
//    TextField startMinutes;
//    @FXML
//    TextField endMinutes;
//
//    @FXML
//    TextField startDay;
//    @FXML
//    TextField endDay;
//
//    @FXML
//    TextField startMonth;
//    @FXML
//    TextField endMonth;
//
//    @FXML
//    TextField startYear;
//    @FXML
//    TextField endYear;

    @FXML
    ListView customersListView;
    Database db;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        db = Database.getInstance();

        customersListView.getItems().addAll(db.getCustomers());

        addButton.setOnAction((event) -> {
            
            Appointment appointment = getAppointmentFromControlls();

              int appointmentId = db.saveAppointment(
                      appointment.getTitle(),
                      appointment.getDescription(),
                      appointment.getLocation(),
                      appointment.getContact(),
                      appointment.getUrl(),
                      appointment.getStartDate(),
                      appointment.getEndDate(),
                      appointment.getCustomer().getCustomerId());
            
            System.out.println("got customer");
            if (appointment != null) {
                //((CustomerController)invocator).addCustomerToListView(customer);
                ((AppointmentsController) invocator).addAppointment(appointment);
                closeStage(event);
            }
        });
    }



    
}
