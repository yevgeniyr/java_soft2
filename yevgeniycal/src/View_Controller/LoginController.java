/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.collections.FXCollections;

/**
 * FXML Controller class
 *
 * @author yevgeniy
 */

public class LoginController extends CalController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    TextField username;
    @FXML
    TextField password;
    
    @FXML
    Button loginButton;
    
    @FXML
    ChoiceBox<String> languageSelect;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //languageSelect = new javafx.scene.control.ChoiceBox(FXCollections.observableArrayList("Russian", "Japanese"));
        
        languageSelect.getItems().add("Japanese");
        languageSelect.getItems().add("Russian");
        languageSelect.getItems().add("English");
        languageSelect.setValue("English");
        Locale locale = Locale.getDefault();
        System.out.println(locale);
        System.out.println("testing");
        loginButton.setOnAction(e -> checkLogin(languageSelect));
        Locale loc = new Locale("ru", "RU");
        // TODO
        printProperties(loc);
    }
    
    private static void printProperties(Locale locale) {
        ResourceBundle rb = ResourceBundle.getBundle("Cal", locale);
        System.out.println(rb.getString("invalidlogin"));
        //System.out.println(rb.getString("open"));
   }

    private void checkLogin(ChoiceBox<String> languageSelect) {
        System.out.println(languageSelect.getValue());
        
    }

}
