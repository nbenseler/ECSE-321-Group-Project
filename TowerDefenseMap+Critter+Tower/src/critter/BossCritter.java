package critter;

import java.awt.Color;

import map.RoadMapSquare;
public class BossCritter extends critter {
	private Color c;	

	public BossCritter(RoadMapSquare s) {
		super(s);
		c=Color.CYAN;
		speed = 0.02;
		radius=0.6;
		damage = 10;
	}
	public Color getColor() {
		return c;
	}

}
