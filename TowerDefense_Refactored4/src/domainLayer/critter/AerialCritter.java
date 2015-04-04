package domainLayer.critter;

import java.awt.Color;

import domainLayer.mapSquares.RoadMapSquare;

public abstract class AerialCritter extends Critter {
	private final static Color color = Color.RED; 

	public AerialCritter(RoadMapSquare square, double speed, int damage, int healthPoints, double distance, int monetaryValue, double radius) {
		super(square, speed, damage, healthPoints, distance, monetaryValue, radius, color);
	}
}