package ua.edu.sumdu.j2se.sergeySavchenko.tasks;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * class "Task", describes the task
 *
 * @author Sergey Savchenko
 */
public class Task implements Cloneable, Serializable {
    public static final int MILLI_SECONDS = 1000;
    private String title;
    private Date time;
    private Date start;
    private Date end;
    private int interval;
    private boolean active;

    /**
     * constructor for creating non-repeating tasks
     *
     * @param title name of task
     * @param time  start time of non-repeating task
     */
    public Task(String title, Date time) {        
        this.title = title;
        this.time = time;
        start = new Date(0);
        end = new Date(0);
        interval = 0;
        active = false;
    }

    /**
     * constructor for creating repeating tasks
     *
     * @param title    name of task
     * @param start    start time of repeating task
     * @param end      end time of repeating task
     * @param interval period of time between repeating tasks
     */
    public Task(String title, Date start, Date end, int interval) {
        String message = "Time or time interval can't be negative";
        if (!end.after(start) || (interval <= 0))
            throw new IllegalArgumentException(message);
        this.title = title;
        this.start = start;
        this.end = end;
        this.interval = interval * MILLI_SECONDS;
        time = new Date(0);
        active = false;
    }

    /**
     * method for reading name of task
     *
     * @return name of task
     */
    public String getTitle() {
        return title;
    }

    /**
     * method for setting name of task
     *
     * @param title name of task
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * method for getting activity of task
     *
     * @return activity of task
     */
    public boolean isActive() {
        return active;
    }

    /**
     * method for setting activity of task
     *
     * @param active activity of task
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * method for getting start time of task
     *
     * @return start time of task
     */
    public Date getTime() {
        return time;
    }

    /**
     * method for setting repetitiveness of task
     * (from repeating to non-repeating)
     *
     * @param time time of task
     */
    public void setTime(Date time) {
        this.time = time;
        start = time;
        end = time;
        interval = 0;
        active = false;
    }

    /**
     * method for getting start time of task
     *
     * @return start time of task
     */
    public Date getStartTime() {
        return start;
    }

    /**
     * method for getting end time of task
     *
     * @return end time of task
     */
    public Date getEndTime() {
        return end;
    }

    /**
     * method for getting interval between tasks
     *
     * @return period of time between tasks
     */
    public int getRepeatInterval() {
        return interval / MILLI_SECONDS;
    }

    /**
     * method for changing repetitiveness of task
     * (from non-repeating to repeating)
     *
     * @param start    start time of task
     * @param end      end time of task
     * @param interval period of time between repeating tasks
     */
    public void setTime(Date start, Date end, int interval) {
        String message 
       = "End time can't be less than start time or interval can't be negative";
        if ((!end.after(start)) || (interval <= 0)) 
                                    throw new IllegalArgumentException(message);
        this.start = start;
        this.end = end;
        this.interval = interval * MILLI_SECONDS;
        time = start;
        active = false;
    }

    /**
     * method for getting repetitiveness of task
     *
     * @return task repeats or not
     */
    public boolean isRepeated() {
        return interval != 0;
    }

    /**
     * method for gettiung time of next task
     *
     * @param current set current time
     * @return time of next task
     */
    public Date nextTimeAfter(Date current) {
        Date nextTime = start;        
        if (isActive()) {
            if (isRepeated()) {
                if (start.after(current)) { return start; 
                } else if (!end.before(current)) {
                    while (!nextTime.after(current)) {
                        nextTime = new Date(nextTime.getTime() + interval);
                    }
                    if (!end.before(nextTime)) {
                        return nextTime;
                    } else return null; 
                    } else return null;
            } else if (time.after(current)) { return time; 
            } else return null;
        } else return null;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Task task = (Task) obj;
        if (isRepeated() && task.isRepeated()) {
            if (active != task.active) return false;
            if (!start.equals(task.start)) return false;
            if (!end.equals(task.end)) return false;
            if (interval != task.interval) return false;
        } else {
            if (active != task.active) return false;
            if (!time.equals(task.time)) return false;
        }
        return title.equals(task.title);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = title.hashCode();
        if (isRepeated()) {
            result = prime * result + start.hashCode();
            result = prime * result + end.hashCode();
            result = prime * result + interval;
            result = prime * result + (active ? 1 : 0);
        } else {
            result = prime * result + time.hashCode();
            result = prime * result + (active ? 1 : 0);
        }
        return result;
    }

