package org.Giraffe;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.util.Log;

public class Music {
	private static MediaPlayer mp= null;
	
	/** Stop old song and start playing new one */
	public static void create(Context context, int resource)
	{
		stop(context);
		//stop(context);
		if(OptionsMenu.getMusic(context))
		{
			mp = MediaPlayer.create(context, resource);
			
			
		}
		//mp.setLooping(true);
		//mp.start();
	}
	public static void setLooping(Context context, int resource)
	{
		if(OptionsMenu.getMusic(context))
		{
			mp.setLooping(true);
		}
	}
	public static void stop(Context context)
	{
		if(mp != null)
		{
			mp.stop();
			mp.release();
			mp = null;
		}
	}
	public static void pause(Context context)
	{
		mp.pause();
	}
	public static void start(Context context)
	{
		//stop(context);
		if(OptionsMenu.getMusic(context))
		{
			mp.start();	
		}
		
		
	}
	
}
