/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import yevgeniyrechkininventory.YevgeniyRechkinInventory;
import Model.Part;


/**
 * FXML Controller class
 *
 * @author yevgeniy
 */
public class AddPartController extends InventoryPartController implements Initializable {

    /**
     * Initializes the controller class.
     */
    YevgeniyRechkinInventory mainApp;

    private Stage dialogStage;

    @FXML
    private void handleOk(ActionEvent actionEvent) {
        Part part = getPartFromControlls();
        if (part == null ) {
            return;
        }
        if (validate(part) != true ) {
            return;
        }
        part.setPartID(((MainScreenController) invocator).nextPartID());
        ((MainScreenController) invocator).addPart(part);
        closeStage(actionEvent);
    }
}
