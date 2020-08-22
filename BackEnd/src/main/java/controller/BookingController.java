package controller;

import dao.BookingDAO;
import io.javalin.http.Handler;
import model.Booking;

import java.sql.Date;

public class BookingController {
    public static Handler getBooking = ctx ->{
        String str_id = ctx.queryParam("id");
        if (str_id==null){
            ctx.json("{'status':'failed', 'reason': 'No id provided'}");
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
}
