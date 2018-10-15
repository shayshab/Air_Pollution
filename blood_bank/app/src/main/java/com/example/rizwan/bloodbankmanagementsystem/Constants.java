package com.example.rizwan.bloodbankmanagementsystem;

import java.util.ArrayList;

/**
 * Created by rizwan on 31/8/17.
 */

public class Constants {
     private static final String ROOT_URL = "http://bbms-android-project.000webhostapp.com/webservices/";
     //private static final String ROOT_URL = "http://31.170.165.32/bbms/webservices/";
     //private static final String ROOT_URL = "http://192.168.42.132/bbms/webservices/";
     public static final String REGISTER_URL = ROOT_URL+"register.php";
     public static final String LOGIN_URL = ROOT_URL+"login.php";
     public static final String DONAR_REGISTER_URL = ROOT_URL+"donar_register.php";
     public static final String DONAR_LIST_URL = ROOT_URL+"donar_list.php";
     public static final String MY_DONARS = ROOT_URL+"my_donar_list.php";

     public static ArrayList<String> did = new ArrayList<>();
     public static ArrayList<String> dname = new ArrayList<>();
     public static ArrayList<String> dstate = new ArrayList<>();
     public static ArrayList<String> dcity = new ArrayList<>();
     public static ArrayList<String> dmobile = new ArrayList<>();
     public static ArrayList<String> dblood = new ArrayList<>();

     public static String temp_number;


}
