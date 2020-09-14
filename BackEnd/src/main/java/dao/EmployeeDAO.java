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

    public static int checkLogin(String email, String password) {
        //Returns 2 if employee, 3 if admin, 0 if none (in future 1 will be customer)
        try {
            // Here you prepare your sql statement
            String sql = "SELECT `email`, `password`, `type` FROM agme.employee WHERE `email` = '" + email + "';";

            // Execute the query
            Connection connection = DatabaseUtils.connectToDatabase();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);

            // If there is a result, that means that the email matches.
            if(result.next()) {
                // 2) Check if the password matches
                if (Utils.passwordIsValid(password, result.getString("password"))){
                    return result.getInt("type");
                }
            }

            // Close it
            DatabaseUtils.closeConnection(connection);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static Employee checkLogin(Context ctx) {
        String email, password;
        email = ctx.formParam("loginemail");
        password = ctx.formParam("loginpassword");
        if (email==null){
            return null;
        }
        if (password==null){
            return null;
        }
        int type = checkLogin(email, password);
        if (type==0){
            return null;
        }
        return getEmployeeByEmail(email);
    }

    public static Employee getEmployeeByEmail(String email){
        try {
            // Here you prepare your sql statement
            String sql = "SELECT * FROM agme.employee WHERE `email` = '" + email + "';";

            // Execute the query
            Connection connection = DatabaseUtils.connectToDatabase();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);

            // If there is a result, that means that the email matches.
            if(result.next()) {
                // 2) Check if the password matches
                return new Employee (result.getInt("employee_id"), result.getInt("business_id"), result.getInt("type"),
                        result.getString("first_name"), result.getString("last_name"),
                        result.getString("email"), result.getString("phone"),
                        result.getString("password"));
            }

            // Close it
            DatabaseUtils.closeConnection(connection);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Employee createEmployee(int business_id, int type, String first_name,
                                          String last_name, String email, String phone, String password) {
        Employee employee = new Employee(business_id, type, first_name, last_name, email, phone, password);
        String update_sql;
        update_sql = "INSERT INTO `agme`.`employee` ( `first_name`,`last_name`, `business_id`, `email`,`phone`, `type`, `password`) " +
                "VALUES('" + first_name + "' ,'" + last_name + "' ,'" + business_id + "','" +
                 email + "','" + phone + "', '" + type + "', '" + Utils.generateHashPassword(password) + "');";

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