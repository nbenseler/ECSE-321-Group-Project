package critter;

import java.awt.Color;

import map.RoadMapSquare;
public class GroundCritter extends critter {
	private Color c;	

	public GroundCritter(RoadMapSquare s) {
		super(s);
		c=Color.RED;
		speed = 0.02;
		radius=0.5;
		damage = 1;
	}
	public Color getColor() {
		return c;
	}

}
