package org.Giraffe;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class GameController implements OnTouchListener{
	GameModel model;
	final float delta=10;
	
	float firstX;
	float firstY;
	int state=0;
	float updateX;
	float updateY;
	float distance;
	float previous=0f;
	
	public GameController(GameModel model){
		this.model=model;
	}


		public boolean onTouch(View v, MotionEvent event) {
			synchronized(model){
			int action = event.getAction();
		      updateX=event.getX();
		      updateY=event.getY();
		       
			
			if (action == MotionEvent.ACTION_DOWN) {
		    	  
		           firstX = event.getRawX();
		           firstY = event.getRawY();
		           return true;
		       }else  if (action == MotionEvent.ACTION_MOVE){
		    	   if(Math.abs(updateX-firstX)>delta ){
		    		  
		    		   distance = firstX - event.getX();
		    		  //previous=distance;
		    		  if (distance >= 100 && !model.getGiraffe().getCooldown() && !model.getGiraffe().autoAttack) {
		    			  
		    				 model.getGiraffe().setToPrime();
		    				 model.getGiraffe().autoAttackTime=System.currentTimeMillis()+0;
		    				// model.getGiraffe().autoAttack=true;
		    				  
		    				  
		    				  if(model.getGiraffe().getHitBox().get(0).getX()>90){
		    				  model.getGiraffe().headBack();
		    			  }
		    			  
		    		  }
		    		  
		    	  }
		           return true;
		           }
		       else if(action==MotionEvent.ACTION_UP ) {
		    	   if((firstX+20<event.getX() || firstX-20>event.getX())
		    			   && updateX<firstX && !model.getGiraffe().getCooldown()){ 
			           model.getGiraffe().setTime();
			           model.getGiraffe().setToAttack();
			           model.getGiraffe().setCooldown(true);
			           
			           model.getGiraffe().headBackToNormal();
		    		   /**makes sure giraffe isnt currently jumping so theres no double jump**/
		    	   }else{
		    		  if(model.getGiraffe().getDoubleJump()<2){
		    			   model.getGiraffe().updateJumpCount();
		    			  model.getGiraffe().setJump(true);
		    		 }
		    	   }
		    	   return true;
		       }
		       return false;
			}
		}
}
