/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import Model.Appointment;
import Model.Customer;
import database.Database;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author yevgeniy
 */
public class CustomerController extends CalController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    ListView customers;
    @FXML
    TextArea addressTextArea;
    @FXML
    Label customerName;
    
    @FXML Button newCustomerButton;
    
    @FXML
    TableView appointmentsTableView;
    @FXML
    TableColumn titleTableColumn;
    @FXML
    TableColumn descTableColumn;
    @FXML
    TableColumn locTableColumn;
    @FXML
    TableColumn contactTableColumn;
    @FXML
    TableColumn urlTableColumn;
    
    @FXML
    TableColumn startTableColumn;

    @FXML
    TableColumn endTableColumn;

    @FXML
    Button newAppointmentButton;

    @FXML ChoiceBox<String> monthsChoiceBox;
    
    @FXML ChoiceBox<String> weeksChoiceBox;
    
    final String[] months = new String[]{
        "All", 
        "January",
        "February",
        "March",
        "April",
        "May",  
        "June", 
        "July", 
        "August",
        "September",    
        "October",
        "November",
        "December"
    };    
    
    ObservableList<Customer> choices;
            
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Database db = Database.getInstance();

        titleTableColumn.setCellValueFactory(
                new PropertyValueFactory<Appointment, String>("title")
        );

        descTableColumn.setCellValueFactory(
                new PropertyValueFactory<Appointment, String>("description")
        );

        locTableColumn.setCellValueFactory(
                new PropertyValueFactory<Appointment, String>("location")
        );

        contactTableColumn.setCellValueFactory(
                new PropertyValueFactory<Appointment, String>("contact")
        );

        urlTableColumn.setCellValueFactory(
                new PropertyValueFactory<Appointment, String>("url")
        );
        
        startTableColumn.setCellValueFactory(
                new PropertyValueFactory<Appointment,Date>("startDate")
        );
        
        endTableColumn.setCellValueFactory(
                new PropertyValueFactory<Appointment,Date>("endDate")
        );

//        ObservableList<Appointment> appointments
//                = FXCollections.observableArrayList(
//                        new Appointment(1, 1, "Meet for coffee",
//                                "this is desc", "5 & 58 main ave", "contact", "http://this.url")
//                );

        

        choices = FXCollections.observableArrayList();

        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        appointmentsTableView.setItems(appointments); 
 
        
//choices.add(new Choice(null, "No selection"));
        for (Customer customer : db.getCustomers()) {
            choices.add(customer);
        }
        customers.getItems().addAll(choices);

        
        customers.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Customer>() {
            
                    @Override
            public void changed(ObservableValue<? extends Customer> observableValue,
                    Customer oldCustomer, Customer newCustomer) {

                if ( newCustomer == null ) {
                    System.out.println("newCustomer is null");
                    return;
                }
                if ( newCustomer.getAddress() == null ) {
                    System.out.println("newCustomer.getAddress is null");
                }
                        
                System.out.println(newCustomer.getAddress().toString());
                
                addressTextArea.setText(newCustomer.getAddress().toString());
                
                customerName.setText("Appointment with " + newCustomer.getCustomerName());
                
                appointments.clear();

                ArrayList<Appointment> appointments = newCustomer.getAppointments(); 
                
                if (appointments != null) { 
                    appointments.addAll(appointments);
                }

                ((TableColumn)appointmentsTableView.getColumns().get(0)).setVisible(false);
                ((TableColumn)appointmentsTableView.getColumns().get(0)).setVisible(true);

            }
        });

        monthsChoiceBox.getItems().addAll(months);
        monthsChoiceBox.setValue("All");
        weeksChoiceBox.getItems().addAll("All","1","2","3","4");
        weeksChoiceBox.setValue("All");
                
        newCustomerButton.setOnAction(e -> {
            showDialog("Add Customer","View_Controller/NewCustomer.fxml");
        });
        newAppointmentButton.setOnAction(e -> showNewAppointmentScene());
    }
    
    
    public int getCurrentCustomerId() {
        return ((Customer)customers.getSelectionModel().getSelectedItem()).getCustomerId();
    }
    
    public void addCustomerToListView(Customer customer) {
        choices.add(customer);
        customers.setItems(choices);
        
    }
    public FXMLLoader getResource(String resource) {
        return new FXMLLoader(getClass().getClassLoader().getResource(resource));
    }

    public void showDialog(String title, String sceneFXML) {
        showDialog(title, sceneFXML, null);
    }
        
    public void showDialog(String title, String sceneFXML, Object invocatorData) {
        //get reference to the button's stage         
        //Stage =(Stage) addpartbutton.getScene().getWindow();
        //load up OTHER FXML document
        try {
            FXMLLoader loader = getResource(sceneFXML);
            Parent root;

            root = loader.load();

            Stage _dialogStage = new Stage();
            _dialogStage.setTitle(title);
            Scene scene = new Scene(root);
            _dialogStage.setScene(scene);
            CalController ctrl = loader.getController();
            ctrl.setDialogStage(_dialogStage);
            ctrl.setInvocator(this);
            ctrl.setInvocatorData(invocatorData);
            //ctrl.postInit();
            //ctrl.populateControls();
            
            _dialogStage.showAndWait();
        } catch (IOException e) {
            System.out.println(e);
        }
    }    
    
    private void showNewAppointmentScene() {
        try {
            Stage _stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("View_Controller/Appointment.fxml"));
            Parent root = loader.load();


            _stage.initModality(Modality.APPLICATION_MODAL); 
            Scene scene = new Scene(root);
            _stage.setScene(scene);
            _stage.showAndWait();
            
        } catch (IOException ex) {
            System.out.println(ex);
            ex.printStackTrace();

        }

    }

    void addCustomer(Customer customer) {
        
    }
}
