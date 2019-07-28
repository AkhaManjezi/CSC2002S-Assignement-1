package com.example.assignment1;

import android.content.Context;
import android.content.SharedPreferences;

public class DiaryEntryPrefs {
    /** This application's preferences label */
    private static final String PREFS_NAME = "com.our.package.DiaryEntryPrefs";

    /** This application's preferences */
    private static SharedPreferences settings;

    /** This application's settings editor*/
    private static SharedPreferences.Editor editor;

    /** Constructor takes an android.content.Context argument*/
    public DiaryEntryPrefs(Context ctx){
        if(settings == null){
            settings = ctx.getSharedPreferences(PREFS_NAME,
                    Context.MODE_PRIVATE );
        }
        /*
         * Get a SharedPreferences editor instance.
         * SharedPreferences ensures that updates are atomic
         * and non-concurrent
         */
        editor = settings.edit();
    }

}
