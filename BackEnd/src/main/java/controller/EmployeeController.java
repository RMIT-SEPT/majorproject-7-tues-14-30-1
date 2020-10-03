package controller;

import controller.util.Status;

import dao.BusinessDAO;
import dao.CustomerDAO;
import dao.EmployeeDAO;
import dao.SessionDAO;
import io.javalin.http.Handler;
import model.Business;
import model.Customer;
import model.Employee;

import java.util.ArrayList;
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
            ctx.json(new Status(nextFree));
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

    public static Handler makeNextBooking = ctx ->{
        Customer cus = CustomerDAO.checkLogin(ctx);

        System.out.println(ctx.formParamMap());


        if (cus==null){
            ctx.json(new Status("Incorrect username or password"));
            return;
        }
        String emp_id_str = ctx.formParam("employee_id");
        if (emp_id_str==null || emp_id_str.equals("")){
            ctx.json(new Status("Please provide `employee_id`"));
            return;
        }
        int emp_id = Integer.parseInt(emp_id_str);
        String hour_str = ctx.formParam("hour");
        if (hour_str==null || hour_str.equals("")){
            ctx.json(new Status("Please provide `hour`"));
            return;
        }
        int hour = Integer.parseInt(hour_str);
        String day_str = ctx.formParam("day");
        if (day_str==null || day_str.equals("")){
            ctx.json(new Status("Please provide `day`"));
            return;
        }
        int day = Integer.parseInt(day_str);
        Employee emp = EmployeeDAO.getEmployeeByEmployee_ID(emp_id);
        if (emp.makeNextBooking(cus.getCustomer_ID(),hour,day)){
            ctx.json(new Status());
            return;
        }
        else{
            ctx.json(new Status("Employee is not availible during that time."));
            return;
        }
    };

    public static Handler updateSession = ctx -> {


        String employee_idAsString = ctx.formParam("employee_id");
        if (employee_idAsString == null) {
            ctx.json(new Status("No employee id provided"));
            return;
        }
        int employee_id = Integer.parseInt(employee_idAsString);

        String session_data = ctx.formParam("session_data");
        if (session_data == null) {
            ctx.json(new Status("No session data provided"));
            return;
        }

        ArrayList<int[]> session_arrList;
        session_arrList = new ArrayList<>();

        String[] session_first_split = session_data.split(",");

        for (String str : session_first_split) {
            String[] session_second_split = str.split(".");

            int[] session_array = new int[]{Integer.parseInt(session_second_split[0]), Integer.parseInt(session_second_split[1]),
                    Integer.parseInt(session_second_split[2]), Integer.parseInt(session_second_split[3])};

            session_arrList.add(session_array) ;
        }

        //Validating that the user requesting the update has permission to actually update the employee
        Employee emp = EmployeeDAO.checkLogin(ctx);
        if (emp==null){
            ctx.json(new Status("No account with those details"));
            return;
        }

//        Need to validate correct business and admin!

//        if (emp.getType()<3 || emp.getBusiness_ID() != business_id){ //3 is the admin level
//            ctx.json(new Status("Account does not have permission to update this employee information"));
//            return;
//        }

        SessionDAO.updateSession(employee_id, session_arrList);

        ctx.json(new Status());
    };


}