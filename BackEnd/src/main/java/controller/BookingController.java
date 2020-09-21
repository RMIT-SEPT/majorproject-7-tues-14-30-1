package controller;

import controller.util.Status;
import dao.BookingDAO;
import dao.CustomerDAO;
import dao.EmployeeDAO;
import io.javalin.http.Handler;
import model.Booking;
import model.Customer;
import model.Employee;

import java.util.ArrayList;
import java.util.Calendar;

public class BookingController {
    public static Handler getBooking = ctx ->{
        String str_id = ctx.queryParam("id");
        if (str_id==null){
            ctx.json(new Status("No ID Provided"));
            return;
        }
        int id = Integer.parseInt(str_id);
        Booking booking = BookingDAO.getBookingByBooking_id(id);
        Customer cus = CustomerDAO.checkLogin(ctx);
        if (cus==null){
            Employee emp = EmployeeDAO.checkLogin(ctx);
            if (emp==null){
                System.out.println("Emp null & user nulll");
                ctx.json(new Status("Incorrect `email` or `password`"));
                return;
            }
            if (emp.getEmployee_ID()!=id && !(booking.getBusiness_id()==emp.getBusiness_ID() && emp.getType()==3)){ //3 is the admin level
                System.out.println("user null and not employee or admin");
                ctx.json(new Status("Account does not have permission to view this booking"));
                return;
            }
        }
        else if (booking.getCustomer_id() != cus.getCustomer_ID()){
            System.out.println("customer incorrect");
            ctx.json(new Status("You do not have permission to view this booking"));
            return;
        }
        if (booking != null) {
            ctx.json(new Status(booking));
        }
        else{
            ctx.json(new Status("No booking with that id exists"));
        }
    };

    public static Handler createBooking = ctx ->{

        /*String customer_idAsString = ctx.formParam("customer_id");
        if (customer_idAsString == null) {
            ctx.json(new Status("No customer id provided"));
            return;
        }
        int customer_id = Integer.parseInt(customer_idAsString);*/
        Customer cus = CustomerDAO.checkLogin(ctx);
        if (cus==null){
            ctx.json(new Status("Incorrect `email` or `password`"));
            return;
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
        Employee emp = EmployeeDAO.getEmployeeByEmployee_ID(employee_id);

        String dateTimeAsString = ctx.formParam("dateTime");
        if (dateTimeAsString == null) {
            ctx.json(new Status("No 'dateTime' provided"));
            return;
        }
        java.sql.Timestamp dateTime = java.sql.Timestamp.valueOf(dateTimeAsString);
        //Truncate dateTime to get rid of minutes,seconds,miliseconds
        Calendar bookingTime = Calendar.getInstance();
        bookingTime.setTime(dateTime);
        bookingTime.set(Calendar.MINUTE, 0);
        bookingTime.set(Calendar.SECOND, 0);
        bookingTime.set(Calendar.MILLISECOND, 0);

        //Check that the employee is free for the booking
        if(!emp.isFree(dateTime)){
            ctx.json(new Status("Employee is not available"));
            return;
        }
        BookingDAO.createBooking(new Booking(cus.getCustomer_ID(), employee_id, business_id, dateTime));
        ctx.json(new Status());
    };

    public static Handler getBookingsByCustomer_id = ctx -> {
        Customer cus = CustomerDAO.checkLogin(ctx);
        if (cus==null) {
            ctx.json(new Status("Please provide an accurate `email` and `password`"));
            return;
        }
        ArrayList<Booking> bookings = BookingDAO.getBookingsByCustomer_id(cus.getCustomer_ID());
        ctx.json(new Status(bookings));
    };

    public static Handler getBookingsByEmployee_id = ctx -> {
        String employee_id = ctx.queryParam("id");
        if (employee_id == null) {
            ctx.json(new Status("Please enter an employee ID"));
            return;
        }
        int id = Integer.parseInt(employee_id);
        ArrayList<Booking> bookings = BookingDAO.getBookingsByEmployee_id(id);
        ctx.json(new Status(bookings));
    };
}
