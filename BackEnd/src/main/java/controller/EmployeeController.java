package controller;

import dao.EmployeeDAO;
import io.javalin.http.Handler;
import model.Employee;

public class EmployeeController {
    public static Handler getEmployee = ctx ->{
        String str_id = ctx.queryParam("id");
        if (str_id==null){
            ctx.json("{'status':'failed', 'reason': 'No id provided'}");
            return;
        }
        int id = Integer.parseInt(str_id);
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
