package critterMain;

//This class will interact with the Map class in order to specify exactly where the critter can move
public class Position {
	
	private double xPos; //bounded by MAX_X_VAL
	private double yPos; //bounded by MAX_Y_VAL
	private double MAX_X_VAL; //based on length input from user
	private double MAX_Y_VAL; //based on width input from user
	
	public Position(double xIn, double yIn){
		
		this.xPos = xIn;
		this.yPos = yIn;
	}

	public double getxPos(){
		return this.xPos;
	}
	
	public double getyPos(){
		return this.yPos;
	}
	
	public double shortestDistanceTo(Position compare){
		
		return 0.0;
	}
}
