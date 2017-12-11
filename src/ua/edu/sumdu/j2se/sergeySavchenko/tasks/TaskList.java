package ua.edu.sumdu.j2se.sergeySavchenko.tasks;

import java.io.Serializable;

/**
 * abstract class "TaskList", sets methods for implementation
 *
 * @author Sergey Savchenko
 */
public abstract class TaskList implements Iterable<Task>, Serializable {

    /**
     * method for adding task to task array(list)
     *
     * @param task task adding to array(list)
     */
    public abstract void add(Task task);

    /**
     * method for deleting task from task array(list)
     *
     * @param task task deleting from array(list)
     * @return result successful(true) or not(false)
     */
    public abstract boolean remove(Task task);

    /**
     * method for defining size of task array(list)
     *
     * @return size of task array(list)
     */
    public abstract int size();

    /**
     * method for getting task, using it index in array(list)
     *
     * @param index index of task in array(list)
     * @return task using it index
     */
    public abstract Task getTask(int index);
}
