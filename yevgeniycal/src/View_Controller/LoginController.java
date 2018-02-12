/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import Model.User;
import database.Database;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;

import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;

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
    Label usernameLabel;

    @FXML
    Label passwordLabel;

    @FXML
    TextField username;
    @FXML
    TextField password;

    @FXML
    Button loginButton;

    //@FXML
    //ChoiceBox<String> languageSelect;
    Locale locale;
    ResourceBundle rb;

    static User currentUser = null;
            
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //languageSelect = "Russian", "Japanese"));

        currentUser = null;
        
        final String[] languages = new String[]{"English", "Russian", "French"};

        //new javafx.scene.control.ChoiceBox(FXCollections.observableArrayList(
        //languageSelect.getItems().addAll( languages );
        //languageSelect.getItems().add();
        //languageSelect.getItems().add("English");
        //languageSelect.setValue("English");
        //Locale.setDefault(new Locale("ru", "RU"));
        locale = Locale.getDefault();
        changeLocale(locale);

        loginButton.setOnAction(e -> checkLogin(username.getText(), password.getText()));
        
    }

    public static User getCurrentUser() {
        return currentUser;
    }
    
    private void changeLocale(Locale locale) {
        rb = ResourceBundle.getBundle("Cal", locale);
        //System.out.println(rb.getString("invalidlogin"));
        String usernameText = rb.getString("username");
        String passwordText = rb.getString("password");
        String loginText = rb.getString("login");
        usernameLabel.setText(usernameText);
        passwordLabel.setText(passwordText);
        loginButton.setText(loginText);

        //System.out.println(rb.getString("open"));
    }

    private void checkLogin(String username, String password) {

        //System.out.println(languageSelect.getValue());
        Database db = Database.getInstance();
        
        int userId = db.getUserId(username, password);
        System.out.println("got userId" + userId);
        if (userId >= 0) {
            currentUser = new User(username,userId);
            displayCustomerScene();
        } else {
            String invalidLogin = rb.getString("invalidlogin");
            showErrorDialog(invalidLogin);
        }
    }

    private void displayCustomerScene()  {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("View_Controller/Customer.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);

            stage.setScene(scene);
            ((CalController) loader.getController()).setStage(stage);
            stage.show();
        } catch (IOException ex) {
            System.out.println( ex);
            ex.printStackTrace();
            
        }
    }

    @Override
    void showErrorDialog(String errorMsg) {
        //Alert alert = new Alert(Alert.AlertType.ERROR);
        Alert alert = new Alert(Alert.AlertType.ERROR);
        //alert.setTitle("Error Dialog");
        //alert.setHeaderText("Validation Error");
        alert.setContentText(errorMsg);

        alert.showAndWait();
    }

}
