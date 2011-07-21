package org.Giraffe;

public class HitBox{
	
	private String name;
	private Coordinate coordinate;
	protected boolean collide;
	
	public HitBox(String name,Coordinate coordinate, boolean collide){
		this.coordinate=coordinate;
		this.name=name;
		this.collide=collide;
	}

	public float getX(){return coordinate.getX();}
	public int getWidth(){return coordinate.getWidth();}
	public int getY(){return coordinate.getY();}
	public int getHeight(){return coordinate.getHeight();}
	public float getX2(){return coordinate.getX()+coordinate.getWidth();}
	public int getY2(){return coordinate.getY()+coordinate.getHeight();}
	public void setWidth(int w){coordinate.setWidth(w);}
	public void setHeight(int h){coordinate.setHeight(h);}
	public void setY(int y){
		coordinate.setY(y);
	}
	public void setX(float x){
		coordinate.setX(x);
	}
	
	public void collide(boolean collide){
		this.collide=collide;
	}
	
	public boolean landsOn(HitBox other){
		int otherX=(int)other.getX();
		int otherX2=(int)other.getX2();
		int otherY2=(int)other.getY2();
		int otherY1=(int)other.getY();	
		
		if((getX()<otherX2 && getX2()>otherX) && Math.abs(getY2()-otherY1)<6){
			return true;
		}
		
		
		return false;
	}
	public boolean collidesWith(HitBox other){
		
		if(!this.toString().equals(other.toString())){
			int otherX=(int)other.getX();
			int otherX2=(int)other.getX2();
			int otherY2=(int)other.getY2();
			int otherY1=(int)other.getY();	
			
			 if((getX()<otherX2 && getX2()>otherX) && (getY()<otherY2 && getY2()>otherY1)){
				 return true;
			 }
		}
		return false;
		
	}
	
	public String toString()
	{
		return name;
	}



}
