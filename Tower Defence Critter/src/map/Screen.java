package map;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JPanel;


public class Screen extends JPanel implements Runnable{						//this extends the JPanel, and implements Runnable - meaning it calls the run method
	// this class will be the central class for the game
	Thread thread = new Thread(this);

	Frame frame;
	public int scene;					// the scene defines what screen you are on (EX. main menu, map creator...)

	public int hand = 0;				// this hand is used for the mouse clicking detection
	public int XPosOfCursor = 0;		// this variable represents the X position of the cursor on the screen	
	public int YPosOfCursor = 0;		// this variable represents the Y position of the cursor on the screen

	private int Money=500;				// this is the money variable, which keeps track of the players available funds
	private int Lives = 10;				// this is the lives variable which keeps track of the players remaining lives 
	private int XgridNumber;			// this variable represents the number of columns the user decided to have on the map
	private int YgridNumber;			// this variable represents the number of rows the user decided to have on the map
	private int horizontalSizeOfSqures; // this variable defines the width of one square of the map
	private int verticalSizeOfSqures;	// this variable defines the height of one square on the map
	private ArrayList<Integer> roadSquares = new ArrayList<>();	// this arrayList keeps track of where the user wanted to have the road
	private int frameWidth;				// this variable saves the users computer screen width
	private int frameHeight;			// this variable saves the users computer screen height

	private int numberOfTowers = 6; 	// this integer can be changed to accommodate more tower spots on the map
	private String validity = "";		// this string will be changed and used to print whether or not a map is valid

	public Screen(Frame frame, int width, int height)
	{
		XgridNumber = width;		// sets the number of columns to that specified by the user
		YgridNumber = height;		// sets the number of rows to that specified by the user

		this.frame = frame;

		thread.start();					// runs the run method

	}

