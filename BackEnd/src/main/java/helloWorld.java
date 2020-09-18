import controller.BookingController;
import controller.BusinessController;
import controller.CustomerController;
import controller.EmployeeController;
import controller.paths.Web;
import io.javalin.Javalin;
import io.javalin.core.util.RouteOverviewPlugin;
import model.Business;
import model.Customer;

public class helloWorld {
    public static void main(String[] args) {
        Javalin app = Javalin.create(config -> {
            config.addStaticFiles("/public");
            config.enableCorsForAllOrigins();
            config.registerPlugin(new RouteOverviewPlugin("/routes"));
        }).start(getHerokuAssignedPort());
        app.routes(() -> {
            //Basic resources
            app.get(Web.business, BusinessController.getBusiness);
            app.post(Web.business, BusinessController.updateBusiness);
            app.post(Web.employee, EmployeeController.updateEmployee);
            app.delete(Web.business, BusinessController.removeBusiness);
            app.put(Web.business, BusinessController.createBusiness);
            app.get(Web.booking, BookingController.getBooking);
            app.put(Web.booking, BookingController.createBooking);
            app.get(Web.customer, CustomerController.getCustomer);
            app.put(Web.customer, CustomerController.createCustomer);
            app.put(Web.employee, EmployeeController.createEmployee);
            app.get(Web.employee, EmployeeController.getEmployee);
            //Specific functions
            app.get(Web.customerLogin, CustomerController.checkLogin);
            app.get(Web.employeeLogin, EmployeeController.checkLogin);
            app.get(Web.searchBusiness, BusinessController.searchBusiness);
            app.get(Web.getEmployees, BusinessController.getEmployees);
            app.get(Web.employeeNextFreeSession, EmployeeController.nextFreeSession);
            app.get(Web.getBookingByCustomer, BookingController.getBookingsByCustomer_id);
            app.get(Web.getBookingByEmployee, BookingController.getBookingsByEmployee_id);
        });
    }
    public static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 7000;
    }
}