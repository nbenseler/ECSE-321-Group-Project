package domainLayer.critter;

import java.awt.Color;
import java.awt.Color.*;

import presentationLayer.Screen;
import domainLayer.controllers.GameController;
import domainLayer.mapSquares.RoadMapSquare;
import domainLayer.player.Player;

public class Critter {
	private RoadMapSquare square;
	private double xPosition;
	private double yPosition;
	private boolean moveRight = true;
	private boolean moveLeft = false;
	private boolean moveUp = false;
	private boolean moveDown = false;
	private int steps;		
	protected double speed; 	// must divide evenly into 0.24 and 1.0
	protected double radius;
	private Color color;
	protected double distance;
	protected int damage;
	protected int healthPoints;
	protected int monetaryValue;
	protected GameController gameController;
	protected Player player;

	public Critter(RoadMapSquare s)
	{
		this.square = s;
		steps = 0;
		speed = 0.02;
		radius = 0.5;
		distance = 0.24;
		damage = 1;
		this.xPosition = s.getxPosition();
		this.yPosition = s.getyPosition();
		gameController = GameController.getInstance();
		player = Player.getUniqueInstance();
	}

	public Color getColor() {
		return color;
	}

	public int getSteps() {
		return steps;
	}

	public void setSteps(int steps) {
		this.steps = steps;
		distance = distance - speed;
	}

	public void checkDirection()
	{
		if(distance <= 0)
		{
			distance = 1.0;
			steps = 0;
			if(square.getNextRoadMapSquare() == null)
			{
				moveRight=false;
				moveLeft=false;
				moveUp =false;
				moveDown = false;

				Player.getUniqueInstance().setLives(Player.getUniqueInstance().getLives()-damage);			//Start doing damage to base!!!!
			}
			else{
				RoadMapSquare next = (RoadMapSquare) square.getNextRoadMapSquare();	
				if(next.getxPosition()>square.getxPosition())
				{
					moveRight=true;
					moveLeft=false;
					moveUp =false;
					moveDown = false;
				}
				else if(next.getxPosition()<square.getxPosition())
				{
					moveRight=false;
					moveLeft=true;
					moveUp =false;
					moveDown = false;
				}
				else if(next.getyPosition()>square.getyPosition())
				{
					moveRight=false;
					moveLeft=false;
					moveUp =true;
					moveDown = false;
				}
				else if(next.getyPosition()<square.getyPosition())
				{
					moveRight=false;
					moveLeft=false;
					moveUp =false;
					moveDown = true;
				}
				square.removeCritter(this);
				square=next;
				square.addCritter(this);
			}
		}	
	}

	public void updateDirection()
	{

	}

	public boolean isMoveRight() {
		return moveRight;
	}

	public void setMoveRight(boolean moveRight) {
		this.moveRight = moveRight;
	}

	public boolean isMoveLeft() {
		return moveLeft;
	}

	public void setMoveLeft(boolean moveLeft) {
		this.moveLeft = moveLeft;
	}

	public boolean isMoveUp() {
		return moveUp;
	}

	public void setMoveUp(boolean moveUp) {
		this.moveUp = moveUp;
	}

	public boolean isMoveDown() {
		return moveDown;
	}

	public void setMoveDown(boolean moveDown) {
		this.moveDown = moveDown;
	}

	public void setyPosition(double yPosition) {
		this.yPosition = yPosition;
	}

	public void setSpeed(double speed) {
		this.speed = Math.max(speed, 0.01);
	}

	public double getxPosition() {
		return xPosition;
	}

	public void setxPostion(double xPosition) {
		this.xPosition = xPosition;
	}

	public double getyPosition() {
		return yPosition;
	}

	public void setyPosition(int yPosition) {
		this.yPosition = yPosition;
	}

	public double getSpeed() {
		return speed;
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}
	

	public int getHealthPoints() {
		return healthPoints;
	}

	public void setHealthPoints(int healthPoints) {
		this.healthPoints = healthPoints;
	}

	public void takeDamage(int damage) {
		this.healthPoints -= damage;
		if (this.healthPoints <= 0) {
			System.out.println("Critter Just Died");
			gameController.removeCritterFromScreen(this);
			player.setMoney(player.getMoney() + monetaryValue);
		} 
	}

	public RoadMapSquare getSquare() {
		return square;
	}
	
}

