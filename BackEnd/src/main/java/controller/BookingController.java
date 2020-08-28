package controller;

import controller.util.Status;
import dao.BookingDAO;
import dao.BusinessDAO;
import io.javalin.http.Handler;
import model.Booking;
import model.Business;
import model.Customer;
import model.Employee;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

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
        if (booking != null) {
            ctx.json(booking);
        }
        else{
            ctx.json(new Booking(0,0,0,0, new Date(0)));
        }
    };

    public static Handler createBooking = ctx -> {

        String customer_idAsString = ctx.formParam("customer_id");
        if (customer_idAsString == null) {
            ctx.json(new Status("No customer id provided"));
            return;
        }
        int customer_id = Integer.parseInt(customer_idAsString);

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

        String dateTimeAsString = ctx.formParam("dateTime");
        if (dateTimeAsString == null) {
            ctx.json(new Status("No 'dateTime' provided"));
            return;
        }
        Date dateTime = (Date) new SimpleDateFormat("dd/MM/yyyy").parse(dateTimeAsString);

        BookingDAO.createBooking(new Booking(customer_id, employee_id, business_id, dateTime));
        ctx.json(new Status());
    };

    public static Handler getBookingsByCustomer_id = ctx -> {
        String customer_id = ctx.queryParam("id");
        if (customer_id == null) {
            ctx.json(new Status("Please enter a customer ID"));
            return;
        }

        int id = Integer.parseInt(customer_id);
        ArrayList<Booking> bookings = BookingDAO.getBookingsByCustomer_id(id);
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
