/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

// On an appointment table, 
// you really only need customer name, appointment type, start time, and end time.

package View_Controller;

import Model.Appointment;
import Model.Customer;
import database.Database;
import java.io.IOException;
import java.net.URL;
import java.time.ZonedDateTime;
import java.time.temporal.IsoFields;
import java.util.ArrayList;

import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

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
public class AppointmentsController extends CalController implements Initializable {

    /**
     * Initializes the controller class.
    */
    
    @FXML
    TextArea addressTextArea;
    @FXML
    Label customerName;

    @FXML
    Button newAppointmentButton;

    @FXML
    Button deleteAppointmentButton;
    
    @FXML
    Button editAppointmentButton;
    

    @FXML
    TableView appointmentsTableView;
    @FXML
    TableColumn typeTableColumn;
    @FXML
    TableColumn descTableColumn;
    @FXML
    TableColumn locTableColumn;
    @FXML
    TableColumn contactTableColumn;
    @FXML
    TableColumn urlTableColumn;

    @FXML
    TableColumn customerTableColumn;
       
    @FXML
    TableColumn startTableColumn;

    @FXML
    TableColumn endTableColumn;

    @FXML
    ChoiceBox<String> monthsChoiceBox;

    @FXML
    ChoiceBox<String> weeksChoiceBox;

    @FXML
    TextArea customerTextArea;
    
    ObservableList<Appointment> appointments;

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
    
    public void addAppointment(Appointment appointment) {
        appointments.add(appointment);
        appointmentsTableView.refresh();
         appointmentsTableView.getSelectionModel().select(appointment);
    }

    public void removeAppointment(Appointment appointment) {
        appointments.remove(appointment);
        appointmentsTableView.refresh();
        
    }    
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Database db = Database.getInstance();

        typeTableColumn.setCellValueFactory(
                new PropertyValueFactory<Appointment, String>("title")
        );


        customerTableColumn.setCellValueFactory(
                new PropertyValueFactory<Appointment, String>("customerName")
        );

        startTableColumn.setCellValueFactory(
                new PropertyValueFactory<Appointment, String>("startDateFormatted")
        );
        
        endTableColumn.setCellValueFactory(
                new PropertyValueFactory<Appointment, String>("endDateFormatted")
        );


        monthsChoiceBox.getItems().addAll(months);
        monthsChoiceBox.getSelectionModel().selectFirst();

        
        appointments = FXCollections.observableArrayList();
        appointments.addAll(db.getAppointments());

        FilteredList<Appointment> filteredData = new FilteredList<>(appointments, p -> true);

        weeksChoiceBox.getSelectionModel().selectedIndexProperty()
                
                .addListener(new ChangeListener<Number>() {
                    
                    @Override
                    public void changed(ObservableValue<? extends Number> observableValue,
                            Number number, Number newIndex) {

                        monthsChoiceBox.getSelectionModel().selectFirst();
                        filteredData.setPredicate(appointment -> {
                            if (weeksChoiceBox.getItems().get((Integer) newIndex).equals("All")) {
                                return true;
                            }
                            
                            if (appointment.getStartDate().get(IsoFields.WEEK_OF_WEEK_BASED_YEAR) == (Integer) newIndex) {
                                return true;
                            } else {
                                return false;
                            }
                              
                        });
                    }
                });
        
        monthsChoiceBox.getSelectionModel().selectedIndexProperty()
                .addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observableValue,
                            Number number, Number newIndex) {
                        
                        weeksChoiceBox.getSelectionModel().selectFirst();

                        filteredData.setPredicate(appointment -> {

                            if (monthsChoiceBox.getItems().get((Integer) newIndex).equals("All")) {
                                return true;
                            }
 
                            if (appointment.getStartDate().getMonth().getValue() == (Integer) newIndex) {
                                return true;
                            } else {
                                return false;
                            }
                        });

                    }
                });

       
        appointmentsTableView.setItems(filteredData);

        appointmentsTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldAppointment, _newAppointment) -> {

            if (_newAppointment != null) {
                Appointment newAppointment = (Appointment) _newAppointment;

                Customer customer = newAppointment.getCustomer();
                if (customer != null) {
                    System.out.print("customer");
                }

                String details = new String(
                        "Customer :" + customer.getCustomerName() + "\n"
                        + "Phone : " + customer.getAddress().getPhone() + "\n"
                        + "Address : " + customer.getAddress() + "\n"
                        + "Desc: " + newAppointment.getDescription() + "\n"
                        + "URL: " + newAppointment.getUrl() + "\n"
                        + "Start: " + newAppointment.getStartDateFormatted() + "\n"
                        + "End: " + newAppointment.getEndDateFormatted() + "\n"
                );

                //   +
                ///    newAppointment.
                customerTextArea.setText(details);
            }
        });

        monthsChoiceBox.getItems().add("All");
        monthsChoiceBox.getItems().addAll(months);

        weeksChoiceBox.getItems().add("All"); 
        
        for (int i = 1; i <= 52; i++) {
           weeksChoiceBox.getItems().add(Integer.toString(i));
        }
        
        
        editAppointmentButton.setOnAction(e -> {
            Appointment appointment = (Appointment)appointmentsTableView.getSelectionModel().getSelectedItem();
            if (appointment == null) {
                showErrorDialog("Please select an appointment to edit");
                return;
            } 
                showDialog("Edit Appointment","View_Controller/EditAppointment.fxml",appointment);    
            });
      
        newAppointmentButton.setOnAction(e -> showDialog("New Appointment","View_Controller/NewAppointment.fxml"));
        
        deleteAppointmentButton.setOnAction(e -> { 
            Appointment appointment = (Appointment)appointmentsTableView.getSelectionModel().getSelectedItem();
            
            appointments.remove(appointment);
            db.removeAppointment(appointment.getAppointmentId());
        });
    }

    public FXMLLoader getResource(String resource) {
        return new FXMLLoader(getClass().getClassLoader().getResource(resource));
    }

    public void showDialog(String title, String sceneFXML) {
        showDialog(title, sceneFXML, null);
    }

    public void showDialog(String title, String sceneFXML, Object _invocatorData) {
        try {
            System.out.println(" we are in showdialog");
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
            ctrl.setInvocatorData(_invocatorData);
            ctrl.initData();
	    
            System.out.println("set _invocatorData " + _invocatorData);
            //ctrl.postInit();
            //ctrl.populateControls();

            _dialogStage.showAndWait();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}


