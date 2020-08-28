package dao;

import dao.util.DatabaseUtils;
import model.Customer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {
    public static final String SALT = "$2a$10$h.dl5J86rGH7I8bD9bZeZe";

    public static Customer getCustomerByCustomer_ID(int customer_ID) {
        // Fish out the results
        List<Customer> customer = new ArrayList<>();

        try {
            // Here you prepare your sql statement
            String sql = "SELECT * FROM agme.customer WHERE customer_ID = " + customer_ID + ";";

            // Execute the query
            Connection connection = DatabaseUtils.connectToDatabase();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);

            // If you have multiple results, you do a while
            if(result.next()) {
                // 2) Add it to the list we have prepared
                customer.add(new Customer (result.getInt("customer_id"), result.getString("first_name"),
                        result.getString("last_name"), result.getString("phone"),
                        result.getString("email"), result.getString("password")));
            }

            // Close it
            DatabaseUtils.closeConnection(connection);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        if(!customer.isEmpty()) {
            return customer.get(0);
        }
        // If we are here, something bad happened
        return null;
    }

    public static Customer createCustomer(String first_name, String last_name, String phone, String email, String password) {
        Customer cust = new Customer(first_name, last_name, phone, email, password);
        String update_sql;
        update_sql = "INSERT INTO `agme`.`customer` (`first_name`,`last_name`,`phone`,`email`,`password`) VALUES('" + first_name + "' ,'" + last_name + "','" + phone + "','" + email + "','" + password + "');";

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
        return cust;
    }

    public static boolean checkLogin(String email, String password) {
        try {
            // Here you prepare your sql statement
            String sql = "SELECT `email`, `password` FROM agme.customer WHERE `email` = '" + email + "';";

            // Execute the query
            Connection connection = DatabaseUtils.connectToDatabase();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);

            // If there is a result, that means that the email matches.
            if(result.next()) {
                // 2) Check if the password matches
                if (password.equals(result.getString("password"))){
                    return true;
                }
            }

            // Close it
            DatabaseUtils.closeConnection(connection);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}