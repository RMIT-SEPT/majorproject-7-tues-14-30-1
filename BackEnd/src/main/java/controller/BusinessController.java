package controller;

import dao.businessDAO;
import io.javalin.http.Handler;
import model.Business;

public class BusinessController {
    public static Handler getBusiness = ctx ->{
        int id = Integer.parseInt(ctx.queryParam("id"));
        Business bus = businessDAO.getBusinessByBusiness_id(id);
        if (bus != null) {
            ctx.json(bus);
        }
        else{
            ctx.json(new Business(0, "no business exists", "04 xxxx xxxx", "none@none.com"));
        }
    };
}
