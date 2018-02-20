/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import Model.Appointment;
import Model.Customer;
import java.net.URL;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 *
 * @author yevgeniy
 */
public class AppointmentController extends CalController implements Initializable {

    @FXML
    TextField title;
    @FXML
    TextField location;
    @FXML
    TextField contact;
    @FXML
    TextField url;
    @FXML
    TextArea description;
    @FXML
    TextField startHour;
    @FXML
    TextField endHour;
    @FXML
    TextField startMinutes;
    @FXML
    TextField endMinutes;

    @FXML
    TextField startDay;
    @FXML
    TextField endDay;

    @FXML
    TextField startMonth;
    @FXML
    TextField endMonth;

    @FXML
    TextField startYear;
    @FXML
    TextField endYear;

    @FXML
    ListView customersListView;

    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }

    public ZonedDateTime getZonedDateTimeFromControlls(
            TextField month,
            TextField day,
            TextField year,
            TextField hour,
            TextField minutes) {

        ZonedDateTime dt;
        dt = ZonedDateTime.of(Integer.parseInt(year.getText()),
                Integer.parseInt(month.getText()),
                Integer.parseInt(day.getText()),
                Integer.parseInt(hour.getText()),
                Integer.parseInt(minutes.getText()),
                0,
                0,
                ZoneId.systemDefault());

        return dt;
    }
    
    Appointment getAppointmentFromControlls() {
        if (!checkEmpty(title.getText(), "Title Name")) {
            return null;
        }

        if (!checkEmpty(location.getText(), "Location")) {
            return null;
        }

        if (!checkEmpty(contact.getText(), "Contact")) {
            return null;
        }

        if (!checkEmpty(url.getText(), "URL")) {
            return null;
        }
        
        if (!checkEmpty(startMonth.getText(), "Start month")) {
            return null;
        }
        
        if (!checkEmpty(startDay.getText(), "Start day")) {
            return null;
        }
        
         if (!checkEmpty(startYear.getText(), "Start year")) {
            return null;
        }
         
         if (!checkEmpty(startHour.getText(), "Start hour")) {
            return null;
        }
         
        if (!checkEmpty(startMinutes.getText(), "Start minutes")) {
            return null;
        }
        

        Customer customer = (Customer) customersListView.getSelectionModel().getSelectedItem();

        if (customer == null) {
            showErrorDialog("Please select a customer from the list on the right");
            return null;
        }

        ZonedDateTime startDate = getZonedDateTimeFromControlls( startMonth,  startDay, startYear, 
                startHour, startMinutes);

        ZonedDateTime endDate = getZonedDateTimeFromControlls( endMonth, endDay,endYear, 
                endHour, endMinutes);

      
        System.out.println("got date from controlls " + startDate);
        Appointment appointment = new Appointment(
                customer,
                -1,
                title.getText(),
                description.getText(),
                location.getText(),
                contact.getText(),
                url.getText(),
                startDate,
                endDate);
        return appointment;
    }
}
