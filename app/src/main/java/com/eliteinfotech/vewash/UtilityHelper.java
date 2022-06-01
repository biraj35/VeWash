package com.eliteinfotech.vewash;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.View;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UtilityHelper {

    private static final String PASSWORD_PATTERN =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";

    public static boolean checkLogin(Context context)
    {
        SharedPrefHelper loginPref=new SharedPrefHelper("login",context);
        return loginPref.getBool("session");
    }

    public static String checkUserType(Context context)
    {
        SharedPrefHelper loginPref=new SharedPrefHelper("login",context);
        return loginPref.getString("usertype");
    }
    public static ProgressDialog loadingHelper(Context context,String msg){
        ProgressDialog progressDialog=new ProgressDialog(context);
        progressDialog.setMessage(msg);
        return progressDialog;
    }
    public static ProgressDialog loadingHelper(Context context){
        ProgressDialog progressDialog=new ProgressDialog(context);
        progressDialog.setMessage("On Process....Please wait");
        return progressDialog;
    }
    public static void showView(View view){
        view.setVisibility(View.VISIBLE);
    }
    public static void hideView(View view){
        view.setVisibility(View.GONE);
    }

    private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

    public static boolean isValid(final String password) {
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

}
