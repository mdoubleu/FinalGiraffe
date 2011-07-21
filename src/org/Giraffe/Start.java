package org.Giraffe;



import android.app.Activity;
import android.media.AudioManager;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;

public class Start extends Activity implements OnClickListener
{
	//since main menu never dies I should keep the game state, which stores the progress of the game here....I think
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start);
       
      
        
        
        View mContinueButton = findViewById(R.id.continuebutton);
        mContinueButton.setOnClickListener(this);
        View mLevelButton = findViewById(R.id.levelselect);
        mLevelButton.setOnClickListener(this);
        View mOptionsButton = findViewById(R.id.optionButton);
        //mOptionsButton.setOnClickListener(this);
          
         
        View mBackground = findViewById(R.id.mainMenuBackground);
        
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

      //  setVolumeControlStream(AudioManager.STREAM_MUSIC);
      //  Music.create(this, R.raw.jeremythegiraffetheme);
      //  Music.setLooping(this, R.raw.jeremythegiraffetheme);
    }
    public void onClick(View v)
	{
		switch (v.getId())
		{
			case R.id.continuebutton:
				
				Music.stop(this);
				Intent x=new Intent(this, CutSceenActivity.class);
				startActivity(x);
				

				break;
				
			case R.id.levelselect:
				Intent f=new Intent(this, LevelSelect.class);
		        startActivity(f);
		        break;
			case R.id.optionButton:
		        break;
			
		}
		//more buttons go here(if any)

	}
    @Override
    protected void onResume()
    {
    	super.onResume();
    	 
    }
    @Override
    protected void onPause()
    {
    	super.onPause();
    	//Music.stop(this);
    }
    @Override
    protected void onDestroy()
    {
    	super.onDestroy();
    	//Music.stop(this);
    }
}