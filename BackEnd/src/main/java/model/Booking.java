package model;

import java.sql.Timestamp;

public class Booking {
    private int booking_id, customer_id, employee_id, business_id;
    private Timestamp dateTime;
    public Booking(int booking_id, int customer_id, int employee_id, int business_id, Timestamp dateTime){
        this.booking_id = booking_id;
        this.customer_id = customer_id;
        this.employee_id = employee_id;
        this.business_id = business_id;
        this.dateTime = dateTime;
    }

    public Booking(int customer_id, int employee_id, int business_id, Timestamp dateTime) {
        this.customer_id = customer_id;
        this.employee_id = employee_id;
        this.business_id = business_id;
        this.dateTime = dateTime;
    }

    public Booking() {

    }

    public int getBooking_id(){
        return this.booking_id;
    }
    public int getCustomer_id(){
        return this.customer_id;
    }
    public int getEmployee_id(){
        return this.employee_id;
    }
    public int getBusiness_id(){
        return this.business_id;
    }
    public Timestamp getDateTime(){
        return this.dateTime;
    }
}
