package app.com.example.victoriajuan.jerdapp;

/**
 * Created by victoriajuan on 10/25/16.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SaveSharedPreference {
    static final String INCOGNITO_MODE = "";
    static final String ACC_EMAIL = "";
    static final String FILE_DIR = "";

    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setIncognitoMode(Context ctx, String val) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putBoolean(INCOGNITO_MODE, Boolean.parseBoolean(val));
        editor.apply();
    }

    public static void setDefaultAcc(Context ctx, String val1) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(ACC_EMAIL, val1);
        editor.apply();
    }

    public static void setFileDir(Context ctx, String val) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(FILE_DIR, val);
        editor.apply();
    }

    public static boolean getMode(Context ctx) { return getSharedPreferences(ctx).getBoolean(INCOGNITO_MODE, true); }
    public static String getFileDir(Context ctx) { return getSharedPreferences(ctx).getString(FILE_DIR, ""); }

}