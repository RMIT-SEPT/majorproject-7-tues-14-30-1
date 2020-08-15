package model;

public class Business {
    private String name, phone_number, email;
    private int business_id;

    public Business(int business_id, String name, String phone_number, String email) {
        this.name = name;
        this.phone_number = phone_number;
        this.email = email;
        this.business_id = business_id;
    }

    public int getBusiness_id(){
        return business_id;
    }
    public String getName(){
        return name;
    }
    public String getPhone_number(){
        return phone_number;
    }
    public String getEmail(){
        return email;
    }

    public String toString(){
        return "hello";
    }
}
