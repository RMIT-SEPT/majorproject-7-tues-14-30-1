package dao;

import controller.util.Utils;
import dao.util.DatabaseUtils;
import io.javalin.http.Context;
import model.Customer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        update_sql = "INSERT INTO `agme`.`customer` (`first_name`,`last_name`,`phone`,`email`,`password`) VALUES('" + first_name + "' ,'" + last_name + "','" +
                "" + phone + "','" + email + "','" + Utils.generateHashPassword(password) + "');";

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

    public static Customer checkLogin(String email, String password) {
        try {
            // Here you prepare your sql statement
            String sql = "SELECT `customer_id`, `email`, `password` FROM agme.customer WHERE `email` = '" + email + "';";

            // Execute the query
            Connection connection = DatabaseUtils.connectToDatabase();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);

            // If there is a result, that means that the email matches.
            if(result.next()) {
                // 2) Check if the password matches
                if (Utils.passwordIsValid(password, result.getString("password"))){
                    return getCustomerByCustomer_ID(result.getInt("customer_id"));
                }
            }

            // Close it
            DatabaseUtils.closeConnection(connection);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Customer checkLogin(Context ctx) {
        String email, password;
        email = ctx.formParam("email");
        password = ctx.formParam("password");
        if (email==null){
            return null;
        }
        if (password==null){
            return null;
        }
        return checkLogin(email, password);
    }

    public static int getCustomer_idByEmail(String email){
        try {
            // Here you prepare your sql statement
            String sql = "SELECT `email`, `customer_id` FROM agme.customer WHERE `email` = '" + email + "';";

            // Execute the query
            Connection connection = DatabaseUtils.connectToDatabase();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);

            // If there is a result, that means that the email matches.
            if(result.next()) {
                // 2) Check if the password matches
                return result.getInt("customer_id");
            }

            // Close it
            DatabaseUtils.closeConnection(connection);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static boolean emailInUse(String email) {
        try {
            // Here you prepare your sql statement
            String sql = "SELECT `email` FROM agme.customer WHERE `email` = '" + email + "';";

            // Execute the query
            Connection connection = DatabaseUtils.connectToDatabase();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);

            // If there is a result, that means that the email matches.
            if(result.next()) {
                // 2) Check if the password matches
                return true;
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