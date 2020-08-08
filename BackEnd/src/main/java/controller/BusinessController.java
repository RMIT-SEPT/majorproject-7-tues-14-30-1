package controller;

import dao.businessDAO;
import io.javalin.http.Handler;
import model.Business;

public class BusinessController {
    public static Handler getBusiness = ctx ->{
        Business bus = businessDAO.getBusinessByBusiness_id(1);
        ctx.json(bus);
    };
}
