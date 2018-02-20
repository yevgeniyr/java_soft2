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
import java.sql.Timestamp;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import javax.xml.transform.Result;

/**
 *
 * @author yevgeniy
 */
public class Database {

    private static Database database = null;
    private static Database inMemDB = null;
    private static final boolean useInMemoryDB = false;
    Connection conn = null;

    private void connect() {

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

    public Date utcToLocalDate(Date date) {
        ZoneId defaultZoneId = ZoneId.systemDefault();
        //1. Convert Date -> Instant
        Instant instant = date.toInstant();
        System.out.println("instant : " + instant); //Zone : UTC+0

        //2. Instant + system default time zone + toLocalDate() = LocalDate
        LocalDate localDate = instant.atZone(defaultZoneId).toLocalDate();
        return java.sql.Date.valueOf(localDate);
    }

    public static Database getInstance() {
        if (useInMemoryDB) {
            if (inMemDB == null) {
                inMemDB = new MemDB();
            }
            return inMemDB;
        }

        if (database == null) {
            database = new Database();

            database.connect();
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

            if (rs.next()) {
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
        PreparedStatement statement;
        ArrayList<Customer> customers;
        customers = new ArrayList<>();

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

    public ArrayList<Appointment> getAppointments() {
        ArrayList<Appointment> appointments = new ArrayList<>();

        PreparedStatement statement;
        try {
            statement = conn.prepareStatement(
                    "select appointmentID,a.customerId,title,description,location,contact,url,start,end,"
                    + "customerName, c.active, d.addressId as addressId, active, address, address2, ci.cityId as cityId, "
                    + "postalCode, "
                    + "phone,  city, country from appointment a, customer c, address d, city ci, "
                    + "country co where a.customerId = c.customerID"
                    + " and c.addressId = d.addressID and ci.cityID = d.cityId and "
                    + "co.countryID = ci.countryId order by start"
            );

            //statement.setInt(1, customerId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                ZonedDateTime startDate;
                ZonedDateTime endDate;

                java.sql.Timestamp tsStart = rs.getTimestamp("start");

                ZoneId localZoneId = ZoneId.systemDefault();

                ZonedDateTime utcDateTime = tsStart.toLocalDateTime().atZone(ZoneId.of("UTC"));

                startDate = utcDateTime.withZoneSameInstant(localZoneId);

                java.sql.Timestamp tsEnd = rs.getTimestamp("end");

                utcDateTime = tsEnd.toLocalDateTime().atZone(ZoneId.of("UTC"));

                endDate = utcDateTime.withZoneSameInstant(localZoneId);

                City city = new City(rs.getInt("cityId"), rs.getString("city"));

                Address address = new Address(
                        rs.getInt("d.addressId"),
                        rs.getString("address"),
                        rs.getString("address2"),
                        city,
                        rs.getString("postalCode"),
                        rs.getString("phone"));

                Customer newCustomer = new Customer(rs.getInt("a.customerId"),
                        rs.getString("customerName"),
                        rs.getInt("active"), address);

                Appointment appointment = new Appointment(
                        newCustomer,
                        rs.getInt("appointmentId"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getString("location"),
                        rs.getString("contact"),
                        rs.getString("url"),
                        startDate,
                        endDate
                );
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

            statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
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

            statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
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

    public void updateAppointment(
            int appointmentId,
            int customerId,
            String title,
            String description,
            String location,
            String contact,
            String url,
            ZonedDateTime startDate,
            ZonedDateTime endDate
    ) {
        PreparedStatement statement;
        try {
            String sql = "update appointment set "
                    + "  customerId = ?,"
                    + "  title = ?,"
                    + "  description = ?,"
                    + "  location =?,"
                    + "  contact =?,"
                    + "  url =?,"
                    + "  start = ?,"
                    + "  end = ? where appointmentId = ?";

            statement = conn.prepareStatement(sql);
            statement.setInt(1, customerId);
            statement.setString(2, title);
            statement.setString(3, description);
            statement.setString(4, location);
            statement.setString(5, contact);
            statement.setString(6, url);
            statement.setTimestamp(7, getUTCTimestamp(startDate));
            statement.setTimestamp(8, getUTCTimestamp(endDate));
            statement.setInt(9, appointmentId);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("SQLException:" + e.getMessage());
            System.out.println("SQLState:" + e.getSQLState());
            System.out.println("VendorError:" + e.getErrorCode());
        }
    }

    Timestamp getUTCTimestamp(ZonedDateTime date) {

        ZonedDateTime utcStart = date.withZoneSameInstant(ZoneId.of("UTC"));
        Timestamp tsStart = Timestamp.valueOf(utcStart.toLocalDateTime());
        return tsStart;
    }

    public int saveAppointment(String title,
            String description,
            String location,
            String contact,
            String url,
            ZonedDateTime startDate,
            ZonedDateTime endDate,
            int customerId) {
        PreparedStatement statement;
        int appointmentId = -1;

        try {
            String sql = "insert into appointment (customerId,"
                    + "title,description,location,contact,url,start,end,createDate,createdBy,lastUpdate,lastUpdateBy) "
                    + "values(?,?,?,?,?,?,?,?,NOW(),?,NOW(),?)";

            statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, customerId);
            statement.setString(2, title);
            statement.setString(3, description);
            statement.setString(4, location);
            statement.setString(5, contact);
            statement.setString(6, url);
            statement.setTimestamp(7, getUTCTimestamp(startDate));
            statement.setTimestamp(8, getUTCTimestamp(endDate));
            statement.setString(9, LoginController.getCurrentUser().getUserName());
            statement.setString(10, LoginController.getCurrentUser().getUserName());

            statement.executeUpdate();

            ResultSet rs = statement.getGeneratedKeys();

            if (rs.next()) {
                appointmentId = rs.getInt(1);
            }

        } catch (SQLException e) {
            System.out.println("SQLException:" + e.getMessage());
            System.out.println("SQLState:" + e.getSQLState());
            System.out.println("VendorError:" + e.getErrorCode());

        }
        return appointmentId;
    }

    public void removeAppointment(int appointmentId) {
        PreparedStatement statement;

        try {
            String sql = "delete from appointment where appointmentId = ?";

            statement = conn.prepareStatement(sql);
            statement.setInt(1, appointmentId);
            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("SQLException:" + e.getMessage());
            System.out.println("SQLState:" + e.getSQLState());
            System.out.println("VendorError:" + e.getErrorCode());

        }
    }

}
