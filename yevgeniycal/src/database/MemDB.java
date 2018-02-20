/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

/**
 *
 * @author yevgeniy
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import Model.Address;
import Model.Appointment;
import Model.City;
import Model.Country;
import Model.Customer;
import View_Controller.LoginController;
import com.mysql.jdbc.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import javax.xml.transform.Result;

/**
 *
 * @author yevgeniy
 */
public class MemDB extends Database{

    static MemDB database = null;
    private void connect() {
        
    }

    

    public static Database getInstance() {
        if (database == null) {
            database = new MemDB();
        }
        return database;
    }

    public int getUserId(String username, String password) {
        return 1;
    }

    public ArrayList<Appointment> getAppointments() {
        ArrayList<Appointment> appointments = new ArrayList<>();
        Appointment ap1;
        ZonedDateTime startDate;
        
        City city = new City(1,"new york");
                
        Address address = new Address(1, "9 har", "", city, "11732", "29832-232");
             
                
        Customer newCustomer = new Customer(1, "yevgeniy", 1,  address);
    
        
        startDate = ZonedDateTime.now();
        ap1 = new Appointment(
                newCustomer,
                1,
                "apt1",
                "desc1",
                "loc1",
                "cont1",
                "url1",
                startDate,
                startDate );
        appointments.add(ap1);
        return appointments;
    }
    
    public ArrayList<Customer> getCustomers() {
        ArrayList<Customer> customers;
        customers = new ArrayList<>();
        City city = new City(1,"new york");
        
        Address address = new Address(
                        777,
                        "777 street",
                        "a.address2",
                        city,
                        "777",
                        "7777-77777");
        Customer customer = new Customer(777,
                        "yevgeniy",
                        1,
                        address);
        customers.add(customer);
        return customers;
    }
    

    

  

   
}
