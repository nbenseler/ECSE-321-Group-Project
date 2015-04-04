package presentationLayer;
import javax.swing.JFrame;

public class Frame extends JFrame{			//using JFrame as the basis for the scree/frame

	public Frame(int numberOfColumns, int numberOfRows)        			// constructor for frame
	{
		new JFrame();							// creates instance of jFrame
		
		
		//this.setSize(800, 600);								// gives dimensions of the jframe
		this.setTitle("Tower Defense Map");					// gives title to the frame
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);		// makes window close when x is hit
		this.setExtendedState(MAXIMIZED_BOTH);			// takes up entire screen to start - this can be activated along with the line below to make it fit the entire screen
		this.setUndecorated(true);
		// this.setResizable(true); 							// can be resized					
		this.setLocationRelativeTo(null); 					// makes the screen pop up in the middle of the screen
		Screen screen = new Screen(this, numberOfColumns, numberOfRows);				// passes the frame just created to the screen class - also the height and width specified by the user 
		this.add(screen);								// adds screen to the jframe - allows screen class to paint onto screen
		this.setVisible(true);	
	}
}