	public void paintComponent (Graphics gr)		// this is what updates the screen when it repaints
	{
		gr.clearRect(0, 0, this.frame.getWidth(), this.frame.getHeight());     // clears the last layer - stops lag
		frameWidth = this.frame.getWidth();
		frameHeight = this.frame.getHeight();

		if(scene ==0)					// scene 0 is the welcome menu - not much functionality other than welcoming the user
		{
			gr.setColor(Color.BLUE);
			gr.fillRect(0, 0, this.frame.getWidth(), this.frame.getHeight());
			gr.setColor(Color.RED);
			gr.setFont(gr.getFont().deriveFont(18, 50));
			gr.drawString("Welcome to tower Defense", this.frame.getWidth()/8, this.frame.getHeight()/3);
			gr.setFont(gr.getFont().deriveFont(18, 25));
			gr.drawString("Press Space bar to continue to Game", this.frame.getWidth()/8, this.frame.getHeight()/2);
			gr.setFont(gr.getFont().deriveFont(18, 15));

		}
		else if (scene ==1)				// scene 1 is the main game menu - from here the user can start the game or create a map
		{
			gr.setColor(Color.LIGHT_GRAY);
			gr.fillRect(0, 0, this.frame.getWidth(), this.frame.getHeight());

			horizontalSizeOfSqures = this.frame.getWidth()/(4 + XgridNumber);
			verticalSizeOfSqures = this.frame.getHeight()/(4 + YgridNumber);

			for (int x = 0; x<XgridNumber;x++)				// double for loop creates the map
				for (int y=0; y<YgridNumber; y++)
				{
					gr.setColor(Color.GREEN);
					gr.fillRect(horizontalSizeOfSqures +(x*horizontalSizeOfSqures), verticalSizeOfSqures + (y*verticalSizeOfSqures),
							horizontalSizeOfSqures, verticalSizeOfSqures);
					gr.setColor(Color.BLACK);
					gr.drawRect(horizontalSizeOfSqures +(x*horizontalSizeOfSqures), verticalSizeOfSqures + (y*verticalSizeOfSqures),
							horizontalSizeOfSqures, verticalSizeOfSqures);
					if(y==YgridNumber/2)					// this if statement causes the initial map with the road through the middle to be created
					{
						gr.setColor(Color.DARK_GRAY);
						gr.fillRect(horizontalSizeOfSqures +(x*horizontalSizeOfSqures), verticalSizeOfSqures + (y*verticalSizeOfSqures),
								horizontalSizeOfSqures, verticalSizeOfSqures);
						gr.setColor(Color.BLACK);
						gr.drawRect(horizontalSizeOfSqures +(x*horizontalSizeOfSqures), verticalSizeOfSqures + (y*verticalSizeOfSqures),
								horizontalSizeOfSqures, verticalSizeOfSqures);

						roadSquares.add(horizontalSizeOfSqures +(x*horizontalSizeOfSqures));
						roadSquares.add(verticalSizeOfSqures + (y*verticalSizeOfSqures));
					}
				}
			// these are the texts on the screen
			gr.drawRect(horizontalSizeOfSqures, (int)((YgridNumber+1.25)*verticalSizeOfSqures),  (int)(1.5*horizontalSizeOfSqures),verticalSizeOfSqures);
			gr.drawString("Money:  " + Money, horizontalSizeOfSqures+2, (int)((YgridNumber+1.25)*verticalSizeOfSqures)+13);
			gr.drawRect(horizontalSizeOfSqures, (int)((YgridNumber+2.5)*verticalSizeOfSqures),  (int)(1.5*horizontalSizeOfSqures),verticalSizeOfSqures);
			gr.drawString("Lives: " + Lives, horizontalSizeOfSqures+2, (int)((YgridNumber+2.5)*verticalSizeOfSqures)+13);
			gr.setFont(gr.getFont().deriveFont(18, 20));
			gr.drawString("Press m to create", horizontalSizeOfSqures +(int)((XgridNumber+0.5)*horizontalSizeOfSqures),verticalSizeOfSqures) ;
			gr.drawString("custom map", horizontalSizeOfSqures +(int)((XgridNumber+0.5)*horizontalSizeOfSqures)+25,verticalSizeOfSqures+35);
			gr.drawString("Press s to start game", horizontalSizeOfSqures +(int)((XgridNumber+0.5)*horizontalSizeOfSqures),verticalSizeOfSqures+100) ;
			gr.setFont(gr.getFont().deriveFont(18, 15));			

			for (int x=0; x<((numberOfTowers/2)-1);x++)			// this draws the boxes for the towers
			{
				for (int y = 0; y<2; y++)
				{
					gr.drawRect((int)(horizontalSizeOfSqures*3.5) + (int)(0.75*x*horizontalSizeOfSqures) , (int)((YgridNumber+1.4)*verticalSizeOfSqures +y*verticalSizeOfSqures),  (int)(1.5*horizontalSizeOfSqures),verticalSizeOfSqures);
				}
			}
			gr.drawString("Towers:", (int)(horizontalSizeOfSqures*3.5), (int)((YgridNumber+1.4)*verticalSizeOfSqures)-1);

		}
		else if (scene == 2)				// scene 2 is the map creator
		{
			this.frame.addMouseListener (new MouseHandler(this));			// this checks for the mouse being used - the mouse used in any other scene is useless
			gr.setColor(Color.WHITE);
			gr.fillRect(0, 0, this.frame.getWidth(), this.frame.getHeight());

			int horizontalSizeOfSqures = this.frame.getWidth()/(4 + XgridNumber);
			int verticalSizeOfSqures = this.frame.getHeight()/(4 + YgridNumber);
			
			for (int x = 0; x<XgridNumber;x++)			// this creates the graph
				for (int y=0; y<YgridNumber; y++)
				{
					gr.setColor(Color.GREEN);
					gr.fillRect(horizontalSizeOfSqures +(x*horizontalSizeOfSqures), verticalSizeOfSqures + (y*verticalSizeOfSqures),
							horizontalSizeOfSqures, verticalSizeOfSqures);	
					for (int z = 0; z<roadSquares.size(); z+=2)				// this searches through the roadSquares array (which has the positions the user indicated for road) and paints those spots on the map
					{
						if (roadSquares.get(z)>horizontalSizeOfSqures +(x*horizontalSizeOfSqures)&&roadSquares.get(z+1)>(verticalSizeOfSqures + (y*verticalSizeOfSqures))&&
								roadSquares.get(z)<(2*horizontalSizeOfSqures +(x*horizontalSizeOfSqures)) && roadSquares.get(z+1)<(2*verticalSizeOfSqures + (y*verticalSizeOfSqures)))
						{
							if (z==0||z==2)			// this makes the entry and exit spots red for easier viewing
							{
								gr.setColor(Color.RED);
								gr.fillRect(horizontalSizeOfSqures +(x*horizontalSizeOfSqures), verticalSizeOfSqures + (y*verticalSizeOfSqures),
										horizontalSizeOfSqures, verticalSizeOfSqures);
							}
							else
							{
								gr.setColor(Color.DARK_GRAY);
								gr.fillRect(horizontalSizeOfSqures +(x*horizontalSizeOfSqures), verticalSizeOfSqures + (y*verticalSizeOfSqures),
										horizontalSizeOfSqures, verticalSizeOfSqures);	
							}
						}
					}
					gr.setColor(Color.BLACK);
					gr.drawRect(horizontalSizeOfSqures +(x*horizontalSizeOfSqures), verticalSizeOfSqures + (y*verticalSizeOfSqures),
							horizontalSizeOfSqures, verticalSizeOfSqures);

				}
			// defines the instructions
			gr.setFont(gr.getFont().deriveFont(18, 15));
			gr.drawString("Instructions:", horizontalSizeOfSqures +(int)((XgridNumber+0.5)*horizontalSizeOfSqures),verticalSizeOfSqures) ;
			gr.drawString("1) Select start point", horizontalSizeOfSqures +(int)((XgridNumber+0.5)*horizontalSizeOfSqures),verticalSizeOfSqures+35);
			gr.drawString(" - must be in column 1", horizontalSizeOfSqures +(int)((XgridNumber+0.5)*horizontalSizeOfSqures),verticalSizeOfSqures+70);
			gr.drawString("2) Select endpoint", horizontalSizeOfSqures +(int)((XgridNumber+0.5)*horizontalSizeOfSqures),verticalSizeOfSqures+105);
			gr.drawString(" - must be in last column", horizontalSizeOfSqures +(int)((XgridNumber+0.5)*horizontalSizeOfSqures),verticalSizeOfSqures+140);
			gr.drawString("3) Connect start to end point", horizontalSizeOfSqures +(int)((XgridNumber+0.5)*horizontalSizeOfSqures),verticalSizeOfSqures+175);
			gr.drawString(" - must connect smoothly", horizontalSizeOfSqures +(int)((XgridNumber+0.5)*horizontalSizeOfSqures),verticalSizeOfSqures+210);
			gr.drawString(" - must go start to end", horizontalSizeOfSqures +(int)((XgridNumber+0.5)*horizontalSizeOfSqures),verticalSizeOfSqures+245);
			gr.drawString(" Press F when finished", horizontalSizeOfSqures +(int)((XgridNumber+0.5)*horizontalSizeOfSqures),verticalSizeOfSqures+350);
			gr.drawString(validity, horizontalSizeOfSqures +(int)((XgridNumber+0.5)*horizontalSizeOfSqures),verticalSizeOfSqures+500);


		}
		else if (scene == 3){		// scene 3 is the start

			gr.setColor(Color.LIGHT_GRAY);
			gr.fillRect(0, 0, this.frame.getWidth(), this.frame.getHeight());

			for (int x = 0; x<XgridNumber;x++){			// this creates the graph
				for (int y=0; y<YgridNumber; y++){
					gr.setColor(Color.GREEN);
					gr.fillRect(horizontalSizeOfSqures +(x*horizontalSizeOfSqures), verticalSizeOfSqures + (y*verticalSizeOfSqures),
							horizontalSizeOfSqures, verticalSizeOfSqures);
					gr.setColor(Color.BLACK);
					gr.drawRect(horizontalSizeOfSqures +(x*horizontalSizeOfSqures), verticalSizeOfSqures + (y*verticalSizeOfSqures),
							horizontalSizeOfSqures, verticalSizeOfSqures);
					for(int i = 0; i < roadSquares.size(); i++){
						
							gr.setColor(Color.DARK_GRAY);
							gr.fillRect(roadSquares.get(i),roadSquares.get(i+1) , horizontalSizeOfSqures, verticalSizeOfSqures);
							gr.setColor(Color.BLACK);
							gr.drawRect(roadSquares.get(i),roadSquares.get(i+1) , horizontalSizeOfSqures, verticalSizeOfSqures);
							i++;
						
					}
				}
			}
		}

			else 
			{
				gr.setColor(Color.WHITE);
				gr.fillRect(0, 0, this.frame.getWidth(), this.frame.getHeight());
			}

			gr.setColor(Color.black);
		}

