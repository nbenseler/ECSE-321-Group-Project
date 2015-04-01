package Map;

public class TowerMapSquare extends MapSquare {
	//private Tower t;				// the tower map square has a tower object associated to it
	private int xPosition;
	private int yPosition;
	/*public Tower getT() {			
		return t;
	}*/
	/*public void setT(Tower t) {		
		this.t = t;
	}*/
	public TowerMapSquare( int x, int y)
	{
		xPosition = x;
		yPosition = y;
		road = false;
		
	}
}
