package domainLayer.critter;

import java.awt.Color;

import domainLayer.mapSquares.RoadMapSquare;

public class GroundCritter1 extends GroundCritter {
	
	public GroundCritter1(RoadMapSquare s) {
		super(s);
		c = Color.ORANGE;
		speed = 0.0625;
		radius = 0.5;
		damage = 2;
		healthPoints = 25;
		monetaryValue = 15;
	}

}
