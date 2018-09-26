package apextechies.starbasket.common;


import android.content.Context;
import android.content.SharedPreferences;


public class ClsGeneral {
    public static void setPreferences(Context context , String key, String value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                "WED_APP", Context.MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static void setBoolean(Context context , String key, boolean value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                "WED_APP", Context.MODE_PRIVATE).edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static void setInt(Context context ,String key, int value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                "WED_APP", Context.MODE_PRIVATE).edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static boolean getBoolPreferences(Context context ,String key) {
        SharedPreferences prefs = context.getSharedPreferences("WED_APP",
                Context.MODE_PRIVATE);
        return prefs.getBoolean(key, false);
    }

    public static String getStrPreferences(Context context ,String key) {
        SharedPreferences prefs = context.getSharedPreferences("WED_APP",
                Context.MODE_PRIVATE);
        return prefs.getString(key, "");
    }

    public static int getIntPreferences(Context context ,String key) {
        SharedPreferences prefs = context.getSharedPreferences("WED_APP",
                Context.MODE_PRIVATE);
        return prefs.getInt(key, 0);
    }


}
