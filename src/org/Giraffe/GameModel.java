package org.Giraffe;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class GameModel {
	Context context;
	Giraffe jeremy;
	
	public boolean levelOver=false;
	private boolean levelLose=false;
	private boolean checkFall=false;
	private boolean laugh=false;
	private Enemy enemyLandOn;
	private long lastUpdate;
	private final static float DURATION = 250;

	
	private int score;
	
	ArrayList<Enemy>enemies=new ArrayList<Enemy>();
	ArrayList<Enemy>enemiesToDraw=new ArrayList<Enemy>();
	ArrayList<Enemy>enemiesCollide=new ArrayList<Enemy>();
	
	ArrayList<Background>backgrounds=new ArrayList<Background>();
	
	public GameModel(Context context){
		this.context=context;
		SoundManager.initSounds(context);
		SoundManager.addSound(1,R.raw.boing);  
		SoundManager.addSound(2,R.raw.laugh2); 
		SoundManager.addSound(3,R.raw.ow);  
		SoundManager.addSound(4,R.raw.psh); 
		LevelBuilder level=new LevelBuilder(GameState.getLevel(context), context);
		enemies=level.getEnemies();
		backgrounds=level.getBackgrounds();
		jeremy = new Giraffe(context);
		score=0;
		lastUpdate = System.currentTimeMillis();
	}
	
	public void updateLevel(long now)
	{
		float timePassed = (now - lastUpdate)/DURATION;
		enemiesToDraw.clear();
		jeremy.setPic();
		searchForCollision();
		if(checkFall){
			checkFall();
		}
		
		if(jeremy.getHealth()==0){
			if(laugh==false){
				SoundManager.playSound(2);  
				laugh=true;
			}
			
			levelLose=true;
			levelOver=true;
		}
		
		for(Background bg: backgrounds)
		{
			bg.move(timePassed);
		}
		
		for(Enemy enemy: enemies){
			enemy.move(timePassed);
			if((enemy.toString().equals("netv") || enemy.toString().equals("nstar1") 
					||enemy.toString().equals("ninjaglide2") )&& enemy.getX() < 600 && enemy.projectileDraw){	
					enemy.setImage(true);
					if(enemy.toString().equals("nstar1")||enemy.toString().equals("netv")){
						((GenericEnemy)enemy).jumping(false);
						enemy.moveDown = true;
					}
					
			}
			if(enemy.getX()<810 && !(enemy.getX2()<-5)){
				enemiesToDraw.add(enemy);
			}
		}
		lastUpdate = now;
	}
	
	public void gameOver(){
		if(levelLose){
			levelOver=false;
			((Activity) context).finish();
			Intent gameOverScreen = new Intent(context, GameOver.class);
			gameOverScreen.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			context.startActivity(gameOverScreen);
		}else{
			Music.stop(context);
			levelOver=false;
			((Activity) context).finish();
			Intent winScreen = new Intent(context, WinScreen.class);
			winScreen.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			context.startActivity(winScreen);
			}
		}
	public void checkFall(){
		checkFall=jeremy.fall(enemyLandOn);
	}
	
	public void searchForCollision() {
	    //one loop for all enemies on screen
		
		//then one loop for enemies v giraffe
		
		for(Enemy enemy: enemies){
			if(enemy.toString().equals("kapow") && enemy.getX2()<0){
				levelOver=true;
			}
			if(enemy.getX()<810 && !(enemy.getX2()<-5)){
				if(enemy.canCollide()&&jeremy.canCollide()){
				if(jeremy.getHitBox().get(1).landsOn(enemy.getHitBox().get(0)) && enemy.canLandOn()){
					enemyLandOn=enemy;
					jeremy.jumpTime=System.currentTimeMillis()+0;
					jeremy.doubleJumpCount=0;
					jeremy.setJump(false);
					checkFall=true;
				}
				else if((enemy.hitBox.get(0).collidesWith(jeremy.getHitBox().get(0)) && jeremy.getHitBox().get(0).collide)
				|| (enemy.hitBox.get(0).collidesWith(jeremy.getHitBox().get(1))&& jeremy.getHitBox().get(1).collide)){
					SoundManager.playSound(3);
					//enemy.collided();
					enemy.canCollideSet(false);
					if(enemy.canBeKilled){
						enemy.delayOfTime=System.currentTimeMillis()+0;
						enemy.delayImage=true;
					}
					
					if(!jeremy.delayCollide){
						SoundManager.playSound(3);
						jeremy.loseHealth(1);
						jeremy.delayCollideTime=System.currentTimeMillis()+0;
						jeremy.delayCollide=true;
					}
					if(enemy.toString().equals("netv")||enemy.toString().equals("nstar1")
							||enemy.toString().equals("ninjaglide2")){
						enemy.projectileDraw = false;
					}
				}else if(enemy.hitBox.get(0).collidesWith(jeremy.getHitBox().get(2))
						&&jeremy.getHitBox().get(2).collide&&enemy.canBeKilled){
					SoundManager.playSound(4);
					if(enemy.getY2()<240){
						score+=20;
						if(score>Integer.parseInt(OptionsMenu.SCORE))
						{
						SharedPreferences.Editor editor = 
							PreferenceManager.getDefaultSharedPreferences(context)
								.edit();
								editor.putString("highscore", score+"");
								editor.commit();
						}

						
					}else{
						score+=10;
						if(score>Integer.parseInt(OptionsMenu.SCORE))
						{
						SharedPreferences.Editor editor = 
							PreferenceManager.getDefaultSharedPreferences(context)
								.edit();
								editor.putString("highscore", score+"");
								editor.commit();
						}
					}
					enemy.collidedWithKillbox();
					enemy.delayOfTime=System.currentTimeMillis()+0;
					enemy.delayImage=true;
					enemy.canCollideSet(false);
					if(enemy.toString().equals("netv")||enemy.toString().equals("nstar1")
				||enemy.toString().equals("ninjaglide2")){
						enemy.projectileDraw = false;
					}
				}
			}
		}
		}
	  
	}
	public ArrayList<Background> getBackgrounds(){
		return backgrounds;
	}
	public ArrayList<Enemy> getEnemies(){
		return enemiesToDraw;
	}
	public Giraffe getGiraffe(){return jeremy;}
	public String getScore(){
		return ""+score;
	}
}
