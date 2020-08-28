package controller;


import controller.util.Status;
import dao.CustomerDAO;
import io.javalin.http.Handler;
import model.Customer;

public class CustomerController {
    public static Handler getCustomer = ctx ->{
        String str_id = ctx.queryParam("id");
        if (str_id==null){
            ctx.json(new Status("No ID Provided"));
            return;
        }
        int id = Integer.parseInt(str_id);
        Customer bus = CustomerDAO.getCustomerByCustomer_ID(id);
        if (bus != null) {
            ctx.json(bus);
        }
        else{
            ctx.json(new Status("Customer does not exist"));
        }
    };
}
