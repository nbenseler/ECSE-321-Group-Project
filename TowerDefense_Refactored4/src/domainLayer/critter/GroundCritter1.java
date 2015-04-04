package domainLayer.critter;

import java.awt.Color;

import domainLayer.mapSquares.RoadMapSquare;

public class GroundCritter1 extends GroundCritter {
	private final static double speed = 0.05;
	private final static int damage = 2;
	private final static int healthPoints = 40;
	private final static double distance = 0.20;
	private final static int monetaryValue = 15;
	private final static double radius = 0.4; 

	public GroundCritter1(RoadMapSquare square) {
		super(square, speed, damage, healthPoints, distance, monetaryValue, radius);
	}

}