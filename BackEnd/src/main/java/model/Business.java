package model;

public class Business {
    public String name, phone_number, email;
    public int business_id;

    public Business(int business_id, String name, String phone_number, String email) {
        this.name = name;
        this.phone_number = phone_number;
        this.email = email;
        this.business_id = business_id;
    }

    public String toString(){
        return "hello";
    }
}
