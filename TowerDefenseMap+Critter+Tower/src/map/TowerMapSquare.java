package map;

import java.util.ArrayList;

import critter.critter;
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
		lastShootingTime = System.currentTimeMillis();
		critterList = new ArrayList<>();
	}
	public void setxPosition(int xPosition) {
		this.xPosition = xPosition;
	}
	public int getyPosition() {
		return yPosition;
	}
	public void setyPosition(int yPosition) {
		this.yPosition = yPosition;
	}
	public int getxPosition() {
		return xPosition;
	}
	
	public void attackCritters() {
		//System.out.println("CritterListSize = "+critterList.size());
		if (Math.abs(lastShootingTime - System.currentTimeMillis()) > (t.getRateOfFire()*1000))
		{
			//System.out.println("REady to shoot at critter; checking if critter list is empty");
			if (!critterList.isEmpty()) {
				System.out.println("Critter List is not empty - CurrentSize = "+ critterList.size());
				critter c = critterList.get(0);
				System.out.println("Shot first Critter in List");
				c.takeDamage((int)this.t.getDamage());
				System.out.println("Shooting at : "+ c);
				
				lastShootingTime = System.currentTimeMillis();	
			}
			
		}
	}
	
	
	
	public void removeCritterFromTowerSquare(critter crit)
	{
			critterList.remove(crit);
	}

	@Override
	public void changeList(ArrayList<critter> critterList) {
		this.critterList = critterList;
	}

}
