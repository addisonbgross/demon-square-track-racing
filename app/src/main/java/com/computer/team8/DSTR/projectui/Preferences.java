package com.computer.team8.DSTR.projectui;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

//Wrapper class for accessing shared preferences.
//Referenced: http://stackoverflow.com/questions/23351904/getting-cannot-resolve-method-error-when-trying-to-implement-getsharedpreferen

public class Preferences {
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String name = "nameKey";
    public static final String color = "colorKey";
    public static String charColor = "";
    public static ArrayList< ArrayList<Float> > googleTracks;
    private SharedPreferences sharedpreferences;

    public Preferences() {}

    private static SharedPreferences getPreferences(Context context)
    {
        return context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
    }

    public static void addGoogleTrack(ArrayList<Float> track)
    {
        if (googleTracks == null) {
            googleTracks = new ArrayList<>();
        }

        googleTracks.add(track);
    }

    public static ArrayList<Float> getGoogleTrack(int i)
    {
        if (googleTracks != null) {
            return googleTracks.get(i);
        } else {
            return null;
        }
    }

    public static Iterator<ArrayList<Float>> getGoogleIterator()
    {
        if (googleTracks != null) {
            return googleTracks.iterator();
        } else {
            return null;
        }
    }

    public static String getName(Context context)
    {
        return getPreferences(context).getString(name, "Anonymous");
    }

    public static void setName(Context context, String characterName)
    {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putString(name, characterName);
        editor.commit();
    }

    //Also get and set colors here.
    public static void setColor(Context context, String characterColor){

        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putString(color, characterColor);
        charColor = characterColor;
        editor.commit();
    }

    public static String getColor(Context context){

        Log.i("CHARCOLOR:", charColor);
        if(charColor == "white"){
            return getPreferences(context).getString(color, "white");
        }
        else if(charColor == "pure red"){
            return getPreferences(context).getString(color, "pure red");
        }
        else if(charColor == "green"){
            return getPreferences(context).getString(color, "green");
        }
        else if(charColor == "yellow"){
            return getPreferences(context).getString(color, "yellow");
        }
        else if(charColor == "aqua"){
            return getPreferences(context).getString(color, "aqua");
        }
        else if(charColor == "blue"){
            return getPreferences(context).getString(color, "blue");
        }
        else {
            return getPreferences(context).getString(color, "pure red");
        }
    }
}
