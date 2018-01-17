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
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
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
    Button newAppointmentButton;

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

        ObservableList<Appointment> appointments
                = FXCollections.observableArrayList(
                        new Appointment(1, 1, "Meet for coffee",
                                "this is desc", "5 & 58 main ave", "contact", "http://this.url")
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
            @Override
            public void changed(ObservableValue<? extends Customer> observableValue,
                    Customer oldCustomer, Customer newCustomer) {

                addressTextArea.setText(newCustomer.getAddress().toString());
                customerName.setText("Appointment with " + newCustomer.getCustomerName());
                //newCustomer.address();

            }
        });

        newAppointmentButton.setOnAction(e -> showNewAppointmentScene());
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
}
