package dao;

import dao.util.DatabaseUtils;
import model.Business;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BusinessDAO {
    public static final String SALT = "$2a$10$h.dl5J86rGH7I8bD9bZeZe";

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

}