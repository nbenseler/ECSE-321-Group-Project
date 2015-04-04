package domainLayer.critter;

import java.awt.Color;
import java.awt.Color.*;
import java.util.ConcurrentModificationException;

import presentationLayer.Screen;
import domainLayer.controllers.GameController;
import domainLayer.mapSquares.RoadMapSquare;
import domainLayer.player.Player;

public abstract class Critter {
	private RoadMapSquare square;
	private double speed;
	private int damage;
	private int healthPoints;
	private double distance;
	private int monetaryValue;
	private double radius;
	private Color color;

	private double startingSpeed;
	private int steps;
	private long timeSinceFreeze;

	private double xPosition;
	private double yPosition;

	private boolean moveRight = true;
	private boolean moveLeft = false;
	private boolean moveUp = false;
	private boolean moveDown = false;

	private GameController gameController;
	private Player player;

	public Critter(RoadMapSquare square, double speed, int damage, int healthPoints, double distance, int monetaryValue, double radius, Color color)
	{
		this.square = square;
		this.speed = speed;
		this.damage = damage;
		this.healthPoints = healthPoints;
		this.distance = distance;
		this.monetaryValue = monetaryValue;
		this.radius = radius;
		this.color = color;

		this.startingSpeed = this.speed;
		timeSinceFreeze = 0;
		steps = 0;

		this.xPosition = square.getxPosition();
		this.yPosition = square.getyPosition();
		gameController = GameController.getInstance();
		player = Player.getUniqueInstance();
	}

	public void checkDirection()
	{
		if (distance <= 0)
		{
			distance = 1.0;
			steps = 0;
			if (square.getNextRoadMapSquare() == null)
			{
				moveRight = false;
				moveLeft = false;
				moveUp = false;
				moveDown = false;
				player.setLives(Player.getUniqueInstance().getLives() - damage);
				gameController.removeCritterFromScreen(this);

				
				//Start doing damage to base!!!!
			}
			else {
				RoadMapSquare next = (RoadMapSquare) square.getNextRoadMapSquare();

				if (next.getxPosition() > square.getxPosition()) {
					moveRight = true;
					moveLeft = false;
					moveUp = false;
					moveDown = false;
				}
				else if (next.getxPosition() < square.getxPosition()) {
					moveRight = false;
					moveLeft = true;
					moveUp = false;
					moveDown = false;
				}
				else if (next.getyPosition() > square.getyPosition()) {
					moveRight = false;
					moveLeft = false;
					moveUp = true;
					moveDown = false;
				}
				else if (next.getyPosition() < square.getyPosition()) {
					moveRight=false;
					moveLeft=false;
					moveUp =false;
					moveDown = true;
				}
				square.removeCritter(this);
				square = next;
				square.addCritter(this);
			}
		}
		if (System.currentTimeMillis() - timeSinceFreeze >= 5000) {
			speed = startingSpeed;
			timeSinceFreeze = System.currentTimeMillis();
		}
	}

	public RoadMapSquare getSquare() {
		return square;
	}

	public void setSquare(RoadMapSquare square) {
		this.square = square;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = Math.max(speed, 0.02);
		this.timeSinceFreeze = System.currentTimeMillis();
	}

	public int getHealthPoints() {
		return healthPoints;
	}

	public void setHealthPoints(int healthPoints) {
		this.healthPoints = healthPoints;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public int getSteps() {
		return steps;
	}

	public void setSteps(int steps) {
		this.steps = steps;
		distance = distance - speed;
	}

	public double getxPosition() {
		return xPosition;
	}

	public void setxPosition(double xPosition) {
		this.xPosition = xPosition;
	}

	public double getyPosition() {
		return yPosition;
	}

	public void setyPosition(double yPosition) {
		this.yPosition = yPosition;
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

	public int getDamage() {
		return damage;
	}

	public int getMonetaryValue() {
		return monetaryValue;
	}

	public double getRadius() {
		return radius;
	}

	public Color getColor() {
		return color;
	}

	public void takeDamage(int damage) {
		this.healthPoints -= damage;
		if (this.healthPoints <= 0) {
			gameController.removeCritterFromScreen(this);
			player.setMoney(player.getMoney() + monetaryValue);
		} 
	}

}