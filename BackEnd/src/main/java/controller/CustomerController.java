package controller;


import dao.CustomerDAO;
import io.javalin.http.Handler;
import model.Customer;

public class CustomerController {
    public static Handler getCustomer = ctx ->{
        int id = Integer.parseInt(ctx.queryParam("id"));
        Customer bus = CustomerDAO.getCustomerByCustomer_ID(id);
        if (bus != null) {
            ctx.json(bus);
        }
        else{
            ctx.json(new Customer(0, "FirstName", "LastName", "0435 xxx xxx",
                    "fName.lName@blank.com", "*********"));
        }
    };
}
