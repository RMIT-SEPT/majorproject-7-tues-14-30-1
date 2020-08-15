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
            result.next();
            // 2) Add it to the list we have prepared
            employee.add(new Employee (result.getInt("employee_ID"), result.getInt("business_ID"),
                    result.getString("fName"), result.getString("lName"),
                    result.getString("email"), result.getString("phone_number"),
                    result.getString("password")));

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

}