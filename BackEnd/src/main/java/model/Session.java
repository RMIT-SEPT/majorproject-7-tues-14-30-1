package model;

public class Session {
    private int employee_ID;
    private boolean timeOfDay[][];

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
}
