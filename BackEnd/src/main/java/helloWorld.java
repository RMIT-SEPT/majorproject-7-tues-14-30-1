import controller.BusinessController;
import controller.paths.Web;
import io.javalin.Javalin;
import io.javalin.core.util.RouteOverviewPlugin;

public class helloWorld {
    public static void main(String[] args) {
        Javalin app = Javalin.create(config -> {
            config.addStaticFiles("/public");
            config.registerPlugin(new RouteOverviewPlugin("/routes"));
        }).start(getHerokuAssignedPort());
        app.routes(() -> {
            app.get(Web.business, BusinessController.getBusiness);
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