/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import Model.Address;
import Model.City;
import Model.Customer;
import database.Database;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

/**
 *
 * @author yevgeniy
 */

public class NewCustomerController extends CalController implements Initializable{
    @FXML TextField newCustomerName;
    @FXML TextField newAddress;
    @FXML TextField newAddress2;
    @FXML ChoiceBox<String> newCitiesChoiceBox;
    @FXML TextField newPostalCode;
    @FXML TextField newPhone;
    @FXML Button save;
    Database db;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        db = Database.getInstance();
        newCitiesChoiceBox.getItems().addAll("New York","Chicago","Boston","Moscow","Culver");
    }   
    
    
    private Customer getCustomer() {

        if (! checkEmpty(newCustomerName.getText(), "Customer Name") ) {      
            return null;
        }    

        if (! checkEmpty(newAddress.getText(), "Address") ) {      
            return null;
        } 
        
        if (! checkEmpty(newAddress2.getText(), "Address2") ) {      
            return null;
        } 
        
        if (! checkEmpty(newPostalCode.getText(), "Postal Code") ) {      
            return null;
        } 
        
        if (! checkEmpty(newPhone.getText(), "Phone") ) {      
            return null;
        } 
        
        if ( ! checkEmpty(newCitiesChoiceBox.getSelectionModel().getSelectedItem(), "City" )) {
            return null;
        }

        City city = new City(3,newCitiesChoiceBox.getSelectionModel().getSelectedItem());
        int addressId = db.saveAddress(newAddress.getText(),
                       newAddress2.getText(),
                       city.getId(),
                       newPostalCode.getText(),
                       newPhone.getText(),
                       1);
        
        
        int customerId = db.saveCustomer(newCustomerName.getText(), addressId);
        //customer.setCustomerName(newCustomerName.getText());
        
        Address address = new Address(
            addressId,
            newAddress.getText(), 
            newAddress2.getText(), 
            city, 
            newPostalCode.getText(), 
            newPhone.getText());
    
        Customer customer = new Customer(customerId, newCustomerName.getText(), 1, address);
    
        
        return customer;
    }
    
    @FXML
    private void handleSave(ActionEvent actionEvent) {
        Customer customer  = getCustomer();
        
        System.out.println("got customer");
        if (customer != null) {
            ((CustomerController)invocator).addCustomerToListView(customer);
            closeStage(actionEvent);
        }
    }
}
