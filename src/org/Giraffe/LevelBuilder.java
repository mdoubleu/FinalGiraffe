package org.Giraffe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;
import java.lang.ClassLoader;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;


public class LevelBuilder {
	String readLine = null;
	int numberForBackground;
	Context context;
	GameView g;
	ArrayList<Background> backgrounds = new ArrayList<Background>();
	ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	
	public LevelBuilder(int level, Context context){

		this.context = context;
		try
		{
			levelMaker(getLevelResource(level, context));
		}
		catch(IOException e)
		{
			//do funny stuff
		}
		
		//bad code here!
        if(level==1)
        {
        	Music.create((Activity)context, R.raw.newcentralpark);
			Music.start((Activity)context);
			Music.setLooping((Activity)context, R.raw.newcentralpark);
        }
        if(level==2)
        {
        	Music.create((Activity)context, R.raw.chinatown);
			Music.start((Activity)context);
			Music.setLooping((Activity)context, R.raw.chinatown);
        }
	}
	
	/**
	 * 
	 * @param level integer for level number
	 * @param context current context
	 * @return Scanner for level file
	 * Finds an returns a Scanner handle for level file
	 */
	private Scanner getLevelResource(int level, Context context) throws IOException
	{
		String levelsource = "level" + level;
		Integer levelid = context.getResources().getIdentifier(levelsource, "raw", "org.Giraffe");
		if(levelid == 0)
			throw new IOException();
		InputStream is = context.getResources().openRawResource(levelid);
        return new Scanner(new InputStreamReader(is));
	}
	
	/**
	 * 
	 * @param file Scanner handle for the level file
	 * Goes through the file and process each line.
	 */
	private void levelMaker(Scanner file){
		while (file.hasNextLine()) {
			readLine = file.nextLine();
			processLine(readLine);
		}
	}
	
	/**
	 * 
	 * @param s String formatted with key:data
	 * @return key
	 */
	private String getKey(String s)
	{
		String[] results =  s.split(":");
		return results[0];
	}
	
	/**
	 * 
	 * @param s String formatted with key:data
	 * @return data
	 */	
	private String getRawData(String s)
	{
		String[] results =  s.split(":");
		return results[1];
	}
	
	/**
	 * 
	 * @param s either image or [image1, image2, image3, ...]
	 * @return an ArrayList of Bitmaps where imageX refers to a drawable
	 */
	private ArrayList<Bitmap> getRes(String s)
	{
		ArrayList<Bitmap> results = new ArrayList<Bitmap>();
		if(s.contains("["))
		{
			//multiple images
			//clean up []
			String cleaned = s.substring(1, s.length()-1);
			String[] images = cleaned.split(",");
			for(String image:images)
			{
				results.add(getDrawable(image));
			}
		}
		else
		{
			//single image
			results.add(getDrawable(s));
		}
		return results;
	}

	/**
	 * 
	 * @param s either "speed, x, y" or [speed1, x1, y1, speed2, x2, y2 ...]
	 * @return an ArrayList of all strings in s split with ","
	 */
	private ArrayList<String> getData(String s)
	{
		ArrayList<String> results = new ArrayList<String>();
		if(s.contains("["))
		{
			//multiple data points
			//clean up []
			String cleaned = s.substring(1, s.length()-1);
			String[] items = cleaned.split(",");
			for(String item:items)
			{
				results.add(item);
			}
		}
		else
		{
			//single data point
			results.add(s);
		}
		return results;
	}	
	
