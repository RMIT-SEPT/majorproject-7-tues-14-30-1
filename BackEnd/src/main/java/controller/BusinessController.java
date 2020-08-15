package controller;

import dao.businessDAO;
import io.javalin.http.Handler;
import model.Business;

public class BusinessController {
    public static Handler getBusiness = ctx ->{
        String str_id = ctx.queryParam("id");
        if (str_id==null){
            ctx.json("{'status':'failed', 'reason': 'No id provided'}");
        }
        int id = Integer.parseInt(str_id);
        Business bus = businessDAO.getBusinessByBusiness_id(id);
        if (bus != null) {
            ctx.json(bus);
        }
        else{
            ctx.json(new Business(0, "no business exists", "04 xxxx xxxx", "none@none.com"));
        }
    };

    public static Handler updateBusiness = ctx ->{
        //First get the id of the business that needs to change
        String str_id = ctx.formParam("id");
        //TODO Authenticate the request (only the admin of the business should be able to make changes to the business
        if (str_id == null) {
            ctx.json("{'status':'failed', 'reason': 'No id provided'}");
        }
        int id = Integer.parseInt(str_id);
        Business business = businessDAO.getBusinessByBusiness_id(id);
        String email = ctx.formParam("email");
        if (email!=null){
            business.setEmail(email);
        }
        String phone_number = ctx.formParam("phone_number");
        if (phone_number!=null){
            business.setPhone_number(phone_number);
        }
        String name = ctx.formParam("name");
        if (name!=null){
            business.setName(name);
        }
        businessDAO.updateBusiness(business);
        ctx.json("{'status':'success'}");
    };


    public static Handler removeBusiness = ctx ->{
        String str_id = ctx.formParam("id");
        if (str_id == null) {
            ctx.json("{'status':'failed', 'reason': 'No id provided'}");
        }
        int id = Integer.parseInt(str_id);
        businessDAO.removeBusiness(id);
        ctx.json("{'status':'success'}");
    };

    public static Handler createBusiness = ctx ->{
        String email = ctx.formParam("email");
        if (email==null){
            ctx.json("{'status':'failed', 'reason': 'No `email` provided'}");
        }
        String phone_number = ctx.formParam("phone_number");
        if (phone_number==null){
            ctx.json("{'status':'failed', 'reason': 'No `phone_number` provided'}");
        }
        String name = ctx.formParam("name");
        if (name==null){
            ctx.json("{'status':'failed', 'reason': 'No `name` provided'}");
        }
        businessDAO.createBusiness(new Business(name, phone_number, email));
        ctx.json("{'status':'success'}");
    };

}
