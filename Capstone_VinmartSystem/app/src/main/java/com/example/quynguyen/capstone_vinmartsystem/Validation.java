package com.example.quynguyen.capstone_vinmartsystem;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {

    public static boolean checkEmail(String email) {
        boolean check = false;
        String emailPattern = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        Pattern regex = Pattern.compile(emailPattern);
        Matcher matcher = regex.matcher(email);
        if (matcher.find()) {
           check = true;
        } else {
           check = false;
        }
        return check;
    }

    public static boolean checkSpecialKey(String user) {
        boolean check = false;
        String emailPattern = "^[^!@#$%^&*()|_,;:'?|]{2,20}$";
        Pattern regex = Pattern.compile(emailPattern);
        Matcher matcher = regex.matcher(user);
        if (matcher.find()) {
            check = true;
        } else {
            check = false;
        }
        return check;
    }

    public static boolean checkNumberInString(String fullname) {
        boolean check = false;
        String namePattern = "^[\\D]{2,50}$";
        Pattern regex = Pattern.compile(namePattern);
        Matcher matcher = regex.matcher(fullname);
        if (matcher.find()) {
            check = true;
        } else {
            check = false;
        }
        return check;
    }


}
