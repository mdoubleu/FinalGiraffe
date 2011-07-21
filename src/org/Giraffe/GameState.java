package org.Giraffe;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/*
 * In this class you have the progress of the game, and the next level method
 */
public class GameState 
{
	private static int progress=1;
	//SHOULDNT GO THE FUCK DOWN EVER!!!!!!!
	public static int totalProgress=1;
	
	//returns the level of the state to the gamemodel to load rawr
	public static int getLevel(Context context)
	{
		

		return Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(context)
				.getString("levelselect", progress+""));
	}
	//goes to the next level
	//total progress needs to be saved in the preferences 
	public static void nextLevel(Context context)
	{
		if(progress>=2)
		{
			progress=1; 
			SharedPreferences.Editor editor = 
				PreferenceManager.getDefaultSharedPreferences(context)
					.edit();
					editor.putString("levelselect", progress+"");		
					editor.commit();
		}
		else
		{
			progress+=1;
			totalProgress+=1;
			SharedPreferences.Editor editor = 
				PreferenceManager.getDefaultSharedPreferences(context)
					.edit();
					editor.putString("levelselect", progress+"");
					editor.putString("totalprogress",totalProgress+"");
					
					editor.commit();
		}
	}
}
