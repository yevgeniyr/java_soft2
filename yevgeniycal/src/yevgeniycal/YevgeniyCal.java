/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yevgeniycal;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import View_Controller.CalController;
import View_Controller.LoginController;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;


/**
 *
 * @author yevgeniy rechkin
 */
public class YevgeniyCal extends Application {  
    Connection conn = null;
    
    public Connection getDBConnection() {
        return conn;
    }
    
    

     private void displayLogin(Stage stage)    { 
        try {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("View_Controller/Login.fxml"));
        //FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("View_Controller/Customer.fxml"));
        
        Parent root = loader.load();
        
        Scene scene = new Scene(root);        
        
        stage.setScene(scene);
        //((CalController) loader.getController()).setInvocator(this);
               ((CalController) loader.getController()).setStage(stage);
        stage.show();
        }
        catch (IOException ex) {
            System.out.println(ex);
        }
    }
     
     
    
    @Override
    public void start(Stage stage) throws Exception {
       displayLogin(stage); 
        
    
      
       
        
        
        Timestamp  ts;
        ts = getUTCTimestamp("test");
        
    }

    public Timestamp getUTCTimestamp(String appointmentTime) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd kk:mm"); 
        String txtStartTime;
        txtStartTime = "2017-03-29 12:00";
        
        appointmentTime= txtStartTime;

        LocalDateTime ldtStart;
        ldtStart = LocalDateTime.parse(appointmentTime, df);


        // get local zone
        ZoneId zid = ZoneId.systemDefault();

        ZonedDateTime zdtStart = ldtStart.atZone(zid);
        
        System.out.println("Local Time: " + zdtStart);
        
        ZonedDateTime utcStart = zdtStart.withZoneSameInstant(ZoneId.of("UTC"));
        
        System.out.println("Zoned time: " + utcStart);
        
        ldtStart = utcStart.toLocalDateTime();
        System.out.println("Zoned time with zone stripped:" + ldtStart);
        //Create Timestamp values from Instants to update database
        Timestamp startsqlts = Timestamp.valueOf(ldtStart); //this value can be inserted into database
        System.out.println("Timestamp to be inserted: " +startsqlts);
        return startsqlts;
    }


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
