package model;

import dao.SessionDAO;

public class Employee {
    private int employee_ID, business_ID;
    private String fName, lName, email, phone_number, password;
    private Session session;

    public Employee(int employee_ID, int business_ID, String fName, String lName,
                    String email, String phone_number, String password) {
        this.employee_ID = employee_ID;
        this.business_ID = business_ID;
        this.fName = fName;
        this.lName = lName;
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

    public String getfName() {
        return fName;
    }

    public String getlName() {
        return lName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setEmployee_ID(int employee_ID) {
        this.employee_ID = employee_ID;
    }

    public void setBusiness_ID(int business_ID) {
        this.business_ID = business_ID;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public void setlName(String lName) {
        this.lName = lName;
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
}

