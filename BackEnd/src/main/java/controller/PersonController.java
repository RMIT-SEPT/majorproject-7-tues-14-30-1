package controller;

import controller.util.Status;
import dao.PersonDAO;
import io.javalin.http.Handler;
import model.Person;

//Checks data passed to validate
public class PersonController {
    public static Handler checkLogin = ctx -> {
        String errormsg = "You are missing: ";
        String password = ctx.formParam("loginpassword");
        if (password == null){
            errormsg+="`loginpassword` ";
        }
        String email = ctx.formParam("loginemail");
        if (email==null){
            errormsg+="`loginemail` ";
        }
        if (errormsg!= "You are missing: "){
            ctx.json(new Status(errormsg));
            return;
        }
        Person cus = PersonDAO.checkLogin(email, password);
        if (cus!=null){
            ctx.json(new Status(cus));
        }
        else{
            ctx.json(new Status("Incorrect username or password"));
        }
        return;
    };
}
