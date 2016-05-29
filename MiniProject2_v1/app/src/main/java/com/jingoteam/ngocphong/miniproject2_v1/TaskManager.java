package com.jingoteam.ngocphong.miniproject2_v1;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NgocPhong on 29/05/2016.
 */
public class TaskManager {
    private static List<Task> taskList = new ArrayList<>();

    public static List<Task> getTaskList() {
        return taskList;
    }

    public static void setTaskList(List<Task> taskList) {
        TaskManager.taskList = taskList;
    }

    public static void addTask(Task task){
        taskList.add(task);
    }
}
