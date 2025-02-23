package dao;

import com.mysql.cj.x.protobuf.MysqlxCrud;
import dao.util.DatabaseUtils;
import model.Business;
import model.Employee;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BusinessDAO {
    public static final String SALT = "$2a$10$h.dl5J86rGH7I8bD9bZeZe";

//    Return a business based on the inputted business ID
    public static Business getBusinessByBusiness_id(int business_id) {
        // Fish out the results
        List<Business> business = new ArrayList<>();

        try {
            // Here you prepare your sql statement
            String sql = "SELECT * FROM agme.business WHERE business_id = " + business_id + ";";

            // Execute the query
            Connection connection = DatabaseUtils.connectToDatabase();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);

            // If you have multiple results, you do a while
            if(result.next()) {
                // 2) Add it to the list we have prepared
                business.add(new Business (result.getInt("business_id"), result.getString("name"),
                        result.getString("phone_number"), result.getString("email")));
            }

            // Close it
            DatabaseUtils.closeConnection(connection);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        if(!business.isEmpty()) {
            return business.get(0);
        }
        // If we are here, something bad happened
        return null;
    }

//    Update business information, such as name, phone, email
    public static void updateBusiness(Business business) {
        int business_id;
        business_id = business.getBusiness_id();
        String update_sql;
        update_sql = "UPDATE `agme`.`business` SET `name` = '" + business.getName() + "', `phone_number` = '" + business.getPhone_number() + "', " +
                "`email` = '" + business.getEmail() + "'  WHERE `business_id` = '" + business_id + "';";

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

//    Delete a business
    public static void removeBusiness(int id){
        String update_sql;
        update_sql= "DELETE FROM `agme`.`business` WHERE `business_id` = '" + id + "';";

        try {
            // Execute the query
            Connection connection = DatabaseUtils.connectToDatabase();
            Statement statement = connection.createStatement();
            statement.execute(update_sql);
            // Close it
            DatabaseUtils.closeConnection(connection);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

//    Create a new business
    public static void createBusiness(Business business){
        String update_sql;
        update_sql = "INSERT INTO `agme`.`business` (`name`,`phone_number`,`email`) VALUES('" + business.getName() + "' ,'" + business.getPhone_number() + "','" + business.getEmail() + "');";

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

//    Search for a business, enter search term, DESC or ASC, and what to sort by, you can also just search without worrying about the order
    public static ArrayList<Business> searchBusiness(String searchTerm, String sort, String order) {
        String select_business;
        ArrayList<Business> arrayList;
        arrayList = new ArrayList<Business>();
        String DESC = "desc";

        if(sort != null) {
            if(order.equals(DESC)) {
                select_business = "SELECT * FROM `agme` . `business` WHERE `name` LIKE '%" + searchTerm + "%' ORDER BY " + sort + " DESC";
            } else {
                select_business = "SELECT * FROM `agme` . `business` WHERE `name` LIKE '%" + searchTerm + "%' ORDER BY " + sort + " ASC";
            }

        } else {
            select_business = "SELECT * FROM `agme` . `business` WHERE `name` LIKE '%" + searchTerm + "%'";
        }

        try {
            // Execute the query
            Connection connection = DatabaseUtils.connectToDatabase();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(select_business);
            while(result.next()) {
                arrayList.add(new Business (result.getInt("business_id"), result.getString("name"),
                        result.getString("phone_number"), result.getString("email")));
            }
            // Close it
            DatabaseUtils.closeConnection(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return arrayList;
    }

//    Search employees by business and, if chosen, email as well
    public static ArrayList<Employee> getEmployees(int business_id, String email) {
        String employees;
        ArrayList<Employee> employeeList;
        employeeList = new ArrayList<Employee>();

        if (email != null) {
            employees = "SELECT * FROM `agme` . `person` WHERE `business_id` = '" + business_id + "' AND `email` LIKE '%" + email + "%';";
        } else {
            employees = "SELECT * FROM `agme` . `person` WHERE `business_id` = '" + business_id + "';";
        }
        System.out.println(employees);
        try {
            // Execute the query
            Connection connection = DatabaseUtils.connectToDatabase();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(employees);
            while(result.next()) {
                employeeList.add(new Employee (result.getInt("person_id"), result.getInt("business_id"),
                        result.getInt("type"), result.getString("first_name"),
                        result.getString("last_name"), result.getString("email"),
                        result.getString("phone"), result.getString("password")));
            }
            // Close it
            DatabaseUtils.closeConnection(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return employeeList;
    }
}