		public void run()
		{		

			scene = 0;

			while(true)				// while loop that runs the game
			{
				this.frame.addKeyListener(new KeyHandler(this));			// this waits for user to input commands through the keyboard
				repaint();			// repaints the screen continually


				try {
					Thread.sleep(20);					// this causes a pause so that it does not update the screen so quickly 
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}

		public class MouseHeld					// this class checks to see if the mouse is held
		{
			boolean mouseDown = false;			// change when mouse is used

			public void mouseMoved(MouseEvent e) {
				XPosOfCursor = e.getXOnScreen();
				YPosOfCursor = e.getYOnScreen();


			}

			public void updateMouse(MouseEvent e)
			{
				double xPos = e.getXOnScreen();
				double yPos = e.getYOnScreen();
				
				if (scene ==2)				// this checks to see if the mouse is being used in scene 2 and on the the map 
				{
					if (mouseDown && hand ==0)
					{
						if(xPos >=horizontalSizeOfSqures && xPos <= horizontalSizeOfSqures +((XgridNumber)*horizontalSizeOfSqures))
						{
							if (yPos >= verticalSizeOfSqures && yPos<= verticalSizeOfSqures + (YgridNumber*verticalSizeOfSqures))
							{

								if (roadSquares.isEmpty()) // this means you are clicking the entry point
								{	// this if statement checks if the entry point is valid (in leftmost column)
									if(xPos >=horizontalSizeOfSqures&&xPos<= horizontalSizeOfSqures +horizontalSizeOfSqures&&
											(yPos >= verticalSizeOfSqures && yPos<= verticalSizeOfSqures + (YgridNumber*verticalSizeOfSqures)))
									{
										roadSquares.add(((int)(Math.ceil((int)((xPos)/horizontalSizeOfSqures)))*horizontalSizeOfSqures)+1);
										roadSquares.add(((int)(Math.ceil((int)((yPos)/verticalSizeOfSqures)))*verticalSizeOfSqures)+1);
										System.out.println(roadSquares);
									}

								}
								else if (roadSquares.size()==2)		// if you are element 2 of the list you are typing in the end point (0 is x of entry, 1 i y of entry)
								{		//this checks if the selected point is a valid exit point
									if(xPos >=horizontalSizeOfSqures +((XgridNumber-1)*horizontalSizeOfSqures)&& xPos<=horizontalSizeOfSqures +((XgridNumber)*horizontalSizeOfSqures)&&
											(yPos >= verticalSizeOfSqures && yPos<= verticalSizeOfSqures + (YgridNumber*verticalSizeOfSqures)))
									{
										roadSquares.add(((int)(Math.ceil((int)((xPos)/horizontalSizeOfSqures)))*horizontalSizeOfSqures)+1);
										roadSquares.add(((int)(Math.ceil((int)((yPos)/verticalSizeOfSqures)))*verticalSizeOfSqures)+1);
										System.out.println(roadSquares);
									}
								}
								else if (roadSquares.get(roadSquares.size()-1) != yPos)	//this checks that there are no duplicates being added to the list in a row
								{
									roadSquares.add(((int)(Math.ceil((int)((xPos)/horizontalSizeOfSqures)))*horizontalSizeOfSqures)+1);
									roadSquares.add(((int)(Math.ceil((int)((yPos)/verticalSizeOfSqures)))*verticalSizeOfSqures)+1);
									System.out.println(roadSquares);
								}
							}

						}
					}
				}

			}


			public void mouseDown(MouseEvent e) {			// this checks if the mouse is pressed
				mouseDown = true;

				if(hand!=0)
				{
					hand = 0;				//when called changes hand to 0 which ininitates the updateMouse 
				}

				updateMouse(e);			// the mouse event e is sent from the MouseHandler Method

			}

		}

		public class KeyTyped					// this class is called when the keys are typed by the KeyHandler class
		{
			public void keyESC()				// these methods tell the associated actions when a key is pressed 
			{
				System.exit(0);			// esc exits the game

			}

			public void keySPACE() {
				scene = 1;				// space changes to scene 1

			}
			public void keyM() {
				roadSquares.clear();
				scene = 2;				// m goes to map builder

			}

			public void keyF() {		
				isValid(roadSquares);	// f checks validity of map

			}

			public void keyS(){
				scene = 3;  			 // if s is pressed goes to the start game screen
			}

			private void isValid(ArrayList<Integer> roadRoute) {		// this method checks to see if the map entered by the user is valid

				ArrayList<Integer> convertedRoute = new ArrayList<>();		
				for (int i = 0; i<roadRoute.size(); i+=2)				// this method fills the new array list with the old array list values, but reduced to integers 1,2,3... 
				{

					convertedRoute.add(((XgridNumber+4)*roadRoute.get(i))/frameWidth);

					convertedRoute.add(((YgridNumber+4)*roadRoute.get(i+1))/frameHeight);

				}

				boolean b = true;			// this boolean decides at the end shows if a map is valid. the for loops try to make b false, if b is true after all testing then the map is valid
				int greaterX = 0;
				int greaterY = 0;
				for(int i=0; i<convertedRoute.size();i+=2)
				{

					greaterX=0;
					greaterY=0;
					if (i==0)			// the first entry is the starting point while the second is the end point - this means that the first entry must be compared to the 5th not the third
					{
						greaterX = Math.abs(convertedRoute.get(i)-convertedRoute.get(i+4));
						greaterY = Math.abs(convertedRoute.get(i+1)-convertedRoute.get(i+4+1));

					}
					else if (i==2)		// the endpoint does not need to compare to anything so it is just given true values
					{
						greaterX=1;
						greaterY=0;
					}
					else if (i==convertedRoute.size()-2)	// this is the last entry in the arraylist and must be compared to the exit point value entry
					{
						greaterX = Math.abs(convertedRoute.get(i)-convertedRoute.get(2));
						greaterY = Math.abs(convertedRoute.get(i+1)-convertedRoute.get(3));

					}
					else		// the rest of the values are simply compared to the next value in the list
					{
						greaterX = Math.abs(convertedRoute.get(i)-convertedRoute.get(i+2));
						greaterY = Math.abs(convertedRoute.get(i+1)-convertedRoute.get(i+3));
					}

					if (greaterX+greaterY!=1)		// in order for a move to be valid, it must be 1 step in at most 1 direction
					{
						b=false;
						i=roadRoute.size();
					}

					if (b == true)
					{
						validity = "This map is valid";			// this prints on screen if map is valid
					}
					else
					{
						validity = "This map is invalid";	 // this prints on screen if map is invalid
						roadSquares.clear();
					}

				}
			}
		}
	} 