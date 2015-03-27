package critterMain;

import java.awt.image.BufferedImage; 
import java.util.ArrayList;



import observer.Subject;

public class Critter extends Subject{

	protected double speed;
	protected int hitPoints; //equivalent to reward the player receives for destroying the critter
	private Position pos;
	private boolean alive;
	protected String type = null;
	protected ArrayList<String> resist = new ArrayList<String>();
	//Each critter will take away one life from the player if it manages to get through the map
	protected BufferedImage sprite;
	private boolean speedHasChanged = false;

	/**
	 * Critter constructor, that adjusts the speed and hit points of the Critter object based on the level
	 * @param level
	 */
	public Critter(int level){
		setSpeed(level);
		setHitPoints(level);
		this.alive = true;
	}
	/**
	 * Deals damage to the critter based on the type of damage, done by calling the percentDamageTaken method
	 *  and sets the alive boolean to false if Critter hit points falls to zero
	 * @param damageGiven
	 * @param typeDamage
	 */
	public void takesDamage(int damageGiven, String typeDamage){

		this.hitPoints -= (int)damageGiven*percentDamageTaken(typeDamage);
		setChanged();
		notifyHitPointsObserver(this);
		if(this.hitPoints <= 0){
			this.hitPoints = 0;
			this.alive = false;
			setChanged();
			notifyAliveObserver(this);

		}
	}
	/**
	 * Iterates through the resist ArrayList and checks if the type of damage given, represented by the 
	 * string being passed into it is found within the list. If so it returns a value of 0.5 or half of the 
	 * original damage
	 * @param  type of damage being given to the critter
	 * @return double value representing the percent of damage taken
	 */
	private double percentDamageTaken(String typeDamage){
		for(String a : resist){
			if(typeDamage.equalsIgnoreCase(a)){
				return 0.5;
			}
		}
		return 1;
	}

	public void speedChanged(int percentChange){
		if(!this.speedHasChanged){
			this.speed = speed*percentChange;
			setChanged();
			notifySpeedObserver(this);
			this.speedHasChanged = true;
		}
	}

	public void moveTo(int x, int y){
		this.pos = new Position(x,y);
		setChanged();
		notifyPositionObserver(this);
	}

	//The following are the helper methods that get and set values for our Critter objects

	private void setSpeed(int level){
		this.speed = level;
	}
	private void setHitPoints(int level){
		this.hitPoints = level*20;
	}
	public double getSpeed(){
		return this.speed;
	}
	public int getHitPoints(){
		return this.hitPoints;
	}
	public boolean isAlive(){
		return this.alive;
	}
	public Position getPosition(){
		return this.pos;
	}

	//returns a string with all the critter information
	public String toString(){
		return "The critter type is : " + type + "; the speed is: " + speed + "; the hp is: " + hitPoints + 
				"; the position is: " + pos + "; is it alive: " + alive;
	}
}
