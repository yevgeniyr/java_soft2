/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import Model.Appointment;
import Model.Customer;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author yevgeniy
 */




public class AppointmentController extends CalController implements Initializable {
    @FXML Button addButton;
    @FXML TextField title;
    @FXML TextField location;
    @FXML TextField contact;
    @FXML TextField url;
    @FXML TextArea description;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        

        addButton.setOnAction((event) -> handleSave());
    
    }
     
    private Appointment getAppointmentFromControlls() {
        if (! checkEmpty(title.getText(), "Customer Name") ) {      
            return null;
        } 
        
        if (! checkEmpty(location.getText(), "Location") ) {      
            return null;
        }
        
        if (! checkEmpty(contact.getText(), "Contact") ) {      
            return null;
        }
        
        if (! checkEmpty(url.getText(), "URL") ) {      
            return null;
        }
        
        
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(0);
        //cal.set(year, month, day, hour, minute, second);
        cal.set(2018, 9, 23, 1, 1, 0);
        Date startDate = cal.getTime(); // get back a Date object
        Date endDate = cal.getTime(); // get back a Date object
        
                
                
        int customerId;
        customerId = ((CustomerController)invocator).getCurrentCustomerId();
        Appointment appointment = Appointment(
                customerId, 
                title.getText(), 
                description.getText(), 
                location.getText(),
                contact.getText(),
                url.getText(),
                startDate, 
                endDate );
        return appointment;
    }
                   

    

    private void handleSave() {
        Appointment appointment  = getAppointmentFromControlls();
        
        System.out.println("got customer");
        if (appointment != null) {
            //((CustomerController)invocator).addCustomerToListView(customer);
            closeStage(actionEvent);
        }
    }

    private Appointment Appointment(int customerId, String text, String text0, String text1, String text2, String text3, Date startDate, Date endDate) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

