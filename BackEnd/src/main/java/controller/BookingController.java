package controller;

import controller.util.Status;
import dao.BookingDAO;
import dao.BusinessDAO;
import dao.CustomerDAO;
import dao.EmployeeDAO;
import io.javalin.http.Handler;
import model.Booking;
import model.Business;
import model.Customer;
import model.Employee;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class BookingController {
    public static Handler getBooking = ctx ->{
        String str_id = ctx.queryParam("id");
        if (str_id==null){
            ctx.json(new Status("No ID Provided"));
            return;
        }
        System.out.println(str_id);
        int id = Integer.parseInt(str_id);
        Booking booking = BookingDAO.getBookingByBooking_id(id);
        int[] customer_id = CustomerDAO.checkLogin(ctx);
        if (customer_id[0] == 0){
            ctx.json(new Status("Incorrect `email` or `password`"));
            return;
        }
        else if (booking.getCustomer_id() != customer_id[1]){
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
        int[] customer_id = CustomerDAO.checkLogin(ctx);
        if (customer_id[0] == 0){
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
        BookingDAO.createBooking(new Booking(customer_id[1], employee_id, business_id, dateTime));
        ctx.json(new Status());
    };

    public static Handler getBookingsByCustomer_id = ctx -> {
        int customer_id[] = CustomerDAO.checkLogin(ctx);
        if (customer_id[0] == 0) {
            ctx.json(new Status("Please provide an accurate `email` and `password`"));
            return;
        }
        ArrayList<Booking> bookings = BookingDAO.getBookingsByCustomer_id(customer_id[1]);
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
