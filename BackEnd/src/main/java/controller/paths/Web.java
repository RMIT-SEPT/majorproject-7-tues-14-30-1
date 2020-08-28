package controller.paths;

public class Web {
    //basic resources
    public static final String business = "/api/business";
    public static final String customer = "/api/customer";
    public static final String employee = "/api/employee";
    public static final String booking = "/api/booking";

    //Business related
    public static final String searchBusiness = "/api/business/searchBusiness";
    public static final String getEmployees = "/api/business/getEmployees";
    //Customer related
    public static final String customerLogin = "/api/customer/login";

    //Booking related
    public static final String getBookingByCustomer = "/api/booking/getByCustomer";
    public static final String getBookingByEmployee = "/api/booking/getByEmployee";
    
    //Session related

    //employee related
    public static final String employeeNextFreeSession = "/api/employee/nextFreeSession";

}