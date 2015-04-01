package Map;

import java.awt.Color;
import java.awt.Color.*;
public class critter {
	private RoadMapSquare square;
	private double xPostion;
	private double yPosition;
	private boolean moveRight=true;
	private boolean moveLeft=false;
	private boolean moveUp=false;
	private boolean moveDown=false;
	private boolean firstTime = true;
	private int steps;		
	protected double speed; 	// must divide evenly into 0.24 and 1.0
	protected double radius;
	private Color c;
	protected double distance;
	protected int damage;
	
	public critter(RoadMapSquare s)
	{
		this.square =s;
		steps =0;
		speed =0.02;
		radius =0.5;
		distance = 0.24;
		damage =1;
	}
	public Color getColor() {
		return c;
	}


	public int getSteps() {
		return steps;
	}
	public void setSteps(int steps) {
		this.steps = steps;
	}
	public void checkDirection()
	{
		System.out.println("Checking direction");
		if (firstTime == true)
		{
			distance =0.24;
		}
		else
		{
			distance =1.0;
		}
		System.out.println("Steps*Speed = "+ (steps*speed));
		if(steps*speed ==(distance))
		{
			firstTime = false;
			steps = 0;
			if(square.getNextSquare()==null)
			{
				moveRight=false;
				moveLeft=false;
				moveUp =false;
				moveDown = false;

					Player.getUniqueInstance().setLives(Player.getUniqueInstance().getLives()-damage);			//Start doing damage to base!!!!
			}
			else{
				RoadMapSquare next = (RoadMapSquare) square.getNextSquare();	
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
			square=next;
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
		this.speed = speed;
	}

	public double getxPostion() {
		return xPostion;
	}

	public void setxPostion(double d) {
		this.xPostion = d;
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

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	
	
	

}