	/**
	 * 
	 * @param levelstring String for a line read from the level file
	 * Process the contents of the level file into backgrounds or enemies depending on type:
	 * each line contains:
	 * fill:boolean
	 * type:String
	 * res:String/[String, String, ...]
	 * deathres:String/[String, String, ...] OPTIONAL
	 * data:[int speed1, int x1, int y1, int speed2, int x2, int y2, ...]
	 * These items are delmited by " ; "
	 */
	private void processLine(String levelstring){
		 String[] items = levelstring.split(" ; ");
		 HashMap<String, String> keyMap = new HashMap<String,String>();
		 for(String item:items)
		 {
			 keyMap.put(getKey(item), getRawData(item));
		 }

		 //process res and optional deathres
		 ArrayList<Bitmap> images = getRes(keyMap.get("res"));
		 ArrayList<Bitmap> deathImages = null;
		 if(keyMap.containsKey("deathres"))
		 {
			 deathImages = getRes(keyMap.get("deathres"));
		 }
		 
		 int moveBox = 1;
		 if(keyMap.containsKey("moveBox"))
		 {
			 moveBox = Integer.parseInt(keyMap.get("moveBox"));
		 }
		 
		 //create the object instances and add to background or enemy lists
		 ArrayList<String> data = getData(keyMap.get("data"));
		 for(int i=0; i<data.size(); i+=3)
		 {
			 float speed = Float.parseFloat(data.get(i));
			 int x= Integer.parseInt(data.get(i+1));
			 int y = Integer.parseInt(data.get(i+2));
			 Coordinate coordinate=new Coordinate(x,y,images.get(0).getWidth(),images.get(0).getHeight());
			 if (keyMap.get("type").equals("StaticImage")){
				 Background bg=new Background(images, coordinate, speed, Boolean.parseBoolean(keyMap.get("fill")), moveBox);
				 backgrounds.add(bg); 
			 }
			 else if (keyMap.get("type").equals("Enemy")){
				GenericEnemy enemy =new GenericEnemy(images, deathImages,coordinate,speed,getData(keyMap.get("res")).get(0));
				if(keyMap.get("res").contains("helicopter")){
					enemy.moveLeft(true);
					enemy.getHitBox().add(new HitBox("helicopter", coordinate, true));
				}
				else if(keyMap.get("res").contains("netv")){
					enemy.moveLeft(true);
					enemy.setImage(false);
					enemy.getHitBox().add(new HitBox("netv", coordinate, true));
				}else if(keyMap.get("res").contains("icecream1")){
					enemy.moveLeft(true);
					enemy.getHitBox().add(new HitBox("icecream", coordinate, true));
					enemy.setLandOn(true);
				}
				else if(keyMap.get("res").contains("ninja") || keyMap.get("res").contains("ninjaglide") || keyMap.get("res").contains("nstar1")
						|| keyMap.get("res").contains("ninjaglide2")){
					enemy.moveLeft(true);
					enemy.getHitBox().add(new HitBox(keyMap.get("res"), coordinate, true));
					enemy.jumping(true);
					if(keyMap.get("res").contains("ninjaglide2") || keyMap.get("res").contains("nstar1") ){
						enemy.setImage(false);
					}
					
				}
				else if(keyMap.get("res").contains("cbus")){
					enemy.moveLeft(true);
					enemy.getHitBox().add(new HitBox("cbus", coordinate, true));
					enemy.setLandOn(true);
				}
				else if(keyMap.get("res").contains("cdragon")){
					enemy.moveLeft(true);
					enemy.getHitBox().add(new HitBox("cdragon", coordinate, true));
					enemy.setLandOn(true);
					enemy.canBeKilled=false;
				}
				else if(keyMap.get("res").contains("kapow")){
					enemy.moveLeft(true);
					enemy.getHitBox().add(new HitBox("kapow", coordinate, true));
					enemy.setImage(false);
					enemy.canCollideSet(false);
					
				}else if(keyMap.get("res").contains("netguys")){
					enemy.moveLeft(true);
					enemy.getHitBox().add(new HitBox("netguys", 
							new Coordinate((coordinate.getX()),coordinate.getY()+100,
									coordinate.getWidth()/8,(coordinate.getHeight())), true));
					enemy.getHitBox().get(0).setX(coordinate.getX()+100);
					enemy.canBeKilled=false;
				}
				else if(keyMap.get("res").contains("fireball")){
					enemy.moveLeft(true);
					enemy.getHitBox().add(new HitBox("fireball", coordinate, true));
					enemy.canBeKilled=false;
				}else if(keyMap.get("res").contains("tutorial1")||keyMap.get("res").contains("tutorial2")
						||keyMap.get("res").contains("tutorial3")){
					enemy.moveLeft(true);
					enemy.canCollideSet(false);
					enemy.getHitBox().add(new HitBox("tutorial", coordinate, false));
					enemy.canBeKilled=false;
				}
				enemies.add(enemy); 
			 }
		 }
	}
	
	public Bitmap getDrawable(String d)
	{
		Integer identifier = this.context.getResources().getIdentifier(d, "drawable", "org.Giraffe");
		return BitmapFactory.decodeResource(context.getResources(),identifier);
	}
	
	public ArrayList<Background> getBackgrounds(){
		return backgrounds;
	}
	
	public ArrayList<Enemy> getEnemies(){
		return enemies;
	}
	public String getLevel(){
		return readLine;
	}
	
}
