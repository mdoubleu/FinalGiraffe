package org.Giraffe;
import android.app.Activity;
import android.media.AudioManager;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;

public class LevelSelect extends Activity implements OnClickListener
{
	
	//since main menu never dies I should keep the game state, which stores the progress of the game here....I think
    
	/** Called when the activity is first created. */
	//View level2button= findViewById(R.id.lvl2button);
	//View level2buttonbanned=findViewById(R.id.lvl2buttonbanned);
	Context context;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.levelselect);
        //game state should never die essentially
        context=this;
        View level1button = findViewById(R.id.lvl1button);
        level1button.setOnClickListener(this);
        View level2button=findViewById(R.id.lvl2button);
        	//Integer.parseInt(OptionsMenu.getTotalProgress(context)
        if(Integer.parseInt(OptionsMenu.getTotalProgress(context))>=2)
        {
        //View level2button = findViewById(R.id.lvl2button);
        level2button.setBackgroundResource(R.drawable.lvl2button);	
        level2button.setOnClickListener(this);
        
        //View level2buttonbanned=findViewById(R.id.lvl2buttonbanned);
       // level2buttonbanned.setVisibility(View.GONE);
        }
        else
        {
        	  level2button.setBackgroundResource(R.drawable.lvl2buttonbanned);	
        	//View level2button=findViewById(R.id.lvl2buttonbanned);
        	//level2button.setVisibility(View.GONE);
       	
        
        }
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
			case R.id.lvl1button:
				
				Music.stop(this);
				//Intent x=new Intent(this, Start.class);
				//startActivity(x);
				Log.d("Far", "Do i get this far?");
				OptionsMenu.setLevel(v.getContext(),1+"");
				Intent x=new Intent(this, CutSceenActivity.class);
				startActivity(x);

				break;
				
			//we set the fucking level!!	
			case R.id.lvl2button:
				//Intent f=new Intent(this, Extras.class);
		        //startActivity(f);
				Music.stop(this);
				OptionsMenu.setLevel(v.getContext(),2+"");
				Intent f=new Intent(this, CutSceenActivity.class);
				startActivity(f);
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