package domainLayer.critter;

import java.awt.Color;

import domainLayer.mapSquares.RoadMapSquare;

public class BossCritter extends Critter {
	private final static double speed = 0.05;
	private final static int damage = 5;
	private final static int healthPoints = 20;
	private final static double distance = 0.20;
	private final static int monetaryValue = 20;
	private final static double radius = 0.6;
	private final static Color color = Color.RED; 

	public BossCritter(RoadMapSquare square) {
		super(square, speed, damage, healthPoints, distance, monetaryValue, radius, color);
	}

}