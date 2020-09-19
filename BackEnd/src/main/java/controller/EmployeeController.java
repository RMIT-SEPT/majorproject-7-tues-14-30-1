package controller;

import controller.util.Status;

import dao.BusinessDAO;
import dao.CustomerDAO;
import dao.EmployeeDAO;
import io.javalin.http.Handler;
import model.Business;
import model.Customer;
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
            ctx.json(new Status(bus));
        } else {
            ctx.json(new Status("Employee does not exist"));
        }
    };

    public static Handler updateEmployee = ctx -> {

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

        String business_idAsString = ctx.formParam("business_id");
        if (business_idAsString == null) {
            ctx.json(new Status("No business id provided"));
            return;
        }
        int business_id = Integer.parseInt(business_idAsString);


        String employee_idAsString = ctx.formParam("employee_id");
        if (employee_idAsString == null) {
            ctx.json(new Status("No employee id provided"));
            return;
        }
        int employee_id = Integer.parseInt(employee_idAsString);

        String typeAsString = ctx.formParam("type");
        if (typeAsString == null) {
            ctx.json(new Status("No type provided"));
            return;
        }
        int type = Integer.parseInt(typeAsString);

        //Validating that the user requesting the update has permission to actually update the employee
        Employee emp = EmployeeDAO.checkLogin(ctx);
        if (emp==null){
            ctx.json(new Status("No account with those details"));
            return;
        }
        if (emp.getType()<3 || emp.getBusiness_ID() != business_id){ //3 is the admin level
            ctx.json(new Status("Account does not have permission to update this employee information"));
            return;
        }


        if (errormsg!= "You are missing: "){
            ctx.json(new Status(errormsg));
            return;
        }

        errormsg = "";
        //TODO add validation for password length, email @ existince and phone length, email existance
        EmployeeDAO.updateEmployee(employee_id, business_id, type, first_name, last_name, email, phone, password);
        ctx.json(new Status());
    };

    public static Handler createEmployee = ctx ->{
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

        String business_idAsString = ctx.formParam("business_id");
        if (business_idAsString == null) {
            ctx.json(new Status("No business id provided"));
            return;
        }
        int business_id = Integer.parseInt(business_idAsString);

        String typeAsString = ctx.formParam("type");
        if (typeAsString == null) {
            ctx.json(new Status("No type provided"));
            return;
        }
        int type = Integer.parseInt(typeAsString);

        //Validating that the user requesting the update has permission to actually update the employee
        Employee emp = EmployeeDAO.checkLogin(ctx);
        if (emp==null){
            ctx.json(new Status("No account with those details"));
            return;
        }
        if (emp.getType()<3 || emp.getBusiness_ID() != business_id){ //3 is the admin level
            ctx.json(new Status("Account does not have permission to create an employee"));
            return;
        }


        if (errormsg!= "You are missing: "){
            ctx.json(new Status(errormsg));
            return;
        }

        errormsg = "";
        //TODO add validation for password length, email @ existince and phone length, email existance
        Employee employee = EmployeeDAO.createEmployee(business_id, type, first_name, last_name, email, phone, password);
        ctx.json(new Status());
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
            return;
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
        Employee emp = EmployeeDAO.checkLogin(email, password);
        if (emp!=null){
            ctx.json(new Status(emp));
            return;
        }
        else{
            ctx.json(new Status("Incorrect username or password"));
            return;
        }
    };


}