package controller.util;

public class Status {
    public String status;
    public String message;
    public Object payload;
    public Status(){
        this.status = "success";
        this.message = "Successfully completed task";
    }
    public Status(String status, String message){
        this.status = status;
        this.message = message;
    }
    public  Status( String message){
        this.status = "failed";
        this.message = message;
    }
    public Status(Object payload){
        this.status="success";
        this.payload=payload;
    }
}
