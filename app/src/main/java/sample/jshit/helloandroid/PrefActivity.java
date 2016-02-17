package sample.jshit.helloandroid;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

/**
 * Created by zero7 on 2016/2/17.
 */
public class PrefActivity extends PreferenceActivity {

    private SharedPreferences sharedPreferences;
    private Preference defaultColor;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.mypreference);

        //讀取顏色設定元件
        defaultColor=(Preference)findPreference("DEFAULT_COLOR");
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
    }

    @Override
    protected void onResume(){
        super.onResume();

        int color=sharedPreferences.getInt("DEFAULT_COLOR",-1);

        if(color!=-1){
            defaultColor.setSummary(getString(R.string.default_color_summary)+
                ": "+ItemActivity.getColors(color));
        }
    }
}
