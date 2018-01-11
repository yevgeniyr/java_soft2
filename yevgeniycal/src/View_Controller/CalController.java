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
import Model.Part;
import Model.Product;
import java.util.Optional;
import javafx.scene.control.ButtonType;

/**
 *
 * @author yevgeniy
 */
public class CalController {

    private Stage dialogStage;

    Object invocator;
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
        this.invocator = invocator;
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
    
    boolean validate(Part part) {
        if (part.getInStock() > part.getMax()) {
            showErrorDialog("inventory can't be bigger than max");
            return false;
        }
        if (part.getInStock() < part.getMin()) {
            showErrorDialog("inventory can't be lower than min");
            return false;
        }
        if (part.getMin() > part.getMax()) {
            showErrorDialog("min can't be bigger than max");
            return false;
        }

        if (part.getMax() < part.getMin()) {
            showErrorDialog("max can't be smaller than min");
            return false;
        }

        return true;
    }

    boolean validate(Product product) {
        
        if (product.getInStock() > product.getMax()) {
            showErrorDialog("inventory can't be bigger than max");
            return false;
        }
        if (product.getInStock() < product.getMin()) {
            showErrorDialog("inventory can't be lower than min");
            return false;
        }
        if (product.getMin() > product.getMax()) {
            showErrorDialog("min can't be bigger than max");
            return false;
        }

        if (product.getMax() < product.getMin()) {
            showErrorDialog("max can't be smaller than min");
            return false;
        }

        if (product.getPartsList().isEmpty()) {
            showErrorDialog("product must have at least one part");
            return false;
        }
        
        //System.out.println("priceOfParts" + product.priceOfParts());
        //System.out.println("price" + product.getPrice());
        
        if ( product.getPrice() < product.priceOfParts()  ) {
            showErrorDialog("The product's price can't be smaller than the sum of prices of its parts");
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
