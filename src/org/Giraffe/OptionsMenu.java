package org.Giraffe;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class OptionsMenu extends PreferenceActivity implements OnClickListener
{
	private static final String OPT_MUSIC = "music";
	private static final boolean OPT_MUSIC_DEF = true;
	private static final String OPT_SOUNDS = "sounds";
	private static final boolean OPT_SOUNDS_DEF = true;
	private static final String LVL_SLCT = "levelselect";
	private static final String LVL ="1";
	private static final String TOTAL_PROGRESS = "totalprogress";
	private static final String TP ="1";
	
	private static final String HSCORE="highscore";
	static final String SCORE="20";
		
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.optionsmenu);
	}
	public static String getTotalProgress(Context context)
	{
		return PreferenceManager.getDefaultSharedPreferences(context)
			.getString(TOTAL_PROGRESS, TP);
	}
	/** Get the current value of the music option */
	public static boolean getMusic(Context context)
	{
		return PreferenceManager.getDefaultSharedPreferences(context)
			.getBoolean(OPT_MUSIC, OPT_MUSIC_DEF);
	}
	/** Get the current value of the music option */
	public static boolean getSounds(Context context)
	{
		return PreferenceManager.getDefaultSharedPreferences(context)
			.getBoolean(OPT_SOUNDS, OPT_SOUNDS_DEF);
	}
	public static String getLevel(Context context)
	{
		return PreferenceManager.getDefaultSharedPreferences(context)
			.getString(LVL_SLCT, LVL);
	}
	public static String getScore(Context context)
	{
		return PreferenceManager.getDefaultSharedPreferences(context)
			.getString(HSCORE, SCORE);
	}
	public void setScore(Context context,String score)
	{
		//getting the editor ot change the shared preference and then committing that change 
		//returns an editor.....
		SharedPreferences.Editor editor = 
		PreferenceManager.getDefaultSharedPreferences(context)
			.edit();
			editor.putString(HSCORE, score);
			editor.commit();
	}
	
	//public static String getScore(Context context)
	
	
	public static void setLevel(Context context,String number)
	{
		//getting the editor ot change the shared preference and then committing that change 
		//returns an editor.....
		SharedPreferences.Editor editor = 
		PreferenceManager.getDefaultSharedPreferences(context)
			.edit();
			editor.putString(LVL_SLCT, number);
			editor.commit();
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	
	

}
