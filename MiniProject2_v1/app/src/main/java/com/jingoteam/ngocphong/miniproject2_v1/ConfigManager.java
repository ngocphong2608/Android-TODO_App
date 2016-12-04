package com.jingoteam.ngocphong.miniproject2_v1;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.Scanner;

/**
 * Created by NgocPhong on 29/05/2016.
 */
public class ConfigManager implements Serializable {
    public enum MENU_ITEM {YESTERDAY, TODAY, TOMORROW, UNFINISHED, FINISHED}

    public static MENU_ITEM selectedItem = MENU_ITEM.TODAY;
    public static String saveTaskFileName = "AllTask.dat";
    public static String saveConfigFileName = "App.config";
    public static int taskPerDay = 3;
    public static boolean isFirstUse = true;

    public static void saveAllConfig(Context context) {
        File internal = context.getFilesDir();

        String filePath = internal.getAbsolutePath() + "/" + ConfigManager.saveConfigFileName;

        try {
            JSONObject object = new JSONObject();
            object.put("taskPerDay", taskPerDay);
            object.put("isFirstUse", false);

            FileWriter file = new FileWriter(filePath);
            file.write(object.toString());
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void loadAllConfig(Context context) {
        File internal = context.getFilesDir();

        String filePath = internal.getAbsolutePath() + "/" + ConfigManager.saveConfigFileName;

        File file = new File(filePath);

        if (file.exists()) {
            try {
                JSONObject obj = new JSONObject(loadStringFromFile(file));
                taskPerDay = Integer.parseInt(obj.optString("taskPerDay").toString());
                isFirstUse = false;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private static String loadStringFromFile(File file){
            try {
                String content = new Scanner(file).useDelimiter("\\Z").next();

                return content;

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        return "";
    }
}
