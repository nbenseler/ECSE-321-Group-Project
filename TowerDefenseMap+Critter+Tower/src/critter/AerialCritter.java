package critter;

import java.awt.Color;

import map.RoadMapSquare;

public class AerialCritter extends critter {

	public AerialCritter(RoadMapSquare s) {
		super(s);
		c=Color.ORANGE;
		speed =0.06;
		radius = 0.6;
		distance = 0.20;
		damage=2;
		healthPoints = 35;
	}

	private Color c;
	public Color getColor() {
		return c;
	}
}
