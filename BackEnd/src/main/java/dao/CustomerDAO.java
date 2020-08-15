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
            result.next();
            // 2) Add it to the list we have prepared
            customer.add(new Customer (result.getInt("customer_ID"), result.getString("fName"),
                    result.getString("lName"), result.getString("phone_number"),
                    result.getString("email"), result.getString("password")));

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

}