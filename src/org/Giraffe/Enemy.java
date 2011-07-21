package org.Giraffe;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Enemy extends Mechanics{
	protected boolean canCollide=true;
	protected int health;
	protected ArrayList<HitBox> hitBox=new ArrayList<HitBox>();
	protected ArrayList<Bitmap> deathImages=new ArrayList<Bitmap>();
	protected String name;
	protected boolean moveLeft=false;
	protected boolean moveRight=false;
	protected boolean moveUp=false;
	protected boolean moveDown=false;
	private boolean canLandOn=false;
	protected boolean projectileDraw=true;
	protected boolean canShoot = false;
	protected boolean canBeKilled=true;
	
	protected long delayOfTime;
	protected boolean delayImage=false;
	public int stopYCoordinate;
	public boolean jumping;
	public long jumpTime;
		
	public Enemy (ArrayList<Bitmap> images, ArrayList<Bitmap> deathImages,
			Coordinate coordinate, float speed, String name) {
		super.images = images;
		this.deathImages=deathImages;
		super.coordinate=coordinate;
		super.speed=speed;
		this.name=name;
		setImageToDraw(images.get(0));

	}
	
	public void collided(){
		setImageToDraw(deathImages.get(0));
	}
	public void collidedWithKillbox(){
		setImageToDraw(deathImages.get(0));
	}
	public void canCollideSet(boolean canCollide){
		this.canCollide=canCollide;
	}
	public boolean canCollide(){
		return canCollide;
	}
	public void setLandOn(boolean canLandOn){
		this.canLandOn=canLandOn;
	}
	public boolean canLandOn(){
		return canLandOn;
	}
	
	/**
	 * Delay of image to dissapear after collision. EX: helicopter to kapow to nothing.
	 */
	public void delayObstacleImage(long timeIn){
		if(System.currentTimeMillis()-timeIn>400){
			setImage(false);
		}
	}
	
	public void move(float timePassed){
		float currentSpeed = speed*timePassed;
		if(delayImage){
			delayObstacleImage(delayOfTime);
		}else{
			setImageToDraw(animation(images, 200));
			if(moveLeft){
				coordinate.setX(moveLeft(coordinate.getX(), currentSpeed));
				for(HitBox hb:hitBox){
					hb.setX(moveLeft(hb.getX(), currentSpeed));
				}
			}if(moveRight){
			
			}if(moveDown){
				coordinate.setY((int)moveDown(coordinate.getY(), -currentSpeed));
				for(HitBox hb:hitBox){
					hb.setY(coordinate.getY());
				}
			}if (moveUp){
			
			}if(jumping){
				coordinate.setY(jump(getY(), stopYCoordinate, speed/6, -.02f, jumpTime));
				if(getY()==stopYCoordinate){
					jumpTime=System.currentTimeMillis()+0;
				}
			}
		
		}
	}
	public void rangeAttack(){
		
	}
	public void moveHitBox(){
		for(int x=0; x<hitBox.size(); x++){
			hitBox.get(x);
		}
	}
	
	public  ArrayList<HitBox> getHitBox() {
		return hitBox;
		}
	public void setHealth(int h) {
		health=h;
		}
	public int getHealth() {
		return health;
	}
	public int loseHealth(int health, int minus){
		return health-minus;
	}
	public int addHealth(int health, int add){
		return health+add;
	}
	public  void shoot(String image, Coordinate c, float speed, int angle)
	{		
		
	}
	public  void moveParabola(){}
	public void fling(){}
	public void fall(){}

	@Override
	public void setToScale(float xScale, float yScale) {
		super.coordinate.x = super.coordinate.x * xScale;
		super.coordinate.y = (int)(super.coordinate.y * yScale);
		super.coordinate.setHeight((int)(super.coordinate.getHeight()*xScale));
		super.coordinate.setWidth((int)(super.coordinate.getWidth()*xScale));
		for(HitBox hb:hitBox)
		{
			hb.setX(hb.getX()*xScale);
			hb.setY((int)(hb.getY()*yScale));
			hb.setWidth((int)(hb.getWidth()*xScale));
			hb.setHeight((int)(hb.getHeight()*yScale));
		}
	}
	
}
