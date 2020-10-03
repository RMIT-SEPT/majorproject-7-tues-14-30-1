package model;

public class Customer extends Person{

    public Customer(String fName, String lName, String phone_number,
                    String email, String password) {
        this.first_name = fName;
        this.last_name = lName;
        this.phone_number = phone_number;
        this.email = email;
        this.password = password;
    }

    public Customer(int customer_ID, String fName,int type, String lName, String phone_number,
                    String email, String password) {
        this.ID = customer_ID;
        this.first_name = fName;
        this.type=type;
        this.last_name = lName;
        this.phone_number = phone_number;
        this.email = email;
        this.password = password;
    }

    public Customer() {

    }

    public static boolean removeBooking(int booking_id){
        return false;
    }
    }
