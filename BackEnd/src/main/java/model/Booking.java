package model;

import java.sql.Date;

public class Booking {
    private int booking_id, customer_id, employee_id, business_id;
    private Date dateTime;
    public Booking(int booking_id, int customer_id, int employee_id, int business_id, Date dateTime){
        this.booking_id = booking_id;
        this.customer_id = customer_id;
        this.employee_id = employee_id;
        this.business_id = business_id;
        this.dateTime = dateTime;
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
    public Date getDateTime(){
        return this.dateTime;
    }
}
