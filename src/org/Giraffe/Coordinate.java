package org.Giraffe;

public class Coordinate {
	
	public float x;
	public int y;
	private int width;
	private int height;
	
	public Coordinate (float x, int y, int width, int height) {
		this.x=x;
		this.y=y;
		this.width=width;
		this.height= height;
		
	}
	
	public void setWidth (int width) {
		this.width = width;
	}
	
	public void setHeight (int height) {
		this.height = height;
	}
	public int getWidth(){
		return width;
	}
	public int getHeight(){
		return height;
	}
	public float getX () {
		return this.x;
	}
	public int getY() {
		return this.y;
	}
	public void setX(float x){
		this.x=x;
	}
	public void setY(int y){
		this.y=y;
	}
	

}
