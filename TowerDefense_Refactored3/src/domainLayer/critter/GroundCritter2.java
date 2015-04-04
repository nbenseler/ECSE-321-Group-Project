package domainLayer.critter;

import java.awt.Color;

import domainLayer.mapSquares.RoadMapSquare;

public class GroundCritter2 extends GroundCritter {
	
	public GroundCritter2(RoadMapSquare s) {
		super(s);
		c = Color.ORANGE;
		speed = 0.05;
		radius = 0.6;
		damage = 2;
		healthPoints = 25;
		monetaryValue = 15;
	}

}
