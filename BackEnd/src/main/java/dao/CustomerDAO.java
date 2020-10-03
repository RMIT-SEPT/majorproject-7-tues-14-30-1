package dao;

import controller.util.Utils;
import dao.util.DatabaseUtils;
import io.javalin.http.Context;
import model.Customer;
import model.Person;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CustomerDAO {
    public static final String SALT = "$2a$10$h.dl5J86rGH7I8bD9bZeZe";

}