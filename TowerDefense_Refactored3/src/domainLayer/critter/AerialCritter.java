package domainLayer.critter;

import java.awt.Color;

import domainLayer.mapSquares.RoadMapSquare;

public class AerialCritter extends Critter {

	public AerialCritter(RoadMapSquare s) {
		super(s);
		c=Color.RED;
		speed = 0.05;
		radius = 0.6;
		distance = 0.20;
		damage = 5;
		healthPoints = 35;
		monetaryValue = 20;
	}

	protected Color c;
	public Color getColor() {
		return c;
	}
}
