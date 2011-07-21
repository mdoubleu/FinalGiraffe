package org.Giraffe;

import java.util.ArrayList;

import android.graphics.Bitmap;

public class Background extends Mechanics {
	private boolean fill;
	protected ArrayList<Background> repeats=new ArrayList<Background>();
	private float width;
	private int moveBox;
	private static final int OVERLAP = 10;
	
	public Background (ArrayList<Bitmap> images, Coordinate coordinate, float speed, boolean fill, int moveBox) {
		super.images = images; //will usually only contain 1 bitmap, more if background would have animation
		super.coordinate=coordinate;
		super.speed=speed;
		this.fill=fill;
		this.width=0;
		this.moveBox = moveBox;
		setImageToDraw(images.get(0));
	}
	
	public ArrayList<Background> getToDraw(float width){
		if(this.width == width)
		{
			//no change in width, mozy on.
		}
		else
		{
			//width change, recreate and cache the repeats array
			ArrayList<Background> temp = new ArrayList<Background>();
			if(fill)
			{
				float imgWidth = images.get(0).getWidth();
				int numImg = (int)(width/imgWidth);
				for(int i=0; i<width+(numImg+2)*OVERLAP; i+=(imgWidth-OVERLAP))
				{
					temp.add(new Background(images, 
							new Coordinate(i, coordinate.y, coordinate.getWidth(), coordinate.getHeight()), 
							speed,
							fill,
							moveBox));
				}
			}
			else
			{
				temp.add(new Background(images, 
						new Coordinate(coordinate.x, coordinate.y, coordinate.getWidth(), coordinate.getHeight()), 
						speed,
						fill,
						moveBox));
			}
			repeats = temp;
			this.width = width;
		}
		return repeats;
	}

	@Override
	public void move(float timePassed) {
		if(repeats.size()>0)
		{
			float imgWidth = images.get(0).getWidth();
			for(Background bg:repeats) //move all Backgrounds in repeats
			{
				
				bg.coordinate.setX(moveLeft(bg.coordinate.getX(), speed*timePassed));
				if(fill && width!=0 && bg.coordinate.x <= -imgWidth)
				{
					bg.coordinate.x = width;
				}
				else if(!fill && width!= 0 && bg.coordinate.x <= -moveBox)
				{
					bg.coordinate.x = width;
				}
			}
		}
	}

	@Override
	public void setToScale(float xScale, float yScale) {
		super.coordinate.x = super.coordinate.x * xScale;
		super.coordinate.y = (int)(super.coordinate.y * yScale);
		super.coordinate.setHeight((int)(super.coordinate.getHeight()*xScale));
		super.coordinate.setWidth((int)(super.coordinate.getWidth()*xScale));
		
		moveBox = (int)(moveBox*xScale);
		
		for(Background bg:repeats)
		{
			bg.coordinate.x = bg.coordinate.x * xScale;
			bg.coordinate.y = (int)(bg.coordinate.y * yScale);			
			bg.coordinate.setHeight((int)(bg.coordinate.getHeight()*xScale));
			bg.coordinate.setWidth((int)(bg.coordinate.getWidth()*xScale));
		}
	}
}
