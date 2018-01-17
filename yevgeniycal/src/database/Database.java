/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import Model.Address;
import Model.City;
import Model.Country;
import Model.Customer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author yevgeniy
 */
public class Database {

    private static Database database = null;
    
    
    Connection conn = null;

    private void connectMysql() {

//        String driver = "com.mysql.jdbc.Driver";
//        String db = "U04iex";
//        String url = "jdbc:mysql://52.206.157.109/" + db;
//        String user = "U04iex";
//        String pass = "53688251641";

        String driver = "com.mysql.jdbc.Driver";
        String db = "U04iex";
        String url = "jdbc:mysql://localhost/" + db;
        String user = "U04iex";
        String pass = "53688251641";

        try {
            try {
                Class.forName(driver);
            }
            catch (ClassNotFoundException e) {
            
            }
            conn = DriverManager.getConnection(url, user, pass);
            System.out.println("Connected to database:" + db);
        } catch (SQLException e) {
            System.out.println("SQLException:" + e.getMessage());
            System.out.println("SQLState:" + e.getSQLState());
            System.out.println("VendorError:" + e.getErrorCode());
        }
    }

    public Customer[] getCustomers2() {
        Address address = new
                 Address(1, "477 Maddison Ave", "", new City(1,"New York"), "11111", "11111111111");
        Address address2 = new
                 Address(2, "1 Park Ave", "", new City(1,"Bostom"), "22222", "2222222222");
        
    
        Customer [] customers = { new Customer(1,"Yev",1, address),
        new Customer(2, "JOhn", 2, address2)};
        return customers;
    }
   
    
    public static Database getInstance() {
        if ( database == null ) {
            database = new Database();
            
            database.connectMysql();
        }
        return database;
    }
    
    
    public boolean findUserWithPassword(String username, String password) {
        PreparedStatement statement;
        try {
            statement = conn.prepareStatement(
                    "select count(*) as total from user where userName=? and password=?");
            statement.setString(1,username);
            statement.setString(2, password);
            
            ResultSet rs = statement.executeQuery();
            rs.next();
            boolean retVal = (rs.getInt("total") == 1);
            
            rs.close();
            statement.close();
            return retVal;
        }
          
        catch (SQLException e) {
            System.out.println("SQLException:" + e.getMessage());
            System.out.println("SQLState:" + e.getSQLState());
            System.out.println("VendorError:" + e.getErrorCode());
        }
        return false;
    }
    
    
    public ArrayList<Customer> getCustomers() {
        PreparedStatement statement;    
        ArrayList<Customer> customers;
        customers = new ArrayList<>();
        
        try {
            statement = conn.prepareStatement(
                    "select customerID,"
                        + "customerName, "
                        + "c.addressId,"
                        + "c.active,"
                        + "a.address,"
                        + "a.address2,"
                        + "a.cityId,"
                        + "a.postalCode,"
                        + "a.phone,"
                        + "ci.city,"
                        + "co.country"
                        + " from customer c,"
                            + "address a,"
                            + "city ci,"
                            + "country co"
                        + " where c.addressId = a.addressID"
                            + " and a.cityId = ci.cityID"
                            + " and ci.countryId = co.countryID");
            
            ResultSet rs = statement.executeQuery();
            while ( rs.next() ) {
                    City city = new City( rs.getInt("a.cityId"),rs.getString("ci.city"));
                    Country country = new Country(rs.getString("co.country"));
                    
                    Address address = new Address(
                            rs.getInt("c.addressId"),
                            rs.getString("a.address"),
                            rs.getString("a.address2"),
                            city,
                            rs.getString("a.postalCode"),
                            rs.getString("a.phone"));
                    
                    Customer customer = new Customer
                                (rs.getInt("c.customerId"),
                                        rs.getString("c.customerName"),
                                        rs.getInt("c.active"), address);
                    
                    customers.add(customer);
                            
           
                         
            }
            rs.close();
            statement.close();
           
                        

        }
        catch (SQLException e) {
            System.out.println("SQLException:" + e.getMessage());
            System.out.println("SQLState:" + e.getSQLState());
            System.out.println("VendorError:" + e.getErrorCode());
        }
        return customers;
    }
}