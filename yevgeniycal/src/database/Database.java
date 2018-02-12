/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

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
import java.util.ArrayList;
import javax.xml.transform.Result;

/**
 *
 * @author yevgeniy
 */
public class Database {

    private static Database database = null;

    Connection conn = null;

    private void connectMysql() {

        String driver = "com.mysql.jdbc.Driver";
        String db = "U04iex";
        String url = "jdbc:mysql://52.206.157.109/" + db;
        String user = "U04iex";
        String pass = "53688251641";

        //  String driver = "com.mysql.jdbc.Driver";
        //  String db = "U04iex";
        //  String url = "jdbc:mysql://localhost/" + db;
        //  String user = "U04iex";
        //  String pass = "53688251641";
        try {
            try {
                Class.forName(driver);
            } catch (ClassNotFoundException e) {

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
        Address address = new Address(1, "477 Maddison Ave", "", new City(1, "New York"), "11111", "11111111111");
        Address address2 = new Address(2, "1 Park Ave", "", new City(1, "Bostom"), "22222", "2222222222");

        Customer[] customers = {new Customer(1, "Yev", 1, address),
            new Customer(2, "JOhn", 2, address2)};
        return customers;
    }

    public static Database getInstance() {
        if (database == null) {
            database = new Database();

            database.connectMysql();
        }
        return database;
    }

    public int getUserId(String username, String password) {
        PreparedStatement statement;
        int userId = -1;
        try {
            statement = conn.prepareStatement(
                    "select userId from user where userName=? and password=?");
            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet rs = statement.executeQuery();
                        
            if ( rs.next() ) {
                userId = rs.getInt("userId");
            }
            
            rs.close();
            statement.close();
 
        } catch (SQLException e) {
            System.out.println("SQLException:" + e.getMessage());
            System.out.println("SQLState:" + e.getSQLState());
            System.out.println("VendorError:" + e.getErrorCode());
        }
        return userId;
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
    
    public ArrayList<Customer> getCustomers() {
        PreparedStatement statement;
        ArrayList<Customer> customers;
        customers = new ArrayList<>();
        
        ArrayList<Appointment> appointments;

        try {
            statement = conn.prepareStatement(
                    "select c.customerID,"
                    + "c.customerName, "
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
            while (rs.next()) {
                City city = new City(rs.getInt("a.cityId"), rs.getString("ci.city"));
                Country country = new Country(rs.getString("co.country"));

                Address address = new Address(
                        rs.getInt("c.addressId"),
                        rs.getString("a.address"),
                        rs.getString("a.address2"),
                        city,
                        rs.getString("a.postalCode"),
                        rs.getString("a.phone"));

                Customer customer = new Customer(rs.getInt("c.customerID"),
                        rs.getString("c.customerName"),
                        rs.getInt("c.active"),
                        address);

                appointments = getAppointments(customer.getCustomerId());

                customer.setAppointments(appointments);

                customers.add(customer);
            }
            rs.close();
            statement.close();

        } catch (SQLException e) {
            System.out.println("SQLException:" + e.getMessage());
            System.out.println("SQLState:" + e.getSQLState());
            System.out.println("VendorError:" + e.getErrorCode());
        }
        return customers;
    }

    ArrayList<Appointment> getAppointments(int customerId) {
        ArrayList<Appointment> appointments = new ArrayList<>();
        PreparedStatement statement;
        try {
            statement = conn.prepareStatement(
                    "select customerId, appointmentId,title, description, location, contact, url, start, end"
                    + " from appointment where customerId = ?"
            );

            statement.setInt(1, customerId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {

                Appointment appointment = new Appointment(
                        rs.getInt("customerId"),
                        rs.getInt("appointmentId"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getString("location"),
                        rs.getString("contact"),
                        rs.getString("url"),
                        rs.getDate("start"),
                        rs.getDate("end"));
                appointments.add(appointment);
            }
            rs.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println("SQLException:" + e.getMessage());
            System.out.println("SQLState:" + e.getSQLState());
            System.out.println("VendorError:" + e.getErrorCode());
        }

        return appointments;

    }

    public int saveAddress(String address,
            String address2,
            int cityId,
            String postalCode,
            String phone,
            int userId) {
        PreparedStatement statement;
        int addressId = -1;
        
        String userIdString = Integer.toString(userId);
        
        try {
            String sql = "insert into address(address,address2,cityId,"
                    + "postalCode,phone,createDate,createdBy,lastUpdate,lastUpdateBy)"
                    + " values(?,?,?,?,?,NOW(),?,NOW(),?)";

            statement = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, address);
            statement.setString(2, address2);
            statement.setInt(3, cityId);
            statement.setString(4, postalCode);
            statement.setString(5, phone);
            statement.setString(6, userIdString);
            statement.setString(7, userIdString);
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();

            if (rs.next()) {
                addressId = rs.getInt(1);
            }

        } catch (SQLException e) {
            System.out.println("SQLException:" + e.getMessage());
            System.out.println("SQLState:" + e.getSQLState());
            System.out.println("VendorError:" + e.getErrorCode());
        }
        return addressId;
    }

    public int saveCustomer(String customerName,
            int addressId
    ) {

        PreparedStatement statement;
        int customerId = -1;

        try {

            String sql = "insert into customer(customerName,addressId,active,"
                    + "createDate,createdBy,lastUpdate,lastUpdateBy)"
                    + " values(?,?,?,NOW(),?,NOW(),?)";

            statement = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, customerName);
            statement.setInt(2, addressId);
            statement.setInt(3, LoginController.getCurrentUser().getUserId());
            statement.setString(4, LoginController.getCurrentUser().getUserName());
            statement.setString(5, LoginController.getCurrentUser().getUserName());
            
            statement.executeUpdate();
            
            ResultSet rs = statement.getGeneratedKeys();

            if (rs.next()) {

                customerId = rs.getInt(1);

            }

        } catch (SQLException e) {
            System.out.println("SQLException:" + e.getMessage());
            System.out.println("SQLState:" + e.getSQLState());
            System.out.println("VendorError:" + e.getErrorCode());
        }
        return customerId;
    }
}
