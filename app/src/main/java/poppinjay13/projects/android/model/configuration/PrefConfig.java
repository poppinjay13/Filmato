package poppinjay13.projects.android.model.configuration;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import poppinjay13.projects.android.R;

public class PrefConfig {
    private SharedPreferences sharedPreferences;
    private Context context;

    public void prefConfig(Context context){
        sharedPreferences = context.getSharedPreferences(context.getString(R.string.pref_file),Context.MODE_PRIVATE);
    }

    public void writeLoginStatus(boolean status){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(context.getString(R.string.pref_status),status);
        editor.apply();
    }

    public boolean readLoginStatus(){
        return sharedPreferences.getBoolean(context.getString(R.string.pref_status),false);
    }

    public void writeName(String name){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.pref_username),name);
        editor.apply();
    }

    public String readName(){
        return sharedPreferences.getString(context.getString(R.string.pref_username),"User");
    }

    public void displayToast(String message){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }
}
