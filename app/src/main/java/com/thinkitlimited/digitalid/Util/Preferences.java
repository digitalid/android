package com.thinkitlimited.digitalid.Util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceManager;

/**
 * Created by JOSHUA BUJO on 3/16/2017.
 */

public class Preferences
{
    private SharedPreferences sharedPreferences;

    public Preferences(Context paramContext)
    {
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(paramContext);
    }

    public SharedPreferences getSharedPreferences(){
        return sharedPreferences;
    }


    public void setUserEmail(String paramString)
    {
        SharedPreferences.Editor localEditor = this.sharedPreferences.edit();
        localEditor.putString("email", paramString);
        localEditor.commit();
    }

    public void setUserId(String paramString)
    {
        SharedPreferences.Editor localEditor = this.sharedPreferences.edit();
        localEditor.putString("id", paramString);
        localEditor.commit();
    }

    public void setUserName(String paramString)
    {
        SharedPreferences.Editor localEditor = this.sharedPreferences.edit();
        localEditor.putString("user_name", paramString);
        localEditor.commit();
    }
    public void setUserPhone(String paramString)
    {
        SharedPreferences.Editor localEditor = this.sharedPreferences.edit();
        localEditor.putString("user_phone", paramString);
        localEditor.commit();
    }

    public String getPhone()
    {
        return this.sharedPreferences.getString("user_phone", "unknown");
    }


    public String getUserName()
    {
        return this.sharedPreferences.getString("user_name", "");
    }

    public String getUserId()
    {
        return this.sharedPreferences.getString("id", "");
    }

    public String getUserEmail()
    {
        return this.sharedPreferences.getString("email", "");
    }

}
