package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import controller.util.Status;
import dao.BusinessDAO;
import io.javalin.http.Handler;
import model.Business;
import model.Employee;

import java.util.ArrayList;

public class BusinessController {
    public static Handler getBusiness = ctx -> {
        String str_id = ctx.queryParam("id");
        if (str_id == null) {
            ctx.json(new Status("No `id` provided"));
            return;
        }
        int id = Integer.parseInt(str_id);
        Business bus = BusinessDAO.getBusinessByBusiness_id(id);
        if (bus != null) {
            ctx.json(bus);
        } else {
            ctx.json(new Status("No business with that ID exists"));
        }
    };

    public static Handler updateBusiness = ctx -> {
        //First get the id of the business that needs to change
        String str_id = ctx.formParam("id");
        //TODO Authenticate the request (only the admin of the business should be able to make changes to the business
        if (str_id == null) {
            ctx.json(new Status("No `id` provided"));
            return;
        }
        int id = Integer.parseInt(str_id);
        Business business = BusinessDAO.getBusinessByBusiness_id(id);
        String email = ctx.formParam("email");
        if (email != null) {
            business.setEmail(email);
        }
        String phone_number = ctx.formParam("phone_number");
        if (phone_number != null) {
            business.setPhone_number(phone_number);
        }
        String name = ctx.formParam("name");
        if (name != null) {
            business.setName(name);
        }
        BusinessDAO.updateBusiness(business);
        ctx.json(new Status());
    };


    public static Handler removeBusiness = ctx -> {
        String str_id = ctx.formParam("id");
        if (str_id == null) {
            ctx.json(new Status("No `id` provided"));
            return;
        }
        int id = Integer.parseInt(str_id);
        BusinessDAO.removeBusiness(id);
        ctx.json(new Status());
    };

    public static Handler createBusiness = ctx -> {
        String email = ctx.formParam("email");
        if (email == null) {
            ctx.json(new Status("No email provided"));
            return;
        }
        String phone_number = ctx.formParam("phone_number");
        if (phone_number == null) {
            ctx.json(new Status("No `phone_number` provided"));
            return;
        }
        String name = ctx.formParam("name");
        if (name == null) {
            ctx.json(new Status("No `name` provided"));
            return;
        }
        BusinessDAO.createBusiness(new Business(name, phone_number, email));
        ctx.json(new Status());
    };

    public static Handler searchBusiness = ctx -> {
        String searchString = ctx.queryParam("q");
        String sortString = ctx.queryParam("sort");
        String orderString = ctx.queryParam("order");

        if (searchString == null) {
            ctx.json(new Status("Please enter at least one character"));
            return;
        }
        ArrayList businesses = BusinessDAO.searchBusiness(searchString, sortString, orderString);
        ctx.json(new Status(businesses));
    };

    public static Handler getEmployees = ctx -> {
        String business_id = ctx.queryParam("q");
        if (business_id == null) {
            ctx.json(new Status("Please enter a business ID"));
            return;
        }
        int id = Integer.parseInt(business_id);
        ArrayList<Employee> employees = BusinessDAO.getEmployees(id);
        ctx.json(new Status(employees));
    };

}