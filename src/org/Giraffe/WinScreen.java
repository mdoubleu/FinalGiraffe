

	package org.Giraffe;

	import org.Giraffe.GameCall;

	import android.app.Activity;
	import android.media.AudioManager;
	import android.os.Bundle;
	import android.content.Intent;
	import android.util.Log;
	import android.view.View;
	import android.view.WindowManager;
	import android.view.View.OnClickListener;

	import android.app.Activity;
import android.view.View.OnClickListener;

	public class WinScreen extends Activity implements OnClickListener {	
		 /** Called when the activity is first created. */
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
			Music.stop(this);

	        setContentView(R.layout.win);
	  
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
					Intent x=new Intent(this, CutSceenActivity.class);
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
