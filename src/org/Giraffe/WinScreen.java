

	package org.Giraffe;

	import org.Giraffe.GameCall;

	import android.app.Activity;
	import android.media.AudioManager;
import android.net.Uri;
	import android.os.Bundle;
import android.content.Context;
	import android.content.Intent;
	import android.util.Log;
	import android.view.View;
	import android.view.WindowManager;
	import android.view.View.OnClickListener;

	import android.app.Activity;
import android.view.View.OnClickListener;

	public class WinScreen extends Activity implements OnClickListener {	
		 /** Called when the activity is first created. */
		Context context;
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        //GameCall.mPaused=false;
	        context=this;
	        
			Music.stop(this);
			//if you beat level 1 set it to the win layout.
			if(Integer.parseInt(OptionsMenu.getLevel(context))==1)
	        {
				   setContentView(R.layout.win);
	        }
	        if(Integer.parseInt(OptionsMenu.getLevel(context))==2)
	        {
	        	setContentView(R.layout.endscreen);
	        }
	     
	        //if you beat level 2 set it to the you beat the game layout.
	        
	        //View settingsButton = findViewById(R.id.settings_button);
	        //settingsButton.setOnClickListener(this);
	        View mContinueButton = findViewById(R.id.continueButton);
	        mContinueButton.setOnClickListener(this);
	        View mQuitButton = findViewById(R.id.quitButton);
	        mQuitButton.setOnClickListener(this);  
	        
	        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
	    }
	    public void onClick(View v)
		{
			switch (v.getId())
			{
			//Continues from the last end point
				case R.id.continueButton:
					GameState.nextLevel(v.getContext());
					this.finish();
			    	Music.stop(this);
					Intent x=new Intent(this, CutSceneActivity.class);
					startActivity(x);

					break;
			//Quits the game		
				case R.id.quitButton:
					//finish();
				onBackPressed();
			        break;
			}
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
	    }
	    @Override
	    protected void onDestroy()
	    {
	    	super.onDestroy();
	    	//Music.stop(this);
	    }	
	    @Override
		public void onBackPressed()
	    {
	    	super.onBackPressed();
	    	Intent mainmenu = new Intent(this, MainMenu.class);
			startActivity(mainmenu);
	    	return;
	    }
	
}
