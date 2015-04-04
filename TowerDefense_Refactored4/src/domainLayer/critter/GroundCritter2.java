package domainLayer.critter;

import java.awt.Color;

import domainLayer.mapSquares.RoadMapSquare;

public class GroundCritter2 extends GroundCritter {
	private final static double speed = 0.0625;
	private final static int damage = 2;
	private final static int healthPoints = 40;
	private final static double distance = 0.20;
	private final static int monetaryValue = 15;
	private final static double radius = 0.6;

	public GroundCritter2(RoadMapSquare square) {
		super(square, speed, damage, healthPoints, distance, monetaryValue, radius);
	}

}