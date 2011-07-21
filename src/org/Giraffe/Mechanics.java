package org.Giraffe;

import java.util.ArrayList;
import android.graphics.Bitmap;

public abstract class Mechanics {
	protected Coordinate coordinate;
	protected float speed;
	/**This is the way images will be drawn and animated if necessary**/
	protected ArrayList<Bitmap> images;
	protected Bitmap imageToDraw;
	private boolean imageDraw=true;
	
	private long animationTime=System.currentTimeMillis()+0;
	private int animationCount=-1;
	
	private long delayOneSecond;

	/**
	 * Enemies/The Giraffe uses velocity used for physics jump
	 */
	private int gVel;
	
	public float getX(){return coordinate.getX();}
	public float getX2(){return coordinate.getX()+coordinate.getWidth();}
	public int getWidth(){return coordinate.getWidth();}
	public int getY(){return coordinate.getY();}
	public int getY2(){return coordinate.getY()+coordinate.getHeight();}
	public int getHeight(){return coordinate.getHeight();}
	public abstract void setToScale(float xScale, float yScale);
	
	public Bitmap getImageToDraw(){
		return imageToDraw;
	}
	public void setImageToDraw(Bitmap imageToDraw){
		this.imageToDraw=imageToDraw;
	}
	public abstract void move(float timePassed);
	
	public float moveLeft(float x, float speed){
		return x-speed;
		}
	
	public  void moveRight(){}
	public  void moveUp(){}
	public float moveDown(float y, float speed)
	{
		return moveLeft(y,speed);
	}
	
	public  void speedUp(){}
	public  void slowDown(){}
	public  void setSpeed(){}
	public Bitmap animation(ArrayList<Bitmap> images, int timeToWait){
		if(System.currentTimeMillis()-animationTime>timeToWait){
			if(animationCount+1==images.size()){
				animationCount=-1;
			}
			if(animationCount+1<images.size()){
				animationCount++;
			}
			animationTime=System.currentTimeMillis();
		}
		
		if(animationCount<0){
			animationCount=0;
		}
		return images.get(animationCount);
	}
	public boolean drawImage(){	return imageDraw;}
	public void setImage(boolean imageCheck){imageDraw=imageCheck;}
	
	public int jump(int y, int yStop, float velocity, float acceleration,  long staticTime)
	{
		long timeMil=System.currentTimeMillis()-staticTime;
		
		gVel=(int)(velocity+(acceleration*timeMil));
		if(y>=yStop+1){
			
			return yStop;
		}
		return (y-gVel);
		
	}

}