    @Override
    public String toString() {
        SimpleDateFormat sf = new SimpleDateFormat("[yyyy-MM-dd hh:mm:ss.SSS]");
        String activity = "";
        if (!active) {
            activity = " inactive ";
        }
        if (!isRepeated()) {
            return "\"" + title + "\" at " + sf.format(time) + activity;
        } else {
            return "\"" + title + "\" from " + sf.format(start) + " to "
              + sf.format(end) + " every " + writeInterval(interval) + activity;
        }
    }

    private String writeInterval(int interval) {
        String year;
        String month;
        String day;
        String hour;
        String minute;
        String second;
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.setTimeInMillis(interval);
        if (calendar.get(Calendar.YEAR) - 1970 > 1) {
            year = calendar.get(Calendar.YEAR) - 1970 + " years, ";
        } else if (calendar.get(Calendar.YEAR) - 1970 == 1) {
            year = calendar.get(Calendar.YEAR) - 1970 + " year, ";
        } else {
            year = "";
        }
        if (calendar.get(Calendar.MONTH) > 1) {
            month = calendar.get(Calendar.MONTH) + " months, ";
        } else if (calendar.get(Calendar.MONTH) == 1) {
            month = calendar.get(Calendar.MONTH) + " month, ";
        } else {
            if (year != "") {
                year = year.substring(0, year.length() - 2);
            }
            month = "";
        }
        if (calendar.get(Calendar.DAY_OF_MONTH) - 1 > 1) {
            day = (calendar.get(Calendar.DAY_OF_MONTH) - 1) + " days, ";
        } else if (calendar.get(Calendar.DAY_OF_MONTH) - 1 == 1) {
            day = (calendar.get(Calendar.DAY_OF_MONTH) - 1) + " day, ";
        } else {
            if (month != "") {
                month = month.substring(0, month.length() - 2);
            }
            day = "";
        }
        if ((calendar.get(Calendar.HOUR_OF_DAY) - 2) > 1) {
            hour = (calendar.get(Calendar.HOUR_OF_DAY) - 2) + " hours, ";
        } else if ((calendar.get(Calendar.HOUR_OF_DAY) - 2) == 1) {
            hour = (calendar.get(Calendar.HOUR_OF_DAY) - 2) + " hour, ";
        } else {
            if (day != "") {
                day = day.substring(0, day.length() - 2);
            }
            hour = "";
        }
        if (calendar.get(Calendar.MINUTE) > 1) {
            minute = calendar.get(Calendar.MINUTE) + " minutes, ";
        } else if (calendar.get(Calendar.MINUTE) == 1) {
            minute = calendar.get(Calendar.MINUTE) + " minute, ";
        } else {
            if (hour != "") {
                hour = hour.substring(0, hour.length() - 2);
            }
            minute = "";
        }
        if (calendar.get(Calendar.SECOND) > 1) {
            second = calendar.get(Calendar.SECOND) + " seconds";
        } else if (calendar.get(Calendar.SECOND) == 1) {
            second = calendar.get(Calendar.SECOND) + " second";
        } else {
            if (minute != "") {
                minute = minute.substring(0, minute.length() - 2);
            }
            second = "";
        }
        return year + month + day + hour + minute + second;
    }

    @Override
    public Task clone() throws CloneNotSupportedException {
        return (Task) super.clone();
    }
}