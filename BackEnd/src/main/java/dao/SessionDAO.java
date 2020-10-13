package dao;

import controller.util.Utils;
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
                session.setCreated(true);
                // If you have multiple results, you do a while
                while (result.next()) {
                    session.setEmployee_ID(result.getInt("employee_ID"));
                    session.setWorking(result.getInt("day"), result.getInt("hour"), result.getInt("working")==1);
                }


            // Close it
            DatabaseUtils.closeConnection(connection);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return session;
    }

    public static Session initialise(int employee_id){
        Session session = new Session();
        session.setEmployee_ID(employee_id);
        session.setCreated(true);
        try {
            // Here you prepare your sql statement
            String sql = "INSERT INTO `agme`.`session` (`employee_id`,`day`,`hour`,`working`)VALUES";

            for (int day=0; day<7;day++){
                for (int hour=0; hour<24; hour++){
                    sql += "(" + employee_id + " , " + day + "," + hour + ",0),";
                }
            }
            sql = sql.replaceAll(",$", "");
            sql+=";";
            // Execute the query
            Connection connection = DatabaseUtils.connectToDatabase();
            Statement statement = connection.createStatement();
            statement.execute(sql);

            // Close it
            DatabaseUtils.closeConnection(connection);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return session;
    }

    public static Session updateSession(int employee_id, ArrayList<int[]> arrList){
        Session session = new Session();
        session.setEmployee_ID(employee_id);
        session.setCreated(true);
        try {
            // Here you prepare your sql statement
            String sql;
            sql = "UPDATE session SET working = (case";

            for( int[] array : arrList) {
                sql += " when employee_id = " + array[0] + " day= " + array[1] + " hour= " + array[2] + " then " + array[3];
            }
            sql += " else working end) WHERE employee_id>0;";

            //Testing
            System.out.println(sql);

            // Execute the query
            Connection connection = DatabaseUtils.connectToDatabase();
            Statement statement = connection.createStatement();
            statement.execute(sql);

            // Close it
            DatabaseUtils.closeConnection(connection);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return session;
    }

}