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
            config.registerPlugin(new RouteOverviewPlugin("/routes"));
        }).start(getHerokuAssignedPort());
        app.routes(() -> {
            app.get(Web.business, BusinessController.getBusiness);
            app.post(Web.business, BusinessController.updateBusiness);
            app.delete(Web.business, BusinessController.removeBusiness);
            app.put(Web.business, BusinessController.createBusiness);
            app.get(Web.booking, BookingController.getBooking);
            app.get(Web.customer, CustomerController.getCustomer);
            app.get(Web.employee, EmployeeController.getEmployee);
            app.get(Web.searchBusiness, BusinessController.searchBusiness);
            app.get(Web.employeeNextFreeSession, EmployeeController.nextFreeSession);
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