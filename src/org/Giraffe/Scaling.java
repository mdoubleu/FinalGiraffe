package org.Giraffe;

import android.graphics.Bitmap;

public class Scaling {
	float width;
	float height;
	float scaledX;
	float scaledY;
	public float deltaX;
	public float deltaY;
	
	public Scaling (float width,float height) {
		this.width=width;
		this.height=height;
		deltaX = width/800;
		deltaY = height/480;
	}
	
	public Bitmap scaleImg (Background b) {
		return b.getImageToDraw().createScaledBitmap(b.getImageToDraw(),
				(int)(b.getImageToDraw().getWidth()*deltaY),
				(int)(b.getImageToDraw().getHeight()*deltaY), 
				true);
	}
	
	
	public float scaledX (Background b) {
		scaledX=deltaX*b.coordinate.getX();
		return scaledX;
	}
	
	public float scaledY (Background b) {
		scaledY=deltaY*b.coordinate.getY();
		return scaledY;
	}
	
	
	

}
