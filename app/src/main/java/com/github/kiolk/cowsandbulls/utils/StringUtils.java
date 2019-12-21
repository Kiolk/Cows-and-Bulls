package com.github.kiolk.cowsandbulls.utils;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class StringUtils {

    public static final String TO_HH_MM_SS = "HH:mm:ss";
    public static final String TO_MM_SS = "mm:ss";

    public static String getTime(long time){
        if(time > 3600){
            return new SimpleDateFormat(TO_HH_MM_SS, Locale.getDefault()).format(time);
        }
        return new SimpleDateFormat(TO_MM_SS, Locale.getDefault()).format(time);
    }
}
