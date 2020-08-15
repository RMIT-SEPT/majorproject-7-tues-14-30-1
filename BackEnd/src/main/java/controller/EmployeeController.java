package controller;

import dao.EmployeeDAO;
import io.javalin.http.Handler;
import model.Employee;

public class EmployeeController {
    public static Handler getEmployee = ctx ->{
        int id = Integer.parseInt(ctx.queryParam("id"));
        Employee bus = EmployeeDAO.getEmployeeByEmployee_ID(id);
        if (bus != null) {
            ctx.json(bus);
        }
        else{
            ctx.json(new Employee(0, 0, "firstName", "lastName",
                    "fName.lName@blank.com", "0435 xxx xxx", "********"));
        }
    };
}
