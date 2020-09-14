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
    public String toString(){
        return this.status +": " +  this.message;
    }
    @Override
    public boolean equals(Object o){
        if (this == o){
            return true;
        }
        if (!(o instanceof Status)){
            return false;
        }
        Status s = (Status) o;
        return ((s.message == this.message)
        && (s.payload == this.payload)
        && (s.status == this.status));
    }
}
