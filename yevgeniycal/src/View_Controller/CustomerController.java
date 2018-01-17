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
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author yevgeniy
 */
public class CustomerController extends CalController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML ListView customers;
    @FXML TextArea addressTextArea;
    @FXML Label customerName;
    @FXML TableView appointmentsTableView;
    @FXML TableColumn titleTableColumn;
    @FXML TableColumn descTableColumn;
    @FXML TableColumn locTableColumn;
    @FXML TableColumn contactTableColumn;
    @FXML TableColumn urlTableColumn;
   
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
            Database db = Database.getInstance();
            
            titleTableColumn.setCellValueFactory(
                new PropertyValueFactory<Appointment,String>("title")
            );
            
            descTableColumn.setCellValueFactory(
                new PropertyValueFactory<Appointment,String>("description")
            );
              
            locTableColumn.setCellValueFactory(
                new PropertyValueFactory<Appointment,String>("location")
            );
            
            contactTableColumn.setCellValueFactory(
                new PropertyValueFactory<Appointment,String>("contact")
            );
            
            urlTableColumn.setCellValueFactory(
                new PropertyValueFactory<Appointment,String>("url")
            );
            
              
            
          

           ObservableList<Appointment> appointments 
                    = FXCollections.observableArrayList(
                new Appointment(1,1,"Meet for coffee", 
                        "this is desc", "5 & 58 main ave","contact","http://this.url")
                            );        
            
            appointmentsTableView.setItems(appointments); // addItems(appointments);
            
            
            ObservableList<Customer> choices = FXCollections.observableArrayList();
            
//choices.add(new Choice(null, "No selection"));
            for (Customer customer : db.getCustomers()) {
                choices.add(customer);
            }
            customers.getItems().addAll(choices);
            
            customers.getSelectionModel().selectedItemProperty().addListener(
                    new ChangeListener<Customer>() {
                        @Override public void changed(ObservableValue<? extends Customer> observableValue, 
                                Customer oldCustomer, Customer newCustomer) {
                            
                            
                            addressTextArea.setText(newCustomer.getAddress().toString());
                            customerName.setText("Appointment with " + newCustomer.getCustomerName());
                            //newCustomer.address();
                            
                            
                            
                        }
                    });
        }
    }    
    

