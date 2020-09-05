package controller;

import controller.util.Status;
import dao.CustomerDAO;
import dao.EmployeeDAO;
import io.javalin.http.Handler;
import model.Employee;

import java.util.Calendar;
import java.util.TimeZone;

public class EmployeeController {
    public static Handler getEmployee = ctx -> {
        String str_id = ctx.queryParam("id");
        if (str_id == null) {
            ctx.json(new Status("No ID provided"));
            return;
        }
        int id = Integer.parseInt(str_id);
        Employee bus = EmployeeDAO.getEmployeeByEmployee_ID(id);
        if (bus != null) {
            ctx.json(bus);
        } else {
            ctx.json(new Status("Employee does not exist"));
        }
    };
    public static Handler nextFreeSession = ctx -> {
        String str_id = ctx.queryParam("id");
        if (str_id == null) {
            ctx.json(new Status("No ID Provided"));
            return;
        }
        int id = Integer.parseInt(str_id);

        Employee emp = EmployeeDAO.getEmployeeByEmployee_ID(id);
        Calendar calendar = Calendar.getInstance();
        int day= calendar.get(Calendar.DAY_OF_WEEK)-1;
        int hour=calendar.get(Calendar.HOUR_OF_DAY)+1;
        int[] nextFree = new int[2];
        nextFree=emp.getNextSession(day,hour);
        if (nextFree[0]!=25){
            ctx.json("{'day':'" + nextFree[0] + "', 'hour':'" + nextFree[1] + "'}");
        }
        ctx.json(new Status("Worker has no free shifts"));
    };

    public static Handler checkLogin = ctx ->{
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
        int success = EmployeeDAO.checkLogin(email, password);
        if (success>0){
            ctx.json(new Status());
            return;
        }
        else{
            ctx.json(new Status("Incorrect username or password"));
            return;
        }
    };
}