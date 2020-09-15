package controller;


import controller.util.Status;
import dao.CustomerDAO;
import io.javalin.http.Handler;
import model.Customer;
import org.mindrot.jbcrypt.BCrypt;

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

    public static Handler createCustomer = ctx ->{
        System.out.println(ctx.formParamMap());
        String errormsg = "You are missing: ";
        String password = ctx.formParam("password");
        if (password == null){
            errormsg+="password ";
        }
        String first_name = ctx.formParam("first_name");
        if (first_name==null){
            errormsg+="first_name ";
        }
        String last_name = ctx.formParam("last_name");
        if (last_name==null){
            errormsg+="last_name ";
        }
        String phone = ctx.formParam("phone");
        if (phone==null){
            errormsg+="phone ";
        }
        String email = ctx.formParam("email");
        if (email==null){
            errormsg+="email ";
        }
        if (errormsg!= "You are missing: "){
            ctx.json(new Status(errormsg));
            return;
        }
        errormsg = "";
        if (CustomerDAO.emailInUse(email)){
            ctx.json(new Status("That email is allready in use"));
            return;
        }
        //TODO add validation for password length, email @ existince and phone length
        Customer cust = CustomerDAO.createCustomer(first_name, last_name, phone, email, password);
        ctx.json(new Status());
    };

    public static Handler checkLogin = ctx -> {
        String errormsg = "You are missing: ";
        String password = ctx.formParam("password");
        if (password == null){
            errormsg+="password ";
        }
        String email = ctx.formParam("email");
        if (email==null){
            errormsg+="email ";
        }
        if (errormsg!= "You are missing: "){
            ctx.json(new Status(errormsg));
            return;
        }
        boolean success = CustomerDAO.checkLogin(email, password);
        if (success){
            ctx.json(new Status());
        }
        else{
            ctx.json(new Status("Incorrect username or password"));
        }
        return;
    };
}
