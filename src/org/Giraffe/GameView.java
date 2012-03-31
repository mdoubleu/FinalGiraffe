package org.Giraffe;


import java.util.LinkedList;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.widget.ImageSwitcher;
import android.widget.TextView;

public class GameView implements Callback{
	private GameController controller;
	private SurfaceHolder holder;
	private GameModel model;
	private GameThread thread;
	private Context context;
	public Resources Gres;
	private LinkedList<Bitmap> background;
	private float width;
	private float height;
	private TextView textview;
	public Scaling scale;

	
	public GameView(GameController controller,SurfaceHolder holder,GameModel model, Context context){
		this.controller=controller;
		this.holder = holder;
		this.model=model;
		this.context=context;
		Gres=context.getResources();


		
	}
	
	public void surfaceChanged(SurfaceHolder holdr, int format, int width, int height) {
		
	}


	public void surfaceCreated(SurfaceHolder holder) {
		if (thread == null) {
			thread = new GameThread(model,this,controller);
			thread.start();
			}
	}

	public int modelToViewX(float x){
		float gx=((x/800f)*width);
		return (int)gx;
	}
	public int modelToViewY(float y){
		float gx=((y/480f)*height);
		return (int)gx;
	}
	
	
	public void draw() {
		Canvas c = null;
		try {
			c = holder.lockCanvas();

			if (c != null) {
				doDraw(c);
			}
		} finally {
			if (c != null) {
				holder.unlockCanvasAndPost(c);
			}
		}
	}
	public void doDraw(Canvas canvas){
		float width=canvas.getWidth();
		float height=canvas.getHeight();
		scale =  new Scaling (width,height);
		
		Paint v = new Paint();
		v.setColor(Color.BLACK);
		v.setTextSize(40);
		v.setUnderlineText(true);
		
		long now = System.currentTimeMillis();
		for(Background bType:model.getBackgrounds()){
			for(Background b:bType.getToDraw(width))
			{
				canvas.drawBitmap(b.getImageToDraw(),b.coordinate.getX(), b.coordinate.getY(), null);
			}
		}
		for(Enemy e:model.getEnemies()){
			if(e.drawImage()){
				canvas.drawBitmap(e.getImageToDraw(),e.coordinate.getX(), e.coordinate.getY(), null);
			}
			//canvas.drawRect(e.getHitBox().get(0).getX(), e.getHitBox().get(0).getY(), e.getHitBox().get(0).getX2(),
			//		e.getHitBox().get(0).getY2(), v);
			
			//e.move();
		}
		if(model.getGiraffe().drawImage()){
			canvas.drawBitmap(model.getGiraffe().getImageToDraw(), model.getGiraffe().coordinate.getX(), model.getGiraffe().coordinate.getY(), null);
			
		}
		canvas.drawText(model.getScore(), width-65,100, v);
		
		for(int h=0; h<model.getGiraffe().getHealth(); h++){
			Bitmap health=model.getGiraffe().healthImage();
			canvas.drawBitmap(health, width-(40 * (h+1)), (30), null);
				
		}
		//shows hitboxes for Giraffe
		/*canvas.drawRect(model.getGiraffe().getHitBox().get(0).getX(),model.getGiraffe().getHitBox().get(0).getY(),
				model.getGiraffe().getHitBox().get(0).getX2(),model.getGiraffe().getHitBox().get(0).getY2(), v);
	
		canvas.drawRect(model.getGiraffe().getHitBox().get(1).getX(),model.getGiraffe().getHitBox().get(1).getY(),
				model.getGiraffe().getHitBox().get(1).getX2(),model.getGiraffe().getHitBox().get(1).getY2(), v);
		
		canvas.drawRect(model.getGiraffe().getHitBox().get(2).getX(),model.getGiraffe().getHitBox().get(2).getY(),
				model.getGiraffe().getHitBox().get(2).getX2(),model.getGiraffe().getHitBox().get(2).getY2(), v);  */
				
	
	}
	
	
    	
		/*Bitmap background1=background.get(0); 
		 
		 background1.createScaledBitmap(background1, (int)width, (int)height, true);
		
		canvas.drawBitmap(background1, model.bLocation1, 0, null);
		canvas.drawBitmap(background1, model.bLocation2, 0, null);
		*/
		/*Paint v = new Paint();
		v.setColor(Color.WHITE);
		v.setTextSize(30);
		v.setUnderlineText(true);

		
		//giraffeStuff
		GiraffeEntity graff=model.ourGiraffe;
		Drawable g=graff.getImage();
		g.setBounds(graff.X(), graff.Y(), graff.X2(), graff.Y2());
		g.draw(canvas);
		graff.move();*/
		
		/*2if(graff.getHitBox().size()>2){
			canvas.drawRect(graff.getHitBox().get(2).x1(),graff.getHitBox().get(2).y1(),
					graff.getHitBox().get(2).x2(),graff.getHitBox().get(2).y2(), v);
			
			
			canvas.drawRect(graff.getHitBox().get(0).x1(),graff.getHitBox().get(0).y1(),
					graff.getHitBox().get(0).x2(),graff.getHitBox().get(0).y2(), v);
			
			canvas.drawRect(graff.getHitBox().get(1).x1(),graff.getHitBox().get(1).y1(),
			graff.getHitBox().get(1).x2(),graff.getHitBox().get(1).y2(), v);
			
		}*/
		
		//Obstacles Stuff
		/*
		for(Effects e: model.getObjects()){
			e.move();
			
			//model.getEntities().get(x).doDraw()&&
			if(e.drawImage()){
				if(e.toString().equals("giraffe")){
				//	for(int h=0; h<graff.getHealth(); h++){
					//	Drawable health=graff.healthImage();
						//health.setBounds((int)width-(40 * (h+1)), 0, (int)width-(40 * h), 40);
						//canvas.drawText(graff.getScore(), width-65,70, v);
						
						//textview.setText(graff.getScore());
						//textview.draw(canvas);
						//health.draw(canvas);
						
					}
				}
				Drawable f=e.getImage();
				f.setBounds(e.X(),e.Y(),e.X2(),e.Y2());
				f.draw(canvas);
			}
		}*/
		
	//}

	public void surfaceDestroyed(SurfaceHolder holder) {
		try {
			thread.isRunning(false);
		} finally {
			thread = null;
		}
		
	}

}
