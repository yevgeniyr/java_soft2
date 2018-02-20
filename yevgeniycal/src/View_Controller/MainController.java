/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

/**
 *
 * @author yevgeniy
 */
public class MainController extends CalController implements Initializable {
    @FXML Button appointmentsButton;
    @FXML Button customersButton;
    
    @FXML Button typesByMonthButton;
    
    @FXML Button scheduleButton;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
    }
       private void displayAppointmentsScene()  {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("View_Controller/Appointments.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);

            stage.setScene(scene);
            ((CalController) loader.getController()).setStage(stage);
            stage.setMaximized(true);
            stage.show();
        } catch (IOException ex) {
            System.out.println( ex);
            ex.printStackTrace();
            
        }
    }
}
