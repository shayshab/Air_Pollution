package com.example.rizwan.bloodbankmanagementsystem;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by rizwan on 31/8/17.
 */

public class SharedPref {
    private static SharedPref mInstance;
    private static Context mCtx;

    private static String BBMS;//BBMS=Blood Bank Management System

    private SharedPref(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPref getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPref(context);
        }
        return mInstance;
    }

    public boolean userLogin(int id,String username,String email){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(BBMS,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("id",id);
        editor.putString("username",username);
        editor.putString("email",email);

        editor.apply();

        return true;
    }

    public boolean isLogged(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(BBMS,Context.MODE_PRIVATE);

        if (sharedPreferences.getString("username",null) != null){
            return true;
        }

        return false;
    }

    public int getUserId(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(BBMS,Context.MODE_PRIVATE);

        return sharedPreferences.getInt("id",0);

    }
    public String getUsername(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(BBMS,Context.MODE_PRIVATE);

        return sharedPreferences.getString("username",null);

    }

    public boolean logout(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(BBMS,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.clear();

        editor.apply();

        editor.commit();

        return true;
    }

}
