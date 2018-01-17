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
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author yevgeniy
 */




public class AppointmentController implements Initializable {
    @FXML Button addButton;
    @FXML TextField title;
    @FXML TextField location;
    @FXML TextField contact;
    @FXML TextField url;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        addButton.setOnAction(e -> ((CustomerController)this). ;
    }    
    
}
