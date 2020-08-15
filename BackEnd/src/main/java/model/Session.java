package model;

public class Session {
    private int employee_ID;
    private int timeOfDay[];
    private enum daysOfWeek {
        MONDAY,
        TUESDAY,
        WEDNESDAY,
        THURSDAY,
        FRIDAY,
        SATURDAY,
        SUNDAY
    }

    public Session(int employee_ID) {
        this.employee_ID = employee_ID;
        this.timeOfDay = new int[24];
    }

    public int getEmployee_ID() {
        return employee_ID;
    }

    public void setEmployee_ID(int employee_ID) {
        this.employee_ID = employee_ID;
    }

    public int[] getTimeOfDay() {
        return timeOfDay;
    }

    public void setTimeOfDay(int[] timeOfDay) {
        this.timeOfDay = timeOfDay;
    }
}
