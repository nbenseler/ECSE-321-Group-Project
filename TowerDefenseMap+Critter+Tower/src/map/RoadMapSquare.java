package map;

import java.util.ArrayList;

import critter.critter;
import tower.ISquareObserver;
import tower.ITower;
import tower.ITowerObserver;


public class RoadMapSquare extends MapSquare {				// Subclass of mapSquare

	//private int numberOfCritters=0;				// stores number of critters on the square currently
	private int xPosition;						// stores squares position
	private int yPosition;
	private RoadMapSquare nextSquare;
	private ArrayList<ISquareObserver> observers;		// arrayList of observers (towers observing this square)
	private ArrayList<critter> crittersOnSquare;
	protected boolean firstRoad=false;


	public void addCritter(critter c) {
		crittersOnSquare.add(c);
		this.notifyObservers();
	}

	public void removeCritter(critter c) {
		crittersOnSquare.remove(c);
		this.notifyObservers();
	}
	public RoadMapSquare getNextSquare() {
		return nextSquare;
	}

	public void setNextSquare(RoadMapSquare nextSquare) {
		this.nextSquare = nextSquare;
	}

	public boolean isFirstRoad() {
		return firstRoad;
	}

	public void setFirstRoad(boolean firstRoad) {
		this.firstRoad = firstRoad;
	}

	public RoadMapSquare(int x, int y)			//RoadMapSquare constructor
	{
		xPosition =x;
		yPosition =y;
		road = true;
		observers = new ArrayList<ISquareObserver>();
		crittersOnSquare = new ArrayList<critter>();

		//System.out.println("Created Road Square succesfully at: ("+xPosition+","+yPosition+")");
	}

	public ArrayList<ISquareObserver> getObservers()
	{
		return observers;
	}
	public void setObservers(ArrayList observers) {
		this.observers = observers;
	}
	
	public void removeObserver(ITower t)
	{
		this.observers.remove(t);
	}
	public void notifyObservers()					// Notifies the observers that a critter has been added to this square
	{
		for (ISquareObserver o: observers)
		{
			o.changeList(crittersOnSquare);
			//o.updateTowerCritterAdded(this);
		}
		//this.numberOfCritters++;
	}

	//public int getNumberOfCritters() {				
	//return numberOfCritters;
	//}
	//public void setNumberOfCritters(int numberOfCritters) {
	//this.numberOfCritters = numberOfCritters;
	//}
	public int getxPosition() {
		return xPosition;
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
	public void addObserver(ITower tower)			// add observers (towers) to the observer list of this square
	{
		observers.add(tower);
	}

}
