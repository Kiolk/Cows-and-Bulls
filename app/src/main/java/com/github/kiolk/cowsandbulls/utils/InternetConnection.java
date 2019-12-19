package com.github.kiolk.cowsandbulls.utils;

import android.content.Context;
import android.net.ConnectivityManager;

public class InternetConnection {

    public static boolean isInternetAvialable(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
        }else{
            return false;
        }
    }
}
