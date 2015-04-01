package Map;



public class Tower implements ITowerObserver {

	private String towerName;		
	private int xPosition;
	private int yPosition;
	private int towerRange=3;
	private int rateOfFire=1;
	private int damage=2;
	
	public Tower(String name,int x,int y)		//Tower Constructor
	{
		this.towerName = name;
		this.xPosition = x;
		this.yPosition = y;
	}

	@Override
	public void updateTowerCritterAdded(RoadMapSquare m) 		// this updates that a critter has been added at a position in range
	{
			System.out.println("There is an enemy in range of Tower(" + towerName +")"+ " The enemy is at position ("+m.getxPosition()+","+m.getyPosition()+")"+" FIRE!!!\n");
		
	}

	public int getTowerRange() {	
		return towerRange;
	}

	public void setTowerRange(int towerRange) {
		this.towerRange = towerRange;
	}

	@Override	
	public void updateTowerCritterRemoval(RoadMapSquare m) {		// this updates that critter has been removed from position in range
		System.out.println("An Enemy in the tower Range has been Removed: TowerName = " + towerName +" at position ("+xPosition+","+yPosition+")");
		System.out.println("The enemy was removed from position ("+m.getxPosition()+","+m.getyPosition()+")"+" HOLD FIRE!!!\n");
		
	}


}