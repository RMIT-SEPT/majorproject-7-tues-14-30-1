package dao;

import controller.util.Utils;
import dao.util.DatabaseUtils;
import io.javalin.http.Context;
import model.Customer;
import model.Employee;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {
    public static final String SALT = "$2a$10$h.dl5J86rGH7I8bD9bZeZe";


//    Update employee information
    public static Employee updateEmployee(int employee_id, int business_id, int type, String first_name,
                                          String last_name, String email, String phone, String password) {
        Employee employee = new Employee(employee_id, business_id, type, first_name, last_name, email, phone, password);
        String update_sql;
        update_sql = "UPDATE `agme`.`employee` SET `type` = '" + type + "',  `first_name` = '" + first_name + "',  `last_name` = '" + last_name + "'" +
                " `email` = '" + email + "', `phone` = '" + phone + "', `password` = '" + Utils.generateHashPassword(password) + "'  WHERE `employee_id` = '" + employee_id + "';";

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
        return employee;
    }
}