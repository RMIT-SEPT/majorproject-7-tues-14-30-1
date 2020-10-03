package dao;

import controller.util.Utils;
import dao.util.DatabaseUtils;
import io.javalin.http.Context;
import model.Customer;
import model.Person;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CustomerDAO {
    public static final String SALT = "$2a$10$h.dl5J86rGH7I8bD9bZeZe";



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
}