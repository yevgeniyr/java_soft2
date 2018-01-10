/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.event.ActionEvent;

import Model.Part;
import Model.Inhouse;
import Model.Outsourced;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import javafx.scene.layout.Region;

/**
 * FXML Controller class
 *
 * @author yevgeniy
 */
public class ModifyPartController extends InventoryPartController implements Initializable {
    // populates controlls on the screen from the part object
    
    public void setControllsFromPart(Part part) {
        name.setText(part.getName());
        inStock.setText(Integer.toString(part.getInStock()));
        partID.setText(Integer.toString(part.getPartID()));
        price.setText(Double.toString(part.getPrice()));
        min.setText(Integer.toString(part.getMin()));
        max.setText(Integer.toString(part.getMax()));

        if (part instanceof Inhouse) {
            inhouse.setSelected(true);
            location.setText(Integer.toString(((Inhouse) part).getMachineID()));
        } else {
            location.setText(((Outsourced) part).getCompanyName());
            outsourced.setSelected(true);
        }
    }

    @Override
    public void populateControls() {
        setControllsFromPart((Part) invocatorData);
    }

    @FXML
    private void handleModifyOk(ActionEvent actionEvent) {
        Part part = getPartFromControlls();
        part.setPartID(Integer.parseInt(partID.getText()));
        


        ((MainScreenController) invocator).removePart(part);
        ((MainScreenController) invocator).addPart(part);
        
        closeStage(actionEvent);
    }
}
