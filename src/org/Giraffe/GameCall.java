package org.Giraffe;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;


public class GameCall extends Activity{

	GameView view;
	SurfaceView surface;
	SurfaceHolder holder;
	GameController controller;
	GameModel model;
	Context context;
	static boolean mPaused=false;
	static final int DIALOG_PAUSED_ID = 0;
	static final int DIALOG_GAMEOVER_ID = 1;

	//Used for the pause timers
	public static long pressedBack;
	public static long pressedResume;
	public static long timePaused;
	
	//Used for pausing the jumps
	public static long pressedJump;
	public static long releasedJump;
	public static long jumptimer;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        GameCall.jumptimer = 0;
		GameCall.pressedJump = 0;
		GameCall.releasedJump = 0;
        
        getWindow().setFormat(PixelFormat.RGBA_8888);
        setContentView(R.layout.game);

        surface=(SurfaceView)findViewById(R.id.gSurface);
        context=surface.getContext();
        model=new GameModel(context);
        
        holder=surface.getHolder();
        
        view=new GameView(controller, holder, model, context);
        surface.getHolder().addCallback(view);
        
        controller= new GameController(model);
        surface.setOnTouchListener(controller);
        
        
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
       
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE); 
       
    }
    //overrides the back button
    @Override
    public void onBackPressed() {
    	
    	//if(Music.)
    	Music.pause(context);
    	
    	if(mPaused==true)
    	{

    	}
    	else
    	{
	    	//Music.stop(context);
	        //GameCall.this.finish();
	    	// do something on back.
	    	//setContentView(R.layout.pausescreen);
	    	
	    	//gameThread.setRunning(false);
	    	//gameThread.run();
	        mPaused=true;
	        pressedBack = System.currentTimeMillis();
	        pressedJump = System.currentTimeMillis();
    	
    	}
    	if(GameModel.levelOver == false)
    	{
    		showDialog(0);
    	}
	    	//gameThread.suspend();
    
    }
    private void showDialogBox(int i) {
		// TODO Auto-generated method stub
		
	}
	@Override
    protected void onPause() {
        super.onPause();
        
        //Smelly coding results in issues with losing/winning game
       // onBackPressed();
        //mPaused=true;
        //pressedBack = System.currentTimeMillis();
        
        onBackPressed();
	}
    @Override
    protected void onResume() {
        super.onResume();
        
        GameModel.levelOver = false;
        GameModel.levelLose = false;
       
        
    }
    
    @Override
	public void onDestroy() {
    	super.onDestroy();
    	mPaused = false;
    	//sMusic.start(context);
    	pressedBack=0;
    	pressedResume=0;
    	timePaused=0;
     
    }
    
    @Override
	public void finish() {
    	super.finish();
    	//mPaused = false;
    	//pressedBack=0;
    	//pressedResume=0;
    	//timePaused=0;
    	//sMusic.start(context);
     
    }
    
    
    
    
    
    @Override
	protected Dialog onCreateDialog(int id) {
    	Dialog dialog;
    	
		if (id == DIALOG_PAUSED_ID) {
			dialog = new AlertDialog.Builder(this)
            .setMessage("Would you like to quit?")
            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                	mPaused=false;
                	//Music.stop(context);
                    GameCall.this.finish();
               }
           }).setNegativeButton("No", new DialogInterface.OnClickListener() {
               public void onClick(DialogInterface dialog, int id) {
            	   
            	   mPaused=false;
            	  // Music.create(context, R.raw.newcentralpark);
            	   Music.start(context);
            	   pressedResume = System.currentTimeMillis();
            	   timePaused += pressedResume - pressedBack;
            	   
            	   releasedJump = System.currentTimeMillis();
            	   jumptimer = releasedJump - pressedJump;
            	   
            	   dialog.dismiss();
            	  
              }
          })
            .create();
			dialog.setCancelable(false);
		} 
		
		else 
		{
			dialog = super.onCreateDialog(id);
			dialog.setCancelable(false);
		}
		return dialog;
    }
    
    public static boolean getmPaused()
    {
    	return mPaused;
    }
    
}