package domainLayer.critter;

import java.awt.Color;

import domainLayer.mapSquares.RoadMapSquare;

public class AerialCritter2 extends Critter {

	private Color c;	
	
	public AerialCritter2(RoadMapSquare s) {
		super(s);
		c = Color.RED;
		speed = 0.0625;
		radius = 0.3;
		damage = 2;
		healthPoints = 25;
		monetaryValue = 15;
	}
	public Color getColor() {
		return c;
	}
}
