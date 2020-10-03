package model;

import dao.SessionDAO;

import java.util.Arrays;

public class Session {
    private int employee_ID;
    private boolean timeOfDay[][];
    private boolean created = false;

    public Session(){
        this.timeOfDay = new boolean[7][24];
    }

    public Session(int employee_ID) {
        this.employee_ID = employee_ID;
        this.timeOfDay = new boolean[7][24];
    }

    public int getEmployee_ID() {
        return employee_ID;
    }

    public void setEmployee_ID(int employee_ID) {
        this.employee_ID = employee_ID;
    }

    public void setWorking(int day, int hour, boolean working){
        timeOfDay[day][hour]= working;
    }
    public boolean[][] getWorking(){
        return timeOfDay;
    }
    public void setCreated(boolean created){
        this.created=created;
    }
    public boolean getCreated(){
        return created;
    }
    public String toString(){
        return Arrays.deepToString(timeOfDay);
    }
    public int[][] getFree(Employee emp){
        int[][] isFree = new int[7][24];
        for (int day=0; day<7; day++){
            for (int hour=0; hour<24; hour++){
               if(!timeOfDay[day][hour]){
                   isFree[day][hour]=0; //They are neve availible at this time
               }
               else if (emp.isFree(hour,day)){
                   isFree[day][hour]=2;
               }
               else{
                   isFree[day][hour]=1;
               }
            }
        }
        return isFree;
    }
}
