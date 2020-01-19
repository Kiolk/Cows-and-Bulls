package com.github.kiolk.cowsandbulls.utils;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class StringUtils {

    public static final String TO_HH_MM_SS = "HH:mm:ss";
    public static final String TO_MM_SS = "mm:ss";
    public static final String TO_YYYY_MM_DD_T_HH_mm_ss_XXX = "yyyy-MM-dd'T'HH:mm:ssZZZZZ";

    public static String getTime(long time){
        if(time > 3600){
            return new SimpleDateFormat(TO_HH_MM_SS, Locale.getDefault()).format(time * 1000);
        }
        return new SimpleDateFormat(TO_MM_SS, Locale.getDefault()).format(time * 1000);
    }
}
