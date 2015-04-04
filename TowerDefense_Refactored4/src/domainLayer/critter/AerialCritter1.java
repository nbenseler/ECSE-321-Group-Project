package domainLayer.critter;

import java.awt.Color;

import domainLayer.mapSquares.RoadMapSquare;

public class AerialCritter1 extends AerialCritter {
	private final static double speed = 0.05;
	private final static int damage = 3;
	private final static int healthPoints = 20;
	private final static double distance = 0.20;
	private final static int monetaryValue = 20;
	private final static double radius = 0.6;

	public AerialCritter1(RoadMapSquare square) {
		super(square, speed, damage, healthPoints, distance, monetaryValue, radius);
	}
}