package map;

import map.TowerMapSquare;
import tower.GroundTower;
import tower.ITower;
import tower.ITowerObserver;



public class MapSquare {							// superclass of RoadMapSquare and TowerMapSquar
	private int xPosition;
	private int yPosition;
	protected boolean road;							// boolean defines if the square is a road or not
	

	public boolean isRoad() 
	{
		return road;
	}

}
