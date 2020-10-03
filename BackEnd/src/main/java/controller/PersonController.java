package controller;

import controller.util.Status;
import dao.PersonDAO;
import io.javalin.http.Handler;
import model.Person;

public class PersonController {
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
