package model;

import dao.BookingDAO;
import dao.SessionDAO;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;

public class Employee {
    private int employee_ID, business_ID, type;
    private String first_name, last_name, email, phone_number, password;
    private Session session;
    private ArrayList<Booking> bookings = new ArrayList<Booking>();

    public Employee(int employee_ID, int business_ID, int type, String first_name, String last_name,
                    String email, String phone_number, String password) {
        this.employee_ID = employee_ID;
        this.business_ID = business_ID;
        this.type = type;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.phone_number = phone_number;
        this.password = password;
    }


    public Employee(int business_ID, int type, String first_name, String last_name,
                    String email, String phone_number, String password) {
        this.business_ID = business_ID;
        this.type = type;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.phone_number = phone_number;
        this.password = password;
    }

    public Employee() {

    }

    public int getEmployee_ID() {
        return employee_ID;
    }

    public int getBusiness_ID() {
        return business_ID;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getEmail() {
        return email;
    }
    public void addBooking(Booking booking){
        bookings.add(booking);
        BookingDAO.createBooking(booking);
    }
    public String getPhone_number() {
        return phone_number;
    }

    public int getType(){return type;}

    public void setEmployee_ID(int employee_ID) {
        this.employee_ID = employee_ID;
    }

    public void setBusiness_ID(int business_ID) {
        this.business_ID = business_ID;
    }

    public void setFirst_name(String fName) {
        this.first_name = first_name;
    }

    public void setLast_name(String lName) {
        this.last_name = last_name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    public void setCost(double v) {
    }

    public void addSessions(Session ses1) {
    }

    public void initialiseSession(){
        this.session = SessionDAO.initialise(this.employee_ID);
    }

    public void findSessions(){
        this.session = SessionDAO.getSessionByEmployee_ID(this.employee_ID);
        if (!this.session.getCreated()){
            System.out.println("Session was found to be uncreated");
            this.initialiseSession();
        }
    }

    public Session getSession(){
        if (this.session==null) {
            this.findSessions();
        }
        return this.session;
    }

    public void setSession(Session sess){
        this.session = sess;
    }

    public int[] getNextSession(int day,int hour){
        int[] output = new int[2];
        int startDay = day;
        int startHour = hour-1;
        while (!(day==startDay && hour==startHour)){
            hour = (hour+1)%24;
            if (hour==0) {
                day = (day + 1) % 7;
            }
            if (this.isFree(hour, day)){
                output[1] = hour;
                output[0] = day;
                return output;
            }
        }
        output[0]=25;
        return output;
    }

    public boolean isFree(Timestamp dateTime) {
        //First we check if the employee is "rostered on"
        Calendar shift = Calendar.getInstance();
        shift.setTimeInMillis(dateTime.getTime());
        int hour, day;
        hour = (shift.get(Calendar.HOUR_OF_DAY));
        day = shift.get(Calendar.DAY_OF_WEEK)-1;
        if (!getSession().getWorking()[day][hour]) {
            return false;
        }
        //Then we check if the employee has an existing booking
        ArrayList<Booking> bookings = BookingDAO.getBookingsByEmployee_id(employee_ID);
        for (int i=0; i<bookings.size();i++){
            if (bookings.get(i).getDateTime().equals(dateTime)){
                return false;
            }
        }
        return true;
    }
    public boolean isFree(int hour, int day) {
        Calendar shift = translateHourDay(hour,day);
        if (!getSession().getWorking()[day][hour]) {
            return false;
        }
        //Then we check if the employee has an existing booking
        ArrayList<Booking> bookings = BookingDAO.getBookingsByEmployee_id(employee_ID);
        for (int i=0; i<bookings.size();i++){
            if (bookings.get(i).getDateTime().equals(new Timestamp(shift.getTimeInMillis()))){
                return false;
            }
        }
        return true;
    }
    private Calendar translateHourDay(int hour, int day){
        day++; //Need to turn day up because calander starts days at 1
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MINUTE,0);
        cal.set(Calendar.SECOND,0);
        cal.set(Calendar.MILLISECOND,0);
        if (hour>Calendar.HOUR_OF_DAY && day>=Calendar.DAY_OF_WEEK){
            cal.set(Calendar.HOUR_OF_DAY, hour);
            cal.set(Calendar.DAY_OF_WEEK, day);
        }
        else if ((hour<=Calendar.HOUR_OF_DAY && day==Calendar.DAY_OF_WEEK) || day<Calendar.DAY_OF_WEEK){
            cal.set(Calendar.HOUR_OF_DAY, hour);
            cal.set(Calendar.DAY_OF_WEEK, day);
            cal.add(Calendar.WEEK_OF_YEAR, 1);
        }
        day--;
        return cal;
    }
    public boolean makeNextBooking(int cust_id, int hour, int day){
        if (isFree(hour, day)){
            Timestamp ts = new Timestamp(translateHourDay(hour,day).getTimeInMillis());
            Booking newbooking = new Booking(cust_id, this.employee_ID, this.business_ID, ts);
            BookingDAO.createBooking(newbooking);
            return true;
        }
        else{
            return false;
        }
    }
}

