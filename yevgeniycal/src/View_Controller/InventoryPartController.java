package View_Controller;

import Model.Part;
import Model.Inhouse;
import Model.Outsourced;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.Label;

import javafx.scene.control.RadioButton;

import javafx.stage.Stage;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;

/**
 *
 * @author yevgeniy
 */
public class InventoryPartController extends InventoryController {

    private Stage dialogStage;


    @FXML
    RadioButton inhouse, outsourced;

    @FXML
    ToggleGroup locationGroup;

    @FXML
    TextField machineID;
    @FXML
    TextField companyName;
    @FXML
    Label labelCompanyNameMachineID;
    @FXML
    Label labelMachineID;
    @FXML
    TextField name;
    @FXML
    TextField inStock;
    @FXML
    TextField price;

    @FXML
    TextField max;
    @FXML
    TextField min;
    @FXML
    TextField location;
    @FXML
    Label partID;

    boolean inhouseOn = false;

    void showInhouseControls() {
        inhouseOn = true;
        labelCompanyNameMachineID.setText("Machine ID");
        location.setPromptText("Machine ID");
    }

    Part getPartFromControlls() {
        Part part;
        if (inhouseOn) {
            part = new Inhouse();
         
            if ( !checkInteger(inStock.getText(), "MachineID") ) {
                 return null;
            }
            ((Inhouse) part).setMachineID(Integer.parseInt(location.getText()));
        
        } else {
            part = new Outsourced();
            if ( !checkEmpty(location.getText(), "Company Name") ) {
                 return null;
            }
            ((Outsourced) part).setCompanyName(location.getText());
        }

        if (! checkEmpty(name.getText(), "name") ) {      
            return null;
        }

        if ( !checkDouble(price.getText(), "Price") ) {
            return null;
        }
        
        if ( !checkInteger(inStock.getText(), "Inv") ) {
            return null;
        }
        
        if ( !checkInteger(min.getText(), "Min") ) {
            return null;
        }
        
        if ( !checkInteger(max.getText(), "Max") ) {
            return null;
        }
        
        part.setName(name.getText());
        
        part.setInStock(Integer.parseInt(inStock.getText()));
        
        part.setPrice(Double.parseDouble(price.getText()));

        part.setMin(Integer.parseInt(min.getText()));
        part.setMax(Integer.parseInt(max.getText()));
        return part;

    }

    void showCompanyControls() {
        inhouseOn = false;
        labelCompanyNameMachineID.setText("Company Name");
        location.setPromptText("Company Name");
    }

    public void initialize(URL url, ResourceBundle rb) {
        //locationGroup = new ToggleGroup();

        if (locationGroup == null) {

            System.out.println("null locationgroup");
        }
        locationGroup.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) -> {
            if (locationGroup.getSelectedToggle() != null) {
                if (locationGroup.getSelectedToggle() == inhouse) {
                    showInhouseControls();
                } else {
                    showCompanyControls();
                }
                // Do something here with the userData of newly selected radioButton
 
            }
        });

    }
    @FXML
    private void handleCancel(ActionEvent actionEvent) {
        if ( showConfirmDialog("Are you sure you want to cancel") ) {
            closeStage(actionEvent);
        }
    }
}
