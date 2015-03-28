package critterMain;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class TrooperCritter extends Critter{

	/**
	 * The constructor for a critter of type Trooper which sets it's resistances and speed 
	 * also inherits from it's superclass Critter
	 * @param level
	 */
	
	public TrooperCritter(int level){
		super(level);
		
		
		try{
			sprite = ImageIO.read(new File("Users/ivanarredondo/Downloads/5309.png.gif"));
		}
		catch(IOException e){
			System.out.println("no sprite found");
		}
		editHP();
		this.resist.add("water");
		this.resist.add("laser");
		this.type = "Trooper";
	}
	
	private void editHP(){
		this.hitPoints *= 1.5;
	}


}
