package dao;

import dao.util.DatabaseUtils;
import model.Booking;
import model.Business;
import model.Employee;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {
    public static Booking getBookingByBooking_id(int booking_id) {
        // Fish out the results
        List<Booking> bookings = new ArrayList<>();

        try {
            // Here you prepare your sql statement
            String sql = "SELECT * FROM agme.booking WHERE booking_id = " + booking_id + ";";

            // Execute the query
            Connection connection = DatabaseUtils.connectToDatabase();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);

            // If you have multiple results, you do a while
            if(result.next()) {
                // 2) Add it to the list we have prepared
                bookings.add(new Booking (result.getInt("booking_id"), result.getInt("customer_id"),
                        result.getInt("employee_id"), result.getInt("business_id"), result.getDate("datetime")));
            }

            // Close it
            DatabaseUtils.closeConnection(connection);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        if(!bookings.isEmpty()) {
            return bookings.get(0);
        }
        // If we are here, something bad happened
        return null;
    }

    public static void createBooking(Booking booking){
        String update_sql;
        update_sql = "INSERT INTO `agme`.`booking` (`customer_id`,`business_id`,`employee_id`,`datetime`) VALUES" +
                "('" + booking.getCustomer_id() + "','" + booking.getBusiness_id() +"','" + booking.getEmployee_id() + "','" + booking.getDateTime() + "');";

        try {
            // Execute the query
            Connection connection = DatabaseUtils.connectToDatabase();
            Statement statement = connection.createStatement();
            statement.execute(update_sql);
            // Close it
            DatabaseUtils.closeConnection(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static ArrayList<Booking> getBookingsByCustomer_id(int customer_id) {
        String bookings;
        ArrayList<Booking> bookingsList;
        bookingsList = new ArrayList<>();
        bookings = "SELECT * FROM `agme` . `booking` WHERE `customer_id` = '" + customer_id + "';";

        try {
            // Execute the query
            Connection connection = DatabaseUtils.connectToDatabase();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(bookings);
            while(result.next()) {
                bookingsList.add(new Booking (result.getInt("booking_id"), result.getInt("customer_id"),
                        result.getInt("employee_id"), result.getInt("business_id"), result.getDate("datetime")));
            }
            // Close it
            DatabaseUtils.closeConnection(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bookingsList;
    }

    public static ArrayList<Booking> getBookingsByEmployee_id(int employee_id) {
        String bookings;
        ArrayList<Booking> bookingsList;
        bookingsList = new ArrayList<>();
        bookings = "SELECT * FROM `agme` . `booking` WHERE `employee_id` = '" + employee_id + "';";

        try {
            // Execute the query
            Connection connection = DatabaseUtils.connectToDatabase();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(bookings);
            while(result.next()) {
                bookingsList.add(new Booking (result.getInt("booking_id"), result.getInt("customer_id"),
                        result.getInt("employee_id"), result.getInt("business_id"), result.getDate("datetime")));
            }
            // Close it
            DatabaseUtils.closeConnection(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bookingsList;
    }
}
