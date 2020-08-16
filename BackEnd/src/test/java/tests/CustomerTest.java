package tests;

import model.Customer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {

    @Test
    public void cancelBookingFail() throws Exception {
        Customer customer = new Customer(1, "John", "Smith", "0412345654", "johnsmith@gmail.com", "***");
        // Booking booking  = new Booking(booking_ID: 1)
        // booking.setdate(Now+49hours)
        // Customer.addBooking(booking)
        Boolean cancelStatus = Customer.removeBooking(1);
        // Because the booking is more than 48 hours away this is true
        assert(cancelStatus == true);
    }

    @Test
    public void cancelBookingTrue() throws Exception {
        Customer customer = new Customer(1, "John", "Smith", "0412345654", "johnsmith@gmail.com", "***");
        // Booking booking  = new Booking(booking_ID: 1)
        // booking.setdate(Now+30hours)
        // Customer.addBooking(booking)
        Boolean cancelStatus = Customer.removeBooking(1);
        // Because the booking is less than 48 hours away this is false
        assert(cancelStatus == false);
    }

    @Test
    public void cancelBookingNone() throws Exception {
        //Testing for cancel booking when the customer has no booking.
        Customer customer = new Customer(1, "John", "Smith", "0412345654", "johnsmith@gmail.com", "***");
        // Booking booking  = new Booking(booking_ID: 1)
        // booking.setdate(Now+30hours)

        Boolean cancelStatus = Customer.removeBooking(1);
        // Because the booking is not made this is true.
        assert(cancelStatus == true);
    }

}