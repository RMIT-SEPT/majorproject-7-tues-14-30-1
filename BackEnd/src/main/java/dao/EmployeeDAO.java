package dao;

import controller.util.Utils;
import dao.util.DatabaseUtils;
import model.Customer;
import model.Employee;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {
    public static final String SALT = "$2a$10$h.dl5J86rGH7I8bD9bZeZe";

    public static Employee getEmployeeByEmployee_ID(int employee_ID) {
        // Fish out the results
        List<Employee> employee = new ArrayList<>();

        try {
            // Here you prepare your sql statement
            String sql = "SELECT * FROM agme.employee WHERE employee_ID = " + employee_ID + ";";

            // Execute the query
            Connection connection = DatabaseUtils.connectToDatabase();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);

            // If you have multiple results, you do a while
            if(result.next()) {
                // 2) Add it to the list we have prepared
                employee.add(new Employee (result.getInt("employee_id"), result.getInt("business_id"),
                        result.getInt("first_name"), result.getString("first_name"),
                        result.getString("last_name"), result.getString("email"),
                        result.getString("phone"), result.getString("password")));
            }
            // Close it
            DatabaseUtils.closeConnection(connection);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        if(!employee.isEmpty()) {
            return employee.get(0);
        }
        // If we are here, something bad happened
        return null;
    }

    public static Employee createEmployee(int employee_id, int business_id, int type, String first_name,
                                          String last_name, String email, String phone, String password) {
        Employee employee = new Employee(employee_id, business_id, type, first_name, last_name, email, phone, password);
        String update_sql;
        update_sql = "INSERT INTO `agme`.`employee` (`employee_id`,  `first_name`,`last_name`, `business_id`, `email`,`phone`, `type`, `password`) " +
                "VALUES('" + employee_id + "' ,'" + first_name + "' ,'" + last_name + "', ,'" + business_id + "''" +
                "" + email + "','" + phone + "', '" + type + "', '" + Utils.generateHashPassword(password) + "');";

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