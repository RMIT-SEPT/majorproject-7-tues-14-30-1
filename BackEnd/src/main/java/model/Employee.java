package model;

import dao.SessionDAO;

public class Employee {
    private int employee_ID, business_ID, type;
    private String first_name, last_name, email, phone_number, password;
    private Session session;

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
            System.out.println("Session was null once");
            this.findSessions();
        }
        return this.session;
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
            if (this.session.getWorking()[day][hour]){
                output[0] = hour;
                output[1] = day;
                return output;
            }
        }
        output[0]=25;
        return output;
    }
}

