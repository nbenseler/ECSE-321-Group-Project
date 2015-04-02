package map;

import java.util.ArrayList;

import critter.critter;
import tower.ISquareObserver;
import tower.ITowerObserver;
import tower.ITower;

public class TowerMapSquare extends MapSquare implements ISquareObserver {
	private ITower t;				// the tower map square has a tower object associated to it
	private int xPosition;
	private int yPosition;
	private ArrayList<ITower> observers = new ArrayList<ITower>();
	private ArrayList<critter> critterList;
	private long lastShootingTime;
	
	public ITower getT() {			
		return t;
	}
	
	public void setT(ITower t) {		
		this.t = t;
	}
	
	public TowerMapSquare( int x, int y,ITower t)
	{
		xPosition = x;
		yPosition = y;
		road = false;
		this.t = t;
		lastShootingTime = 0;
		critterList = new ArrayList<>();
	}
	
	public void attackCritters() {
		if (Math.abs(lastShootingTime) - System.currentTimeMillis() > 1500)
		{
			if (!critterList.isEmpty()) {
				critter c = critterList.get(0);
				
				lastShootingTime = System.currentTimeMillis();	
			}
			
		}
	}
	
	
	
	public void removeCritterFromTowerSquare(critter c)
	{
		this.critterList.remove(c);
		
	}

	@Override
	public void changeList(ArrayList<critter> critterList) {
		// TODO Auto-generated method stub
		
	}
}
