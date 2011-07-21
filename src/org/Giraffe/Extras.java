package org.Giraffe;

import android.app.Activity;
import android.media.AudioManager;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;

public class Extras extends Activity implements OnClickListener
{
	//since main menu never dies I should keep the game state, which stores the progress of the game here....I think
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.extras);
        //game state should never die essentially
        //View settingsButton = findViewById(R.id.settings_button);
        //settingsButton.setOnClickListener(this);
        View mStartButton = findViewById(R.id.levelselect);
        mStartButton.setOnClickListener(this);
        View mExtrasButton = findViewById(R.id.creditsbutton);
        mExtrasButton.setOnClickListener(this);
        View mOptionsButton = findViewById(R.id.optionButton);
        
        View mBackground = findViewById(R.id.mainMenuBackground);
        
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        Music.create(this, R.raw.jeremythegiraffetheme);
        Music.setLooping(this, R.raw.jeremythegiraffetheme);
    }
    public void onClick(View v)
	{
		switch (v.getId())
		{
			case R.id.levelselect:
				
				Music.stop(this);
				Intent x=new Intent(this, CutSceenActivity.class);
				startActivity(x);
				Log.d("Far", "Do i get this far?");

				break;
				
			case R.id.creditsbutton:
				Intent f=new Intent(this, Credits.class);
				startActivity(f);
		        break;
			case R.id.optionButton:
		        break;
			/*
			case R.id.about_button:
			Intent a = new Intent(this, About.class);

			startActivity(a);
			break;
			case R.id.how_to_play_button:
			Intent b= new Intent(this, HTP.class);
			startActivity(b);
			break;
			*/
		}
		//more buttons go here(if any)

	}
    @Override
    protected void onResume()
    {
    	super.onResume();
    	  setVolumeControlStream(AudioManager.STREAM_MUSIC);
          Music.create(this, R.raw.jeremythegiraffetheme);
          Music.setLooping(this, R.raw.jeremythegiraffetheme);
          Music.start(this);
    }
    @Override
    protected void onPause()
    {
    	super.onPause();
    	Music.stop(this);
    }
    @Override
    protected void onDestroy()
    {
    	super.onDestroy();
    	Music.stop(this);
    }
}