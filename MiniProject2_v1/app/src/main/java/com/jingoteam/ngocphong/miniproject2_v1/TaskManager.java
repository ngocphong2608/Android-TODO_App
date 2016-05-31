package com.jingoteam.ngocphong.miniproject2_v1;

import android.content.Context;

import org.joda.time.DateTime;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
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

    public static void addTask(Context context, Task task){
        taskList.add(task);
    }

    public static void loadAllTask(Context context){
        File internal = context.getFilesDir();

        String filePath =internal.getAbsolutePath() + "/" + ConfigManager.saveTaskFileName;
        File taskFile = new File(filePath);

        if (taskFile.exists()){
            ObjectInputStream inputStream = null;
            try {
                inputStream = new ObjectInputStream(new FileInputStream(filePath));
                taskList = (List<Task>)inputStream.readObject();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
    }

    public static void saveAllTask(Context context){
        File internal = context.getFilesDir();

        String filePath =internal.getAbsolutePath() + "/" + ConfigManager.saveTaskFileName;
        File taskFile = new File(filePath);

        if (!taskFile.exists()) {
            try {
                taskFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {

            ObjectOutputStream outputStream;
            try {
                outputStream = new ObjectOutputStream(new FileOutputStream(filePath));
                outputStream.writeObject(taskList);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static Task getTask(int position) {
        return taskList.get(position);
    }

    public static void removeTask(Context context, Task task) {
        taskList.remove(task);
        saveAllTask(context);
    }

    public static List<Task> getTaskList(DateTime date) {
        List<Task> subList = new ArrayList<>();



        for(Task task : taskList) {

            if (compareDate(date, task.getCreatedDate()))
                subList.add(task);
        }

        return subList;
    }

    public static List<Task> getTaskList(boolean isFinished) {
        List<Task> res = new ArrayList<>();

        for (Task task : taskList){
            if (task.isFinished() == isFinished)
                res.add(task);
        }

        return res;
    }

    private static boolean compareDate(DateTime date1, DateTime date2) {
        return date1.getDayOfMonth() == date2.getDayOfMonth() &&
                date1.getMonthOfYear() == date2.getMonthOfYear() &&
                date1.getYear() == date2.getYear();
    }
}
