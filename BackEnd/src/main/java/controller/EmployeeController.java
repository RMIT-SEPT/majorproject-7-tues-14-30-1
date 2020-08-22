package controller;

import dao.EmployeeDAO;
import io.javalin.http.Handler;
import model.Employee;

import java.util.Calendar;
import java.util.TimeZone;

public class EmployeeController {
    public static Handler getEmployee = ctx -> {
        String str_id = ctx.queryParam("id");
        if (str_id == null) {
            ctx.json("{'status':'failed', 'reason': 'No id provided'}");
            return;
        }
        int id = Integer.parseInt(str_id);
        Employee bus = EmployeeDAO.getEmployeeByEmployee_ID(id);
        if (bus != null) {
            ctx.json(bus);
        } else {
            ctx.json(new Employee(0, 0, "firstName", "lastName",
                    "fName.lName@blank.com", "0435 xxx xxx", "********"));
        }
    };
    public static Handler nextFreeSession = ctx -> {
        String str_id = ctx.queryParam("id");
        if (str_id == null) {
            ctx.json("{'status':'failed', 'reason': 'No id provided'}");
            return;
        }
        int id = Integer.parseInt(str_id);

        Employee emp = EmployeeDAO.getEmployeeByEmployee_ID(id);
        Calendar calendar = Calendar.getInstance();
        int day= calendar.get(Calendar.DAY_OF_WEEK)-1;
        int hour=calendar.get(Calendar.HOUR_OF_DAY)+1;
        int[] nextFree = new int[2];
        nextFree=emp.getNextSession(day,hour);
        if (nextFree[0]!=25){
            ctx.json("{'day':'" + nextFree[0] + "', 'hour':'" + nextFree[1] + "'}");
        }
        ctx.json("{'status':'failed', 'reason': 'worker has no free shifts'}");
    };
}