package dao;

import dao.util.DatabaseUtils;
import model.Session;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SessionDAO {
    public static final String SALT = "$2a$10$h.dl5J86rGH7I8bD9bZeZe";

    public static Session getSessionByEmployee_ID(int employee_ID) {
        // Fish out the results
        Session session = new Session();
        try {
            // Here you prepare your sql statement
            String sql = "SELECT * FROM agme.session WHERE employee_ID = " + employee_ID + ";";

            // Execute the query
            Connection connection = DatabaseUtils.connectToDatabase();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            if (result.next()) {
                session.setEmployee_ID(result.getInt("employee_ID"));

                // If you have multiple results, you do a while
                while (result.next()) {
                    session.setWorking(result.getInt("day"), result.getInt("hour"), result.getInt("working")==1);
                }
            }
            // Close it
            DatabaseUtils.closeConnection(connection);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return session;
    }

}