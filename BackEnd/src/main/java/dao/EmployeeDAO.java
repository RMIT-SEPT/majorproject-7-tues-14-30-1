package dao;

import dao.util.DatabaseUtils;
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
                        result.getString("first_name"), result.getString("last_name"),
                        result.getString("email"), result.getString("phone"),
                        result.getString("password")));
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
        return 0;
    }
}