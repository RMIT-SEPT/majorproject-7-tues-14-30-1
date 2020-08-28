package dao.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 * This class connects you to the database.
 * When you import the database, you should create it under the name "imbd",
 * with user "root" and database password "root1".
 *
 * If you know about DB password encryption and want to secure this a little bit
 * more, go ahead. But it is not required.
 */
public class DatabaseUtils {



    public static Connection connectToDatabase() throws Exception {
        // creates a Connection objecti
        Connection connection = null;

        try {
            // Prepare the information to connect (hard-coded)
            String url = "jdbc:mysql://theduggan.online/agme";
            String user = "dummy";
            String password = "password" ;

            // Connect through the Driver
            connection = DriverManager.getConnection(url, user, password);
        }
        catch (SQLException ex) {
            System.out.println("An error occurred. Maybe user/password is invalid.");
            ex.printStackTrace();
        }

        // Return what you got
        return connection;
    }



    public static void closeConnection(Connection connection) {
        if(connection != null) {
            try {
                connection.close();
            }
            catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }




}
