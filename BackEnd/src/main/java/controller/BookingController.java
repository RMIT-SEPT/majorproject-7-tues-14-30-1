package controller;

import controller.util.Status;
import dao.BookingDAO;
import dao.BusinessDAO;
import io.javalin.http.Handler;
import model.Booking;
import model.Business;

import java.sql.Date;
import java.text.SimpleDateFormat;

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
            ctx.json("{'status':'failed', 'reason': 'No `customer_id` provided'}");
            return;
        }
        int customer_id = Integer.parseInt(customer_idAsString);

        String business_idAsString = ctx.formParam("business_id");
        if (business_idAsString == null) {
            ctx.json("{'status':'failed', 'reason': 'No `business_id` provided'}");
            return;
        }
        int business_id = Integer.parseInt(business_idAsString);

        String employee_idAsString = ctx.formParam("employee_id");
        if (employee_idAsString == null) {
            ctx.json("{'status':'failed', 'reason': 'No `employee_id` provided'}");
            return;
        }
        int employee_id = Integer.parseInt(employee_idAsString);

        String dateTimeAsString = ctx.formParam("dateTime");
        if (dateTimeAsString == null) {
            ctx.json("{'status':'failed', 'reason': 'No `dateTime` provided'}");
            return;
        }
        Date dateTime = (Date) new SimpleDateFormat("dd/MM/yyyy").parse(dateTimeAsString);

        BookingDAO.createBooking(new Booking(customer_id, employee_id, business_id, dateTime));
        ctx.json("{'status':'success'}");
    };
}
