package domainLayer.critter;

import java.awt.Color;

import domainLayer.mapSquares.RoadMapSquare;
public class BossCritter extends Critter {
	private Color c;	

	public BossCritter(RoadMapSquare s) {
		super(s);
		c=Color.CYAN;
		speed = 0.02;
		radius=0.6;
		damage = 10;
		healthPoints = 20;
		monetaryValue = 100;
	}
	
	public Color getColor() {
		return c;
	}

}
