package ua.edu.sumdu.j2se.sergeySavchenko.tasks;

import java.util.*;

/**
 * Created by 2017 on 27.11.2017.
 */
public class Tasks {

    /**
     * method for defining tasks that will be executed during specific
     * period of time
     *
     * @param tasks list of tasks for cheking
     * @param start start time of the specific period
     * @param end   end time of the specific period
     * @return list of tasks that meet the conditions
     */
    public static Iterable<Task> 
                         incoming(Iterable<Task> tasks, Date start, Date end) {
        String message = "End time can't be less or equal start time";
        if (!end.after(start))
            throw new IllegalArgumentException(message);
        Collection<Task> colList = null;
        TaskList taskList = null;
        Class clazz = tasks.getClass();
        try {
            if (tasks instanceof Collection) {
                colList = (Collection<Task>) clazz.newInstance();
            } else if (tasks instanceof TaskList) {
                taskList = (TaskList) clazz.newInstance();
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        for (Task task : tasks) {
            if ((task.nextTimeAfter(start) != null)
                    && (!task.nextTimeAfter(start).after(end))) {
                if (colList != null) {
                    colList.add(task);
                } else if (taskList != null) {
                    taskList.add(task);
                }
            }
        }
        return ((colList != null) ? colList : taskList);
    }
    
    /**
     * method for defining tasks that will be executed in specific
     * period of time
     *
     * @param tasks list of tasks
     * @param start start time of the specific period
     * @param end   end time of the specific period
     * @return collection that meet the conditions
     */
    public static SortedMap<Date, Set<Task>> 
                         calendar(Iterable<Task> tasks, Date start, Date end)  {
        SortedMap<Date, Set<Task>> sortedMap = new TreeMap<>();
        Iterable<Task> incomingList = Tasks.incoming(tasks, start, end);
        Date tempDate = null;
        for (Task task : incomingList) {
            if (task.isRepeated()) {
                tempDate = task.nextTimeAfter(start);
                while ((tempDate != null) && !tempDate.after(end)) {
                    if (sortedMap.containsKey(tempDate)) {
                        sortedMap.get(tempDate).add(task);
                    } else {
                        Set<Task> set = new HashSet<>();
                        set.add(task);
                        sortedMap.put(tempDate, set);
                    }
                    tempDate = task.nextTimeAfter(tempDate);
                }
            } else {
                if (sortedMap.containsKey(tempDate)) {
                        sortedMap.get(tempDate).add(task);
                    } else {
                        Set<Task> set = new HashSet<>();
                        set.add(task);
                        sortedMap.put(tempDate, set);
                    }
            }
        }
        return sortedMap;
    }
}
