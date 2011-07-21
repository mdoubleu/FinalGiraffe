package org.Giraffe;






import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;
import android.widget.AdapterView.OnItemClickListener;

public class CutSceenActivity extends Activity implements OnClickListener
{
	private View replay;
	private View skip;
	long cTime;
	long startTime;
	Context context;
	Intent o;
	
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        
	        
	        setContentView(R.layout.cutsceen1);
	        context=this;
	        replay= findViewById(R.id.replaybutton);
	        replay.setOnClickListener(this);
	        skip= findViewById(R.id.skipbutton);
	        skip.setOnClickListener(this);
	        //Fill view from resource
			o=new Intent(this, GameCall.class);
			Uri videoPath=null;
	        VideoView video = (VideoView) findViewById(R.id.videoView1);
	        //video.setVideoPath("/raw/cutscene1.mp4");
	        if(Integer.parseInt(OptionsMenu.getLevel(context))==1)
	        {
	        	videoPath = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.cutscene1);
	        }
	        if(Integer.parseInt(OptionsMenu.getLevel(context))==2)
	        {
	        	videoPath = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.cutscene2);
	        }
	        video.setVideoURI(videoPath);
	       video.requestFocus();
	       //video.setOnCompletionListener(null); 
	       video.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){
		@Override
		public void onCompletion(MediaPlayer mp) {
			// TODO Auto-generated method stub
			finish();
			startActivity(o);
			
			
		} });
	       video.start();
	      
	        //Music.create(this,R.raw.cutscene1);
	        //mediaPlayer.start(); // no need to call prepare(); create() does that for you
	       
	       getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

	        setVolumeControlStream(AudioManager.STREAM_MUSIC);
	        //Music.create(this, R.raw.jeremythegiraffetheme);
	       // Music.setLooping(this, R.raw.jeremythegiraffetheme);
	        
	    }
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch (v.getId())
		{
		case R.id.replaybutton:
			finish();
			Intent x=new Intent(this, CutSceenActivity.class);
			startActivity(x);
			break;
		case R.id.skipbutton:
			finish();
			Intent z=new Intent(this, GameCall.class);
			startActivity(z);
			break;
		}
		

			 
		
			
	}

	
	
}
