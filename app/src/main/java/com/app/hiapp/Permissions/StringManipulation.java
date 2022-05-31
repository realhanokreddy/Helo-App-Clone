package com.app.hiapp.Permissions;

public class StringManipulation {
    public static String expandUsername(String username){
        return username.replace("_", " ");
    }
    public static String condenseUsername(String username){
        return username.replace(" ", "_");
    }
}
