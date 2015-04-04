package domainLayer.critter;

import java.awt.Color;

import domainLayer.mapSquares.RoadMapSquare;

public abstract class GroundCritter extends Critter {
	private final static Color color = Color.ORANGE; 

	public GroundCritter(RoadMapSquare square, double speed, int damage, int healthPoints, double distance, int monetaryValue, double radius) {
		super(square, speed, damage, healthPoints, distance, monetaryValue, radius, color);
	}
}