package domainLayer.critter;

import java.awt.Color;

import domainLayer.mapSquares.RoadMapSquare;

public class GroundCritter extends Critter {
	protected Color c;	
	
	public GroundCritter(RoadMapSquare s) {
		super(s);
	}
	public Color getColor() {
		return c;
	}
}
