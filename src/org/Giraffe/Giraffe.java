package org.Giraffe;

import java.util.ArrayList;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class Giraffe extends Mechanics{

	ArrayList<Bitmap> images = new ArrayList <Bitmap>();
	ArrayList<Bitmap> states = new ArrayList <Bitmap>();
	protected ArrayList<HitBox> hitBox=new ArrayList<HitBox>();
	
	private boolean canCollide=true;
	private boolean attacking=false;
	protected long cooldownTime;
	
	protected boolean delayCollide=false;
	protected long delayCollideTime=System.currentTimeMillis();
	
	Context context;
	gState myState;
	
	/**State for giraffes neck position */
	private enum gState {NORMAL, JUMPING, PRIMED, ATTACKING, TOUNGE}
	
	long difference=0;
	long primeTime=0;
	long jumpTime=0;
	long autoAttackTime=0;
	
	boolean autoAttack=false;
	
	protected int doubleJumpCount=0;

	
	 /**Controls the neck-attack*/
	long NeckTimeFrozen;
	
	private boolean canJump=false;
	
	private int stopJumpCoordinate;
	private int bodyStopCoordinate;
	private int headStopCoordinate;
	private int killStopCoordinate;
	private float headHoldX;
	
	private int health;
	
	public Giraffe(Context context)
	{
		this.context = context;
		states.add(getDrawable("giraffe"));
		states.add(getDrawable("giraffe90b"));
		states.add(getDrawable("giraffe90f"));
		states.add(getDrawable("giraffetounge"));
		images.add(getDrawable("giraffe"));
		images.add(getDrawable("grunear"));
		
		setImageToDraw(states.get(0));
		
		myState = gState.NORMAL;
		
		coordinate = new Coordinate(0,260,imageToDraw.getWidth(), imageToDraw.getHeight());
		
		Coordinate head=new Coordinate(100, 310, 60, 50);
		hitBox.add(new HitBox("head", head, true));
		Coordinate body=new Coordinate(50, 380, 100, 70);
		hitBox.add(new HitBox("body", body, true));
		Coordinate killbox=new Coordinate(130, 330, 100, 120);
		hitBox.add(new HitBox("killbox", killbox, false));
		
		stopJumpCoordinate=coordinate.getY();
		headStopCoordinate=hitBox.get(0).getY();
		bodyStopCoordinate=hitBox.get(1).getY();
		killStopCoordinate=hitBox.get(2).getY();
		headHoldX=hitBox.get(0).getX();
		
		addHealth(5);
		setPic();
	}
	public Bitmap getDrawable(String d)
	 {
	  Integer identifier = this.context.getResources().getIdentifier(d, "drawable", "org.Giraffe");
	  return BitmapFactory.decodeResource(context.getResources(),identifier);
	 }
	
	public boolean canCollide(){
		return canCollide;
	}
	public void setCollide(boolean canCollide){
		this.canCollide=canCollide;
	}
	public ArrayList<HitBox> getHitBox(){
		return hitBox;
	}
	
	/**
	 * This method makes the giraffe disapear and reapear for 1.5 seconds only when it gets hit by an 
	 * enemy. After 1.5 seconds, the giraffe can collide again/can get hit.
	 * @param timeIn is used to check when the delay should start and end.
	 */
	public void delayCollideOneSecond(long timeIn){
		
		if(System.currentTimeMillis()-timeIn>1000){
			setImage(true);
			delayCollide=false;
		}else if(System.currentTimeMillis()-timeIn>800){
			setImage(false);
		}else if(System.currentTimeMillis()-timeIn>600){
			setImage(true);	
		}else if(System.currentTimeMillis()-timeIn>400){
			setImage(false);
		}else if(System.currentTimeMillis()-timeIn>200){
			setImage(true);
		}else if(System.currentTimeMillis()-timeIn>0){
			setImage(false);
		}
	}
	
	public void move(float timePassed) {		
		if(myState==gState.NORMAL){
			setImageToDraw(animation(images, 250));
		}

	}
	public void updateJumpCount(){
		SoundManager.playSound(1);
		if(doubleJumpCount==2){
			doubleJumpCount=0;
		}
		doubleJumpCount++;
	}
	public int getDoubleJump(){return doubleJumpCount;}
	
	public void setToAttack() {
		
			myState = gState.ATTACKING;
		}
	public void setToJump() {myState = gState.JUMPING;  }
	public void setToPrime () {
		 
			myState = gState.PRIMED;
		}
	public void setToNormal () {myState = gState.NORMAL; }
	public void setTime () {primeTime = System.currentTimeMillis()+0;}
	public void setCooldownTime() {cooldownTime = System.currentTimeMillis()+0;}
	public void updateTime() {difference=System.currentTimeMillis()-primeTime;}
	public String toString(){return "giraffe";}
	
	public boolean fall(Enemy enemyLandOn){
		
		
		//in order to understand this problem we most first look at the jump method....the only problem is that I cannot find the Jump method....therefore I cannot understand how this is coded.
		if(getHitBox().get(1).getX()>enemyLandOn.getHitBox().get(0).getX2() && !getJump()){
			doubleJumpCount=0;
			coordinate.setY(jump(getY(),stopJumpCoordinate, -1f, -.02f, jumpTime ));
			hitBox.get(0).setY(jump(hitBox.get(0).getY(), headStopCoordinate, -1f, -.02f, jumpTime));
			hitBox.get(1).setY(jump(hitBox.get(1).getY(), bodyStopCoordinate, -1f, -.02f, jumpTime ));
			hitBox.get(2).setY(jump(hitBox.get(2).getY(), killStopCoordinate, -1f, -.02f, jumpTime ));
			
			if(getY()==stopJumpCoordinate){
				doubleJumpCount=0;
				hitBox.get(0).setY(headStopCoordinate);
				hitBox.get(1).setY(bodyStopCoordinate);
				hitBox.get(2).setY(killStopCoordinate);
				
				return false;
			}
		}
		return true;
	}
	public void setPic() {		
		if(delayCollide){
			delayCollideOneSecond(delayCollideTime);
		}
		updateTime();
		
		//if you can jump!
		if(getJump()){
			int holdYJump=jump(coordinate.getY(), stopJumpCoordinate, 12f, -.02f, jumpTime);
			
			//if it has landed
			if(holdYJump==stopJumpCoordinate){
				setJump(false);
				doubleJumpCount=0;
				
				hitBox.get(0).setY(headStopCoordinate);
				hitBox.get(1).setY(bodyStopCoordinate);
				hitBox.get(2).setY(killStopCoordinate);
				
			}
			coordinate.setY(holdYJump);
			hitBox.get(0).setY(jump(hitBox.get(0).getY(), headStopCoordinate, 12f, -.02f, jumpTime));
			hitBox.get(1).setY(jump(hitBox.get(1).getY(), bodyStopCoordinate, 12f, -.02f, jumpTime));
			hitBox.get(2).setY(jump(hitBox.get(2).getY(), killStopCoordinate, 12f, -.02f, jumpTime));
			
			
			Log.d("JumpTimer" , "JumpTimer" + GameCall.jumptimer);
		}
	
		switch(myState) {
		case ATTACKING:
			setImageToDraw(states.get(2));
			hitBox.get(0).setY(-100);
				hitBox.get(2).collide(true);
				if (difference>=400){
					
					hitBox.get(2).collide(false);
					//hitBox.get(0).setY(headStopCoordinate);
					//autoAttack=false;
					myState=gState.NORMAL;
					setCooldownTime();
				}
			
		break;
		case TOUNGE:
			setImageToDraw(states.get(3));		
			if(difference>400){
				myState=gState.NORMAL;
			}
			break;
		case NORMAL:
			hitBox.get(0).setX(hitBox.get(1).getX()+50);
			hitBox.get(0).setY(hitBox.get(1).getY()-70);
			
			
			if (System.currentTimeMillis() - cooldownTime > 300) {
				setCooldown(false);
				
				//places the hitbox in the correct position after attack
			}
			
			move(System.currentTimeMillis());
		break;
		case PRIMED:
			if(System.currentTimeMillis()-autoAttackTime>1000){
				myState=gState.ATTACKING;
			}
			setImageToDraw(states.get(1));	
			
			
			break;
		}
	}
	public void headBack(){
		hitBox.get(0).setX((hitBox.get(0).getX()-110));
		hitBox.get(0).setY((hitBox.get(0).getY()+15));
	}
	public void headBackToNormal(){
		hitBox.get(0).setX(headHoldX);
		hitBox.get(0).setY(headStopCoordinate);
	}
	public void setJump(boolean canJ){
		jumpTime=System.currentTimeMillis()+0;
		canJump=canJ;
	}
	/**
	 * checks if the giraffe can jump.
	 * @return
	 */
	public boolean getJump(){return canJump;}
	
	/**
	 * This returns the image for the health to appear on screen and control gameover 
	 * @return strawberry image for health
	 */
	public Bitmap healthImage(){
		return getDrawable("strawberry");
	}
	public void addHealth(int health){this.health+=health;}
	public void loseHealth(int health){this.health-=health;}
	public int getHealth(){return health;}
	
	public void setCooldown (boolean attacking) {
		this.attacking = attacking;
		
	}
	public boolean getCooldown () {
		return attacking;
	}
	@Override
	public void setToScale(float xScale, float yScale) {
		coordinate.x = coordinate.x * xScale;
		coordinate.y = (int)(coordinate.y * yScale);
		coordinate.setHeight((int)(coordinate.getHeight()*xScale));
		coordinate.setWidth((int)(coordinate.getWidth()*xScale));
		
		for(HitBox hb:hitBox)
		{
			hb.setX(hb.getX()*xScale);
			hb.setY((int)(hb.getY()*yScale));
			hb.setWidth((int)(hb.getWidth()*xScale));
			hb.setHeight((int)(hb.getHeight()*yScale));
		}
	}
}
