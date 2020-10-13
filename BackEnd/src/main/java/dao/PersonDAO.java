package dao;

import controller.util.Utils;
import dao.util.DatabaseUtils;
import dao.CustomerDAO;
import io.javalin.http.Context;
import model.Customer;
import model.Employee;
import model.Person;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PersonDAO {
    public static Person createPerson(int business_id, int type, String first_name,
                                          String last_name, String email, String phone, String password) {
        Person person;
        if (type==1){
            person = new Customer(first_name, type, last_name, phone, email, password);
        }
        else{
            person = new Employee(business_id, type, first_name, last_name, email, phone, password);
        }
        String update_sql;
        update_sql = "INSERT INTO `agme`.`person` ( `first_name`,`last_name`, `business_id`, `email`,`phone`, `type`, `password`) " +
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
        return person;
    }

    public static Person createPerson(String first_name,
                                       String last_name, String email, String phone, String password){
        return createPerson(0, 1, first_name, last_name, email, phone, password);
    }


    public static Person checkLogin(String email, String password) {
        //Returns 2 if employee, 3 if admin, 0 if none (in future 1 will be customer)
        try {
            // Here you prepare your sql statement
            String sql = "SELECT  `email`, `password`, `type` FROM agme.person WHERE `email` = '" + email + "';";
            System.out.println(sql);
            // Execute the query
            Connection connection = DatabaseUtils.connectToDatabase();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);

            // If there is a result, that means that the email matches.
            if(result.next()) {
                // 2) Check if the password matches
                if (Utils.passwordIsValid(password, result.getString("password"))){
                    return getPersonByEmail(result.getString("email"));
                }
                else{
                    System.out.println("Wrong password");
                }
            }
            else{
                System.out.println("no result");
            }

            // Close it
            DatabaseUtils.closeConnection(connection);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Person checkLogin(Context ctx) {
        String email, password;
        email = ctx.formParam("loginemail");
        password = ctx.formParam("loginpassword");
        if (email==null){
            return null;
        }
        if (password==null){
            return null;
        }
        Person person = checkLogin(email, password);
        if (person.getType() == 0){
            return null;
        }
        return person;
    }

    public static boolean emailInUse(String email) {
        try {
            // Here you prepare your sql statement
            String sql = "SELECT `email` FROM agme.person WHERE `email` = '" + email + "';";
            System.out.println(sql);

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

    public static Person getPersonByEmail(String email) {
        List<Person> person = new ArrayList<>();

        try {
            // Here you prepare your sql statement
            String sql = "SELECT * FROM agme.person WHERE email = '" + email + "';";
            System.out.println(sql);
            // Execute the query
            Connection connection = DatabaseUtils.connectToDatabase();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);

            // If you have multiple results, you do a while
            if(result.next()) {
                // 2) Add it to the list we have prepared
                if (result.getInt("type")==1){ //means that the person is a customer
                    person.add(new Customer (result.getInt("person_id"), result.getString("first_name"),
                            result.getInt("type"),
                            result.getString("last_name"), result.getString("phone"),
                            result.getString("email"), result.getString("password")));
                }
                else{
                    person.add(new Employee (result.getInt("person_id"), result.getInt("business_id"), result.getInt("type"),
                            result.getString("first_name"), result.getString("last_name"),
                            result.getString("email"), result.getString("phone"),
                            result.getString("password")));
                }

            }

            // Close it
            DatabaseUtils.closeConnection(connection);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        if(!person.isEmpty()) {
            return person.get(0);
        }
        // If we are here, something bad happened
        return null;
    }

    public static int getIdByEmail(String email){
        try {
            // Here you prepare your sql statement
            String sql = "SELECT `email`, `person_id` FROM agme.person WHERE `email` = '" + email + "';";

            // Execute the query
            Connection connection = DatabaseUtils.connectToDatabase();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);

            // If there is a result, that means that the email matches.
            if(result.next()) {
                // 2) Check if the password matches
                return result.getInt("person_id");
            }

            // Close it
            DatabaseUtils.closeConnection(connection);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static Person getPersonByPerson_ID(int person_ID) {
        // Fish out the results
        List<Person> person = new ArrayList<>();

        try {
            // Here you prepare your sql statement
            String sql = "SELECT * FROM agme.person WHERE person_ID = " + person_ID + ";";

            // Execute the query
            Connection connection = DatabaseUtils.connectToDatabase();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);

            // If you have multiple results, you do a while
            if(result.next()) {
                // 2) Add it to the list we have prepared
                if (result.getInt("type")==1){ //means that the person is a customer
                    person.add(new Customer (result.getInt("person_id"), result.getString("first_name"),
                            result.getInt("type"),
                            result.getString("last_name"), result.getString("phone"),
                            result.getString("email"), result.getString("password")));
                }
                else{
                    person.add(new Employee (result.getInt("person_id"), result.getInt("business_id"), result.getInt("type"),
                            result.getString("first_name"), result.getString("last_name"),
                            result.getString("email"), result.getString("phone"),
                            result.getString("password")));
                }
            }

            // Close it
            DatabaseUtils.closeConnection(connection);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        if(!person.isEmpty()) {
            return person.get(0);
        }
        // If we are here, something bad happened
        return null;
    }
}
