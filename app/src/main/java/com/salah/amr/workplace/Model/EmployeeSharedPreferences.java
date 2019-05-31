package com.salah.amr.workplace.Model;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;


/**
 * Created by user on 11/28/2017.
 */

public class EmployeeSharedPreferences {

    private static final String TAG = "EmployeeSharedPreferen";
    private static final String PREF_MANAGER_OR_EMPLOYEE = "pref_manager_or_employee";
    private static final String PREF_CODE_ONCE = "pref_code_once";
    private static final String PREF_SETUP_CHAT_ROOM = "pref_setup_chat_room";
    private static final String PREF_EMPLOYEE_ID = "pref_employee_id";
    private static final String PREF_CHAT_ROOM_NAME = "pref_chat_room_name";
    private static final String PREF_SALARY_TYPE_ALARM = "pref_salary_type_alarm";
    private static final String PREF_EMPLOYEE_CHAT_ROOM_NAME = "pref_employee_chat_room_name";
    private static final String PREF_MANAGER_CHAT_ROOM_NAME = "pref_manager_chat_room_name";
    private static final String PREF_TEMP_EMPLOYEE_ID = "pref_temp_employee_id";
    private static final String PREF_LOGIN_CHAT_ROOM_DIALOG = "pref_login_chat_room_dialog";
    private static final String PREF_SIGNED_OUT = "pref_signed_out";

    private SharedPreferences prefs;

    public EmployeeSharedPreferences(Context context) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public boolean isManager() {
       int i =  prefs.getInt(PREF_MANAGER_OR_EMPLOYEE , 0);
       Log.d(TAG, "isManager: Manger or employee  "+i);
        if(i==0){
            return true;
        }
        else return false;
    }

    public void setManagerOrEmployee(int id) {
        Log.d(TAG, "setManagerOrEmployee: ");
        prefs.edit().putInt(PREF_MANAGER_OR_EMPLOYEE , id).apply();
    }


    public void setCodeOnce(boolean flag) {
        prefs.edit().putBoolean(PREF_CODE_ONCE , flag).apply();
    }


    public boolean getCodeOnce() {
        return prefs.getBoolean(PREF_CODE_ONCE , false);
    }

    public String getLatePenalty(){
        return prefs.getString("late" , "10");
    }

    public String getAbsentPenalty(){
        return prefs.getString("absent" , "20");
    }

    public String getSalaryType(){
        return  prefs.getString("salaryType" , "0");
    }

    public void setSalaryAlarm(boolean flag){
        prefs.edit().putBoolean(PREF_SALARY_TYPE_ALARM , flag).apply();
    }

    public boolean getSalaryAlarm(){
        return  prefs.getBoolean(PREF_SALARY_TYPE_ALARM , false);
    }

    public boolean isSetupChatRoomDialog(){
        return prefs.getBoolean(PREF_SETUP_CHAT_ROOM , false);
    }

    public void setupChatRoomDialog(boolean flag){
        prefs.edit().putBoolean(PREF_SETUP_CHAT_ROOM , flag).apply();
    }

    public void loginChatRoomDialog(boolean flag){
        prefs.edit().putBoolean(PREF_LOGIN_CHAT_ROOM_DIALOG , flag).apply();
    }

    public boolean isLoginChatRoomDialog(){
        return prefs.getBoolean(PREF_LOGIN_CHAT_ROOM_DIALOG , false);
    }



    public void setEmployeeId(int id){
        prefs.edit().putInt(PREF_EMPLOYEE_ID ,id).apply();
    }

    public int getEmployeeId(){
        return prefs.getInt(PREF_EMPLOYEE_ID , -2);
    }

    public void setChatRoomName(String name){
        prefs.edit().putString(PREF_CHAT_ROOM_NAME , name).apply();
    }

    public String getChatRoomName(){
        return prefs.getString(PREF_CHAT_ROOM_NAME , "");
    }

    public void setEmployeeChatRoomName(String name){
        prefs.edit().putString(PREF_EMPLOYEE_CHAT_ROOM_NAME , name).apply();
    }

    public String getEmployeeChatRoomName(){
        return prefs.getString(PREF_EMPLOYEE_CHAT_ROOM_NAME , "");
    }

    public void setManagerChatRoomName(String name){
        prefs.edit().putString(PREF_MANAGER_CHAT_ROOM_NAME , name).apply();
    }

    public String getManagerChatRoomName(){
        return prefs.getString(PREF_MANAGER_CHAT_ROOM_NAME , "");
    }

    public void setTempEmployeeId(int id){
        prefs.edit().putInt(PREF_TEMP_EMPLOYEE_ID , id).apply();
    }

    public int getTempEmployeeId(){
        return prefs.getInt(PREF_TEMP_EMPLOYEE_ID , -2);
    }


    public void setSignedOut(boolean flag){
        prefs.edit().putBoolean(PREF_SIGNED_OUT , flag).apply();
    }

    public boolean getSignedOut(){
        return prefs.getBoolean(PREF_SIGNED_OUT , false);
    }

}
