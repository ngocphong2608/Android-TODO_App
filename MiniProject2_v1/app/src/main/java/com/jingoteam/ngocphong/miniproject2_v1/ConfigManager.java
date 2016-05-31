package com.jingoteam.ngocphong.miniproject2_v1;

/**
 * Created by NgocPhong on 29/05/2016.
 */
public class ConfigManager {
    enum MENU_ITEM{YESTERDAY, TODAY, TOMORROW}
    public static MENU_ITEM selectedItem = MENU_ITEM.TODAY;
    public static String saveTaskFileName = "AllTask.dat";
}
