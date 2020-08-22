package model;

public class Employee {
    private int employee_ID, business_ID;
    private String first_name, last_name, email, phone_number, password;

    public Employee(int employee_ID, int business_ID, String first_name, String last_name,
                    String email, String phone_number, String password) {
        this.employee_ID = employee_ID;
        this.business_ID = business_ID;
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
}

