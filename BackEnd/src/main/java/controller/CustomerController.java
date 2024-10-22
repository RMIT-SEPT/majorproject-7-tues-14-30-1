package controller;


import controller.util.Status;
import dao.CustomerDAO;
import dao.PersonDAO;
import io.javalin.http.Handler;
import model.Customer;
import model.Person;

//Checks the inputs in getCustomer
public class CustomerController {
    public static Handler getCustomer = ctx ->{
        String str_id = ctx.queryParam("id");
        if (str_id==null){
            ctx.json(new Status("No ID Provided"));
            return;
        }
        int id = Integer.parseInt(str_id);
        Person cus = PersonDAO.getPersonByPerson_ID(id);
        if (cus != null) {
            ctx.json(new Status(cus));
        }
        else{
            ctx.json(new Status("Customer does not exist"));
        }
    };

//    checks all fields for create customer as well as that email is not in use
    public static Handler createCustomer = ctx ->{
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
        if (PersonDAO.emailInUse(email)){
            ctx.json(new Status("That email is allready in use"));
            return;
        }
        //TODO add validation for password length, email @ existince and phone length
        Person cust = PersonDAO.createPerson(first_name, last_name, email,phone, password);
        ctx.json(new Status());
    };


}
