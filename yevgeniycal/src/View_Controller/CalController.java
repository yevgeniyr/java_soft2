/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.util.Optional;
import javafx.scene.control.ButtonType;
import yevgeniycal.YevgeniyCal;

/**
 *
 * @author yevgeniy
 */
public class CalController {

    private Stage dialogStage;
     Stage stage;
    
    YevgeniyCal invocator;
    Object invocatorData;

    public void setDialogStage(Stage dialogStage) {

        this.dialogStage = dialogStage;
    }

    @FXML
    void closeStage(ActionEvent actionEvent) {

        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();

    }

    public void postInit() {

    }

    public void populateControls() {

    }

    public void setInvocator(Object invocator) {
        this.invocator = (YevgeniyCal)invocator;
    }
    
    public void setStage(Stage _stage) {
        stage = _stage;
    }

    public void setInvocatorData(Object invocatorData) {
        this.invocatorData = invocatorData;
    }

    void showErrorDialog(String errorMsg) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText("Validation Error");
        alert.setContentText(errorMsg);

        alert.showAndWait();
    }

    boolean checkEmpty(String val, String valName) {
        if (val.isEmpty() ) {
            showErrorDialog(valName + "can't be empty");
            return false;
        }      
        return true;
    }
    
    boolean checkInteger(String val, String valName) {
        
        try {
        Integer.parseInt(val);
                }
        catch ( NumberFormatException e) {
            showErrorDialog(valName + " must be Integer");
            return false;
        }
        return true;
    } 
   
    boolean checkDouble(String val, String valName) {
        
        try {
            Double.parseDouble(val);
        }
        
        catch ( NumberFormatException e) {
            showErrorDialog(valName + " must be decimal");
            return false;
        }
        return true;
    } 
    
  
   
    boolean showConfirmDialog(String msg) {
        Alert alert = new Alert(AlertType.CONFIRMATION,
                msg, 
                ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
        
        Optional<ButtonType> showAndWait = alert.showAndWait();

        return showAndWait.get() == ButtonType.YES;
    }
//
//•  ensuring that a 
//2.  Set 2
//•  preventing the user from deleting a product that has a part assigned to it
//
//•  including a confirm dialogue for all “Delete” and “Cancel” buttons
//
//•  ensuring that the price of a product cannot be less than the cost of the parts
//
//•  ensuring that a product must have a name, price, and inventory level (default 0)
//    
//    }
}
