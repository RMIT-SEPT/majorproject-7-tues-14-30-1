package model;

public class Customer {
    private int customer_ID;
    private String fName, lName, phone_number, email, password;

    public Customer(int customer_ID, String fName, String lName, String phone_number,
                    String email, String password) {
        this.customer_ID = customer_ID;
        this.fName = fName;
        this.lName = lName;
        this.phone_number = phone_number;
        this.email = email;
        this.password = password;
    }

    public int getCustomer_ID() {
        return customer_ID;
    }

    public void setCustomer_ID(int customer_ID) {
        this.customer_ID = customer_ID;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
