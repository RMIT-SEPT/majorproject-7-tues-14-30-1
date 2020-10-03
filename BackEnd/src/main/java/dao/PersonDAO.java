package dao;

import controller.util.Utils;
import dao.util.DatabaseUtils;
import dao.CustomerDAO;
import io.javalin.http.Context;
import model.Employee;
import model.Person;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class PersonDAO {

    public static Person checkLogin(String email, String password) {
        //Returns 2 if employee, 3 if admin, 0 if none (in future 1 will be customer)
        try {
            // Here you prepare your sql statement
            String sql = "SELECT  `email`, `password`, `type` FROM agme.employee WHERE `email` = '" + email + "';";

            // Execute the query
            Connection connection = DatabaseUtils.connectToDatabase();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);

            // If there is a result, that means that the email matches.
            if(result.next()) {
                // 2) Check if the password matches
                if (Utils.passwordIsValid(password, result.getString("password"))){
                    if (result.getInt("type")==1){//If they are a customer
                        return CustomerDAO.getCustomerByEmail(result.getString("emai"));
                    }
                    return EmployeeDAO.getEmployeeByEmail(result.getString("email"));
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
}
