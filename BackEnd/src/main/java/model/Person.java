package model;

public class Person {
    protected int ID, type;
    protected String first_name, last_name, phone_number, email, password;
    public int getType(){
        return type;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getfirst_name() {
        return first_name;
    }

    public void setfName(String fName) {
        this.first_name = fName;
    }

    public String getlast_name() {
        return last_name;
    }

    public void setlName(String lName) {
        this.last_name = lName;
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
