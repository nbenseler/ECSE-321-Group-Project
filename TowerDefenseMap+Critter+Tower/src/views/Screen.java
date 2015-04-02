package views;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

import javax.swing.JPanel;

import tower.GroundTower;
import tower.ITower;
import tower.IceTower;
import tower.TowerFactory;
import tower.UpgradeFactory;
import user.Player;
import critter.AerialCritter;
import critter.GroundCritter;
import critter.critter;
import map.ISquareObserver;
import map.MapSquare;
import map.RoadMapSquare;
import map.TowerMapSquare;


public class Screen extends JPanel implements Runnable{						//this extends the JPanel, and implements Runnable - meaning it calls the run method
														// this class will be the central class for the game
	Thread thread = new Thread(this);
	
	Frame frame;
	public int scene;					// the scene defines what screen you are on (EX. main menu, map creator...)
	MapNameFrame m = new MapNameFrame("hi");
	private static boolean  mapNameViewable = false;
	private static String mapNameText ="";
	public static void setMapNameViewable(boolean newMapNameViewable) {
		mapNameViewable = newMapNameViewable;
	}

	public static void setMapNameText(String newMapNameText) {
		mapNameText = newMapNameText;
	}
	public int upgradeInteger = 0;
	public int hand = 0;				// this hand is used for the mouse clicking detection
	public int XPosOfCursor = 0;		// this variable represents the X position of the cursor on the screen	
	public int YPosOfCursor = 0;		// this variable represents the Y position of the cursor on the screen
	private int firstRoadX;
	private int firstRoadY;
	private long lastCritter=0;
	private boolean groundTowerSelected =false;
	private boolean aerialTowerSelected =false;
	private boolean iceTowerSelected =false;
	private boolean compositeTowerSelected =false;
	
	//private int Money=500;				// this is the money variable, which keeps track of the players available funds
	//private int Lives = 10;				// this is the lives variable which keeps track of the players remaining lives 
	private int XgridNumber;			// this variable represents the number of columns the user decided to have on the map
	private int YgridNumber;			// this variable represents the number of rows the user decided to have on the map
	private int horizontalSizeOfSqures; // this variable defines the width of one square of the map
	private int verticalSizeOfSqures;	// this variable defines the height of one square on the map
	private ArrayList<Integer> roadSquares = new ArrayList<>();	// this arrayList keeps track of where the user wanted to have the road
	private int frameWidth;				// this variable saves the users computer screen width
	private int frameHeight;			// this variable saves the users computer screen height
	
	private int numberOfTowers = 6; 	// this integer can be changed to accommodate more tower spots on the map
	private String validity = "";		// this string will be changed and used to print whether or not a map is valid
	private int saveMap = 0;
	private int convertMap = 0;
	private int isValid = 0;
	private ArrayList<Integer> convertedRoute = new ArrayList<Integer>();
	private ArrayList<String> mapNames = new ArrayList<String>();
	private ArrayList<ArrayList<Integer>> storedMaps = new ArrayList<ArrayList<Integer>>();
	private static MapSquare[][] mapSquareLayout;
	private static ArrayList<critter> critterList = new ArrayList<critter>();
	private int numberOfCrittersThisWave;
	private ArrayList<TowerMapSquare> towerList= new ArrayList<TowerMapSquare>();
	
	public MapSquare[][] getMapSquareLayout() {
		return mapSquareLayout;
	}


	public void setMapSquareLayout(MapSquare[][] mapSquareLayout) {
		this.mapSquareLayout = mapSquareLayout;
	}
	
	public static void RemoveCritterFromScreen(critter c)
	{
		critterList.remove(c);
		((RoadMapSquare)mapSquareLayout[(int) c.getSquare().getxPosition()][c.getSquare().getyPosition()]).removeCritter(c);
	}

	public Screen(Frame frame, int width, int height)
	{
		XgridNumber = width;		// sets the number of columns to that specified by the user
		YgridNumber = height;		// sets the number of rows to that specified by the user
		m.setVisible(mapNameViewable);
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
			gr.drawString("Press M to create custom Map", this.frame.getWidth()/8, this.frame.getHeight()/2);
			gr.drawString("Press L to load Saved Map", this.frame.getWidth()/8, this.frame.getHeight()/2+50);
			gr.setFont(gr.getFont().deriveFont(18, 15));
			
		}

		else if (scene ==2)				// scene 2 is the map creator
		{
			m.setVisible(mapNameViewable);
			//this.frame.addMouseListener (new MouseHandler(this));			// this checks for the mouse being used - the mouse used in any other scene is useless
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
		else if (scene ==3)				// Load Prebuilt Map
		{
			gr.setFont(gr.getFont().deriveFont(25, 20));
			gr.drawString("Select a Map From the List", (int)frameWidth/3, verticalSizeOfSqures);
			for (int i=0;i<storedMaps.size();i++)
			{
				gr.drawString(""+(i+1)+ "     "+ mapNames.get(i)+" 		Size = " +storedMaps.get(i).get(0)+" x "+storedMaps.get(i).get(1), (int)frameWidth/3,verticalSizeOfSqures+(35*(i+1)));
			}
			
		}
		else if (scene ==4)				// Load Game Starts
		{
			if (groundTowerSelected)
			{
				int x=0;			// this draws the boxes for the towers
				int y = 0;
				gr.setColor(Color.ORANGE);
				gr.fillRect((int)(horizontalSizeOfSqures*3.5) + (int)(x*horizontalSizeOfSqures) , (int)((YgridNumber+1.4)*verticalSizeOfSqures +y*verticalSizeOfSqures),  (int)(horizontalSizeOfSqures),verticalSizeOfSqures);
			}
			else if (aerialTowerSelected)
			{
				int x=0;			// this draws the boxes for the towers
				int y = 1;
				gr.setColor(Color.RED);
				gr.fillRect((int)(horizontalSizeOfSqures*3.5) + (int)(x*horizontalSizeOfSqures) , (int)((YgridNumber+1.4)*verticalSizeOfSqures +y*verticalSizeOfSqures),  (int)(horizontalSizeOfSqures),verticalSizeOfSqures);
			}
			else if (iceTowerSelected)
			{
				int x=1;			// this draws the boxes for the towers
				int y = 0;
				gr.setColor(Color.BLUE);
				gr.fillRect((int)(horizontalSizeOfSqures*3.5) + (int)(x*horizontalSizeOfSqures) , (int)((YgridNumber+1.4)*verticalSizeOfSqures +y*verticalSizeOfSqures),  (int)(horizontalSizeOfSqures),verticalSizeOfSqures);
			}
			else if (compositeTowerSelected)
			{
				int x=1;			// this draws the boxes for the towers
				int y = 1;
				gr.setColor(Color.gray);
				gr.fillRect((int)(horizontalSizeOfSqures*3.5) + (int)(x*horizontalSizeOfSqures) , (int)((YgridNumber+1.4)*verticalSizeOfSqures +y*verticalSizeOfSqures),  (int)(horizontalSizeOfSqures),verticalSizeOfSqures);
			}
			//this.frame.addMouseListener (new MouseHandler(this));			// this checks for the mouse being used - the mouse used in any other scene is useless
			for (int x = 0; x<XgridNumber;x++)				// double for loop creates the map
				for (int y=0; y<YgridNumber; y++)
				{
						gr.setColor(Color.GREEN);
						gr.fillRect(horizontalSizeOfSqures +(x*horizontalSizeOfSqures), verticalSizeOfSqures + (y*verticalSizeOfSqures),
							horizontalSizeOfSqures, verticalSizeOfSqures);
						gr.setColor(Color.BLACK);
						gr.drawRect(horizontalSizeOfSqures +(x*horizontalSizeOfSqures), verticalSizeOfSqures + (y*verticalSizeOfSqures),
								horizontalSizeOfSqures, verticalSizeOfSqures);
//draw in road
				}
			for (int x = 0; x<XgridNumber;x++)			// this creates the graph
				for (int y=0; y<YgridNumber; y++)
				{
					gr.setColor(Color.GREEN);
					gr.fillRect(horizontalSizeOfSqures +(x*horizontalSizeOfSqures), verticalSizeOfSqures + (y*verticalSizeOfSqures),
							horizontalSizeOfSqures, verticalSizeOfSqures);	
					for (int z = 0; z<convertedRoute.size(); z+=2)				// this searches through the roadSquares array (which has the positions the user indicated for road) and paints those spots on the map
					{
						if ((convertedRoute.get(z)*frameWidth/(XgridNumber+4))+1>horizontalSizeOfSqures +(x*horizontalSizeOfSqures)&&(convertedRoute.get(z+1)*frameHeight/(YgridNumber+4))+1>(verticalSizeOfSqures + (y*verticalSizeOfSqures))&&
								(convertedRoute.get(z)*frameWidth/(XgridNumber+4))+1<(2*horizontalSizeOfSqures +(x*horizontalSizeOfSqures)) && (convertedRoute.get(z+1)*frameHeight/(YgridNumber+4))+1<(2*verticalSizeOfSqures + (y*verticalSizeOfSqures)))
						{

							gr.setColor(Color.DARK_GRAY);
							gr.fillRect(horizontalSizeOfSqures +(x*horizontalSizeOfSqures), verticalSizeOfSqures + (y*verticalSizeOfSqures),
									horizontalSizeOfSqures, verticalSizeOfSqures);	

						}
					}
			gr.setColor(Color.BLACK);
			gr.drawRect(horizontalSizeOfSqures +(x*horizontalSizeOfSqures), verticalSizeOfSqures + (y*verticalSizeOfSqures),
						horizontalSizeOfSqures, verticalSizeOfSqures);
				}
			// these are the texts on the screen
			gr.drawRect(horizontalSizeOfSqures, (int)((YgridNumber+1.25)*verticalSizeOfSqures),  (int)(1.5*horizontalSizeOfSqures),verticalSizeOfSqures);
			gr.drawString("Money:  " + Player.getUniqueInstance().getMoney(), horizontalSizeOfSqures+2, (int)((YgridNumber+1.25)*verticalSizeOfSqures)+13);
			gr.drawRect(horizontalSizeOfSqures, (int)((YgridNumber+2.5)*verticalSizeOfSqures),  (int)(1.5*horizontalSizeOfSqures),verticalSizeOfSqures);
			gr.drawString("Lives: " + Player.getUniqueInstance().getLives(), horizontalSizeOfSqures+2, (int)((YgridNumber+2.5)*verticalSizeOfSqures)+13);
			gr.setFont(gr.getFont().deriveFont(18, 15));			
			
			for (int x=0; x<((numberOfTowers/2)-1);x++)			// this draws the boxes for the towers
					{
						for (int y = 0; y<2; y++)
						{
							gr.drawRect((int)(horizontalSizeOfSqures*3.5) + (int)(x*horizontalSizeOfSqures) , (int)((YgridNumber+1.4)*verticalSizeOfSqures +y*verticalSizeOfSqures),  (int)(horizontalSizeOfSqures),verticalSizeOfSqures);
						}
					}
			gr.drawString("Towers:", (int)(horizontalSizeOfSqures*3.5), (int)((YgridNumber+1.4)*verticalSizeOfSqures)-1);
			gr.drawString("LEVEL:"+Player.getUniqueInstance().getLevel(), (int)(horizontalSizeOfSqures*XgridNumber)-(2*horizontalSizeOfSqures), (int)((YgridNumber+1.4)*verticalSizeOfSqures)-1);
			
			startWave1();
			//System.out.println("number of critters  = "+critterList.size());
			for(critter a: critterList)
			{
				gr.setColor(a.getColor());
				//System.out.println("XPostion to start = "+ a.getxPostion());
				//System.out.println("YPostion to start = "+ a.getyPosition());
				gr.fillOval((int)(a.getxPostion()*horizontalSizeOfSqures+(1*horizontalSizeOfSqures)), (int)(a.getyPosition()*verticalSizeOfSqures+1.25*verticalSizeOfSqures), (int)(a.getRadius()*horizontalSizeOfSqures), (int)(a.getRadius()*verticalSizeOfSqures));
			}
			for (TowerMapSquare a: towerList)
			{
				//System.out.println("tower is being told to attack critters");
				if (a.getT().isAerialShootingAbility()&&a.getT().isGroundShootingAbility())
				{
					gr.setColor(Color.gray);
				}
				else if(a.getT().isGroundShootingAbility())
				{
					gr.setColor(Color.ORANGE);
				}
				else if(a.getT().isAerialShootingAbility())
				{
					gr.setColor(Color.RED);
				}
				else if(a.getT().isIceShootingAbility())
				{
					gr.setColor(Color.BLUE);
				}
				//System.out.println("X position of square = "+a.getxPosition());
				//System.out.println("Y position of square = "+a.getyPosition());
				gr.fill3DRect((int)(a.getxPosition()*horizontalSizeOfSqures+(horizontalSizeOfSqures)), (int)(a.getyPosition()*verticalSizeOfSqures+verticalSizeOfSqures), (int) horizontalSizeOfSqures, (int)(verticalSizeOfSqures), true);
			}
			
			
		}
			

		else 
		{
			gr.setColor(Color.WHITE);
			gr.fillRect(0, 0, this.frame.getWidth(), this.frame.getHeight());
		}
		
		gr.setColor(Color.black);
	}
	
	private void startWave1() {
		for (critter a: critterList)
		{
			a.setSteps(a.getSteps()+1);
			a.checkDirection();
			if(a.isMoveRight()==true)
			{
			a.setxPostion(a.getxPostion()+a.getSpeed());
	
			}
			else if(a.isMoveLeft()==true)
			{
				a.setxPostion(a.getxPostion()-a.getSpeed());
			}
			else if(a.isMoveUp())
			{
				a.setyPosition(a.getyPosition()+a.getSpeed());
			}
			else if (a.isMoveDown())
			{
				a.setyPosition(a.getyPosition()-a.getSpeed());
			}
			else
			{
				
			}
			for (int x=0; x<((numberOfTowers/2)-1);x++)			//checks if you click in a tower box
			{
				for (int y = 0; y<2; y++)
				{
					//gr.drawRect((int)(horizontalSizeOfSqures*3.5) + (int)(x*horizontalSizeOfSqures) , (int)((YgridNumber+1.4)*verticalSizeOfSqures +y*verticalSizeOfSqures),  (int)(horizontalSizeOfSqures),verticalSizeOfSqures);
				}
			}
		}
		if (numberOfCrittersThisWave==0)
		{
			//System.out.println("Should add critter");
			//System.out.println("vertical Size of squares = "+verticalSizeOfSqures);
			//System.out.println("Frame height = "+ frameHeight);
			lastCritter = System.currentTimeMillis();
			RoadMapSquare start = (RoadMapSquare)mapSquareLayout[firstRoadX][firstRoadY];
			GroundCritter newGroundCritter = new GroundCritter(start);
			newGroundCritter.setxPostion(firstRoadX);
			newGroundCritter.setyPosition(firstRoadY);
			critterList.add(newGroundCritter);
			numberOfCrittersThisWave +=1;
		}
		else if (numberOfCrittersThisWave==10)
		{
			
		}
		else if (Math.abs(System.currentTimeMillis()-lastCritter)>10000)
		{
			if(numberOfCrittersThisWave%2==0)
			{
				lastCritter = System.currentTimeMillis();
				RoadMapSquare start = (RoadMapSquare)mapSquareLayout[firstRoadX][firstRoadY];
				AerialCritter newAerialCritter = new AerialCritter(start);
				newAerialCritter.setxPostion(firstRoadX);
				newAerialCritter.setyPosition(firstRoadY);
				critterList.add(newAerialCritter);
				numberOfCrittersThisWave +=1;
			}
			else
			{
				lastCritter = System.currentTimeMillis();
				RoadMapSquare start = (RoadMapSquare)mapSquareLayout[firstRoadX][firstRoadY];
				GroundCritter newGroundCritter = new GroundCritter(start);
				newGroundCritter.setxPostion(firstRoadX);
				newGroundCritter.setyPosition(firstRoadY);
				critterList.add(newGroundCritter);
				numberOfCrittersThisWave +=1;
			}
			
		}
		for (TowerMapSquare a: towerList)
		{
			//System.out.println("tower is being told to attack critters");
			a.attackCritters();
			
		}
	}

	public void run()
	{		
		Player p = Player.getUniqueInstance();
		p.setLevel(1);
		scene = 0;
		importMaps();
		this.frame.addMouseListener (new MouseHandler(this));
		this.frame.addKeyListener(new KeyHandler(this));
			
		while(true)				// while loop that runs the game
		{
			horizontalSizeOfSqures = this.frame.getWidth()/(4 + XgridNumber);
			verticalSizeOfSqures = this.frame.getHeight()/(4 + YgridNumber);
			//this.frame.addKeyListener(new KeyHandler(this));			// this waits for user to input commands through the keyboard
			repaint();			// repaints the screen continually

			
			try {
				Thread.sleep(40);					// this causes a pause so that it does not update the screen so quickly 
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	private void importMaps()
	{
		File file = new File("Maps.csv");
		try {
			Scanner sc = new Scanner(file);						// initializes scanner on file
			sc.useDelimiter(",");								// specifies new space to be separated by ,
			while (sc.hasNext())								// loops through to get all entries
			{
				ArrayList<Integer> tempMap = new ArrayList<Integer>();
				String input = new String(sc.nextLine());			// pulls the string as one piece from the csv file
				String noSpaceInput = input.replaceAll(" +", "");	// removes all spaces 
				String[] singleEntries = noSpaceInput.split(",");	// creates a sub array split at commas
				String mapName = singleEntries[0];
				mapNames.add(mapName);
				for (int i = 1; i<singleEntries.length; i++)
				{
					tempMap.add(Integer.parseInt(singleEntries[i]));
				}
				System.out.println("Map should have been added");
				storedMaps.add(tempMap);
																	
			}
			sc.close();												// scanner closed
		} catch (FileNotFoundException e) {							// scanner must be in try catch
			// TODO Auto-generated catch block
			e.printStackTrace();
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
			if (scene ==2)				// this checks to see if the mouse is being used in scene 2 and on the the map 
			{
				if (mouseDown && hand ==0)
				{
					if(e.getXOnScreen() >=horizontalSizeOfSqures && e.getXOnScreen()<= horizontalSizeOfSqures +((XgridNumber)*horizontalSizeOfSqures))
					{
						if (e.getYOnScreen() >= verticalSizeOfSqures && e.getYOnScreen()<= verticalSizeOfSqures + (YgridNumber*verticalSizeOfSqures))
								{
								
								if (roadSquares.isEmpty()) // this means you are clicking the entry point
								{	// this if statement checks if the entry point is valid (in leftmost column)
									if(e.getXOnScreen()>=horizontalSizeOfSqures&&e.getXOnScreen()<= horizontalSizeOfSqures +horizontalSizeOfSqures&&
											(e.getYOnScreen() >= verticalSizeOfSqures && e.getYOnScreen()<= verticalSizeOfSqures + (YgridNumber*verticalSizeOfSqures)))
									{
										roadSquares.add(e.getXOnScreen());
										roadSquares.add(e.getYOnScreen());
									}
									
								}
								else if (roadSquares.size()==2)		// if you are element 2 of the list you are typing in the end point (0 is x of entry, 1 i y of entry)
								{		//this checks if the selected point is a valid exit point
									if(e.getXOnScreen()>=horizontalSizeOfSqures +((XgridNumber-1)*horizontalSizeOfSqures)&&e.getXOnScreen()<=horizontalSizeOfSqures +((XgridNumber)*horizontalSizeOfSqures)&&
											(e.getYOnScreen() >= verticalSizeOfSqures && e.getYOnScreen()<= verticalSizeOfSqures + (YgridNumber*verticalSizeOfSqures)))
									{
										roadSquares.add(e.getXOnScreen());
										roadSquares.add(e.getYOnScreen());
									}
								}
								else if (roadSquares.get(roadSquares.size()-1) != e.getYOnScreen())	//this checks that there are no duplicates being added to the list in a row
								{
									roadSquares.add(e.getXOnScreen());
									roadSquares.add(e.getYOnScreen());
								}
								}
						
					}
				}
			}
			if (scene ==4)
			{
				if (groundTowerSelected||aerialTowerSelected||iceTowerSelected||compositeTowerSelected)
				{
					if(e.getXOnScreen() >=horizontalSizeOfSqures && e.getXOnScreen()<= horizontalSizeOfSqures +((XgridNumber)*horizontalSizeOfSqures))
					{
						if (e.getYOnScreen() >= verticalSizeOfSqures && e.getYOnScreen()<= verticalSizeOfSqures + (YgridNumber*verticalSizeOfSqures))
								{
									int convertedX = (int)(((XgridNumber+4)* e.getXOnScreen()/frameWidth)-1);
									int convertedY = (int)(((YgridNumber+4)* e.getYOnScreen()/frameHeight)-1);
									if (mapSquareLayout[convertedX][convertedY]==null)
									{
										TowerFactory tfactory = TowerFactory.getInstance();
										UpgradeFactory ufactory = UpgradeFactory.getInstance();
										ITower t1=null;
										if (groundTowerSelected)
										{
											t1 = tfactory.newTower("groundtower");
										}
										else if(aerialTowerSelected)
										{
											t1 = tfactory.newTower("aerialtower");
										}
										else if (iceTowerSelected)
										{
											t1 = tfactory.newTower("icetower");
										}
										else if (compositeTowerSelected)
										{
											//t1 = tfactory.newTower("compositetower");						//**BRENNAN
										}
										else
										{
											
										}
										if(Player.getUniqueInstance().getMoney()>=(t1.getValue()))
										{
											mapSquareLayout[convertedX][convertedY] = new TowerMapSquare(convertedX,convertedY,t1);
											System.out.println("New Tower Added!!!");
											towerList.add(((TowerMapSquare)mapSquareLayout[convertedX][convertedY]));
											Player.getUniqueInstance().setMoney(Player.getUniqueInstance().getMoney()-t1.getValue());
											subscribeTowerSquareToRoadSquares((TowerMapSquare) mapSquareLayout[convertedX][convertedY]);
										}
										else
										{
											System.out.println("Not enough Money!!!");
										}
									}
								}
					}
				}
				if (mouseDown && hand ==0)
				{
					if(e.getXOnScreen() >=(int)(3.5*horizontalSizeOfSqures) && e.getXOnScreen() <=(int)((3.5*horizontalSizeOfSqures)+(2*horizontalSizeOfSqures)))	//<=(int)(3.5*horizontalSizeOfSqures)+(((numberOfTowers/2)-2)*horizontalSizeOfSqures))
					{
						if (e.getYOnScreen() >= (int)((YgridNumber+1.4)*verticalSizeOfSqures) && e.getYOnScreen() <= (int)((YgridNumber+1.4)*verticalSizeOfSqures+(2*verticalSizeOfSqures)))
								{
									if(e.getXOnScreen()<(int)((3.5*horizontalSizeOfSqures)+horizontalSizeOfSqures))
									{
										if (e.getYOnScreen()<(int)((YgridNumber+1.4)*verticalSizeOfSqures+verticalSizeOfSqures)) 
										{
											groundTowerSelected = (!groundTowerSelected);
											aerialTowerSelected = false;
											iceTowerSelected = false;
											compositeTowerSelected = false;
										}
										else
										{
											groundTowerSelected = false;
											aerialTowerSelected = (!aerialTowerSelected);
											iceTowerSelected = false;
											compositeTowerSelected = false;
										}
									}
									else
									{
										if (e.getYOnScreen()<(int)((YgridNumber+1.4)*verticalSizeOfSqures+verticalSizeOfSqures)) 
										{
											groundTowerSelected = false;
											aerialTowerSelected = false;
											iceTowerSelected = (!iceTowerSelected);
											compositeTowerSelected = false;
										}
										else
										{
											groundTowerSelected = false;
											aerialTowerSelected = false;
											iceTowerSelected = false;
											compositeTowerSelected = (!compositeTowerSelected);
										}
									}
								}
					}
				}
			}
		}
		
		public void subscribeTowerSquareToRoadSquares(TowerMapSquare t1)
		{
			for(int i = Math.max((int)(t1.getxPosition()-t1.getT().getRange()), 0); i<=Math.min((int)(t1.getxPosition()+t1.getT().getRange()), mapSquareLayout.length-1);i++)
			{
				for (int j=Math.max((int)(t1.getyPosition()-t1.getT().getRange()),0);j<=Math.min((int)(t1.getyPosition()+t1.getT().getRange()),mapSquareLayout.length-1);j++)
					{
						if(mapSquareLayout[i][j]!=null&&mapSquareLayout[i][j].isRoad())
						{
							((RoadMapSquare)mapSquareLayout[i][j]).addObserver(((TowerMapSquare)mapSquareLayout[t1.getxPosition()][t1.getyPosition()]));
		
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

/*		public void keySPACE() {
			scene = 1;				// space changes to scene 1
			
		}*/
		public void keyM() {
			roadSquares.clear();
			convertedRoute.clear();
			saveMap =0;
			convertMap = 0;	
			isValid = 0;// ************** wrong spot put in start game button		
			scene = 2;				// m goes to map builder
			mapNameViewable = true;
			
			
		}
		

		public void keyF() {

			convertRoute(roadSquares);
			isValid();	// f checks validity of map

			
		}
		public void keyL() {
		scene = 3;
		convertMap = 0;
		}
		public void key1() {
			if(scene == 3)
			{
				XgridNumber = storedMaps.get(0).get(0);
				YgridNumber = storedMaps.get(0).get(1);
				ArrayList <Integer> temp = new ArrayList<Integer>();
				for (int i = 2; i<storedMaps.get(0).size(); i++)
				{
					temp.add(storedMaps.get(0).get(i));
				}
				convertedRoute=temp;
				convertMapSqaures(temp);
				scene = 4;
					
			}
		}
		public void key2() {
			if(scene == 3&&storedMaps.size()>1)
			{
				XgridNumber = storedMaps.get(1).get(0);
				YgridNumber = storedMaps.get(1).get(1);
				
				ArrayList <Integer> temp = new ArrayList<Integer>();
				for (int i = 2; i<storedMaps.get(1).size(); i++)
				{
					temp.add(storedMaps.get(1).get(i));
				}
				convertedRoute=temp;
				convertMapSqaures(temp);
				scene = 4;
					
			}
		}
		public void key3() {
			if(scene == 3&&storedMaps.size()>2)
			{
				XgridNumber = storedMaps.get(2).get(0);
				YgridNumber = storedMaps.get(2).get(1);
				ArrayList <Integer> temp = new ArrayList<Integer>();
				for (int i = 2; i<storedMaps.get(2).size(); i++)
				{
					temp.add(storedMaps.get(2).get(i));
				}
				convertedRoute=temp;
				convertMapSqaures(temp);
				scene = 4;
					
			}
		}
		public void key4() {
			if(scene == 3&&storedMaps.size()>3)
			{
				XgridNumber = storedMaps.get(3).get(0);
				YgridNumber = storedMaps.get(3).get(1);
				ArrayList <Integer> temp = new ArrayList<Integer>();
				for (int i = 2; i<storedMaps.get(3).size(); i++)
				{
					temp.add(storedMaps.get(3).get(i));
				}
				convertedRoute=temp;
				convertMapSqaures(temp);
				scene = 4;
					
			}
		}
		public void key5() {
			if(scene == 3&&storedMaps.size()>4)
			{
				XgridNumber = storedMaps.get(4).get(0);
				YgridNumber = storedMaps.get(4).get(1);
				ArrayList <Integer> temp = new ArrayList<Integer>();
				for (int i = 2; i<storedMaps.get(4).size(); i++)
				{
					temp.add(storedMaps.get(4).get(i));
				}
				convertedRoute=temp;
				convertMapSqaures(temp);
				scene = 4;
					
			}
		}
		public void key6() {
			if(scene == 3&&storedMaps.size()>5)
			{
				XgridNumber = storedMaps.get(5).get(0);
				YgridNumber = storedMaps.get(5).get(1);
				ArrayList <Integer> temp = new ArrayList<Integer>();
				for (int i = 2; i<storedMaps.get(5).size(); i++)
				{
					temp.add(storedMaps.get(5).get(i));
				}
				convertedRoute=temp;
				convertMapSqaures(temp);
				scene = 4;
			}
		}
		public void key7() {
			if(scene == 3&&storedMaps.size()>6)
			{
				XgridNumber = storedMaps.get(6).get(0);
				YgridNumber = storedMaps.get(6).get(1);
				ArrayList <Integer> temp = new ArrayList<Integer>();
				for (int i = 2; i<storedMaps.get(6).size(); i++)
				{
					temp.add(storedMaps.get(6).get(i));
				}
				convertedRoute=temp;
				convertMapSqaures(temp);
				scene = 4;
			}
		}
		public void key8() {
			if(scene == 3&&storedMaps.size()>7)
			{
				XgridNumber = storedMaps.get(7).get(0);
				YgridNumber = storedMaps.get(7).get(1);
				ArrayList <Integer> temp = new ArrayList<Integer>();
				for (int i = 2; i<storedMaps.get(7).size(); i++)
				{
					temp.add(storedMaps.get(7).get(i));
				}
				convertedRoute=temp;
				convertMapSqaures(temp);
				scene = 4;
			}
		}
		public void key9() {
			if(scene == 3&&storedMaps.size()>8)
			{
				XgridNumber = storedMaps.get(8).get(0);
				YgridNumber = storedMaps.get(8).get(1);
				ArrayList <Integer> temp = new ArrayList<Integer>();
				for (int i = 2; i<storedMaps.get(8).size(); i++)
				{
					temp.add(storedMaps.get(8).get(i));
				}
				convertedRoute=temp;
				convertMapSqaures(temp);
				scene = 4;
			}
		}
			public void keyT() {
				int xposition =6;
				int yposition =8;
				if(scene == 4&&mapSquareLayout[xposition][yposition]==null)
				{
					TowerFactory tfactory = TowerFactory.getInstance();
					UpgradeFactory ufactory = UpgradeFactory.getInstance();
					ITower t1 = tfactory.newTower("aerialtower");
					mapSquareLayout[xposition][yposition] = new TowerMapSquare(xposition,yposition,t1);
					
					
					for(int i = Math.max((int)(xposition-t1.getRange()), 0); i<=Math.min((int)(xposition+t1.getRange()), mapSquareLayout.length-1);i++)
					{
			
						for (int j=Math.max((int)(yposition-t1.getRange()),0);j<=Math.min((int)(yposition+t1.getRange()),mapSquareLayout.length-1);j++)
						{
							if(mapSquareLayout[i][j]!=null&&mapSquareLayout[i][j].isRoad())
							{
								((RoadMapSquare)mapSquareLayout[i][j]).addObserver(((TowerMapSquare)mapSquareLayout[xposition][yposition]));
								towerList.add(((TowerMapSquare)mapSquareLayout[xposition][yposition]));
			
							}
						}
					}
					//System.out.println("Tower is at position: "+(mapSquareLayout[xposition][yposition]).getClass());
					//System.out.println("The mapSquare at position (0,2) now has: "+ ((RoadMapSquare)mapSquareLayout[0][2]).getObservers().size() +"observer(s)");
					
				}
			}
			
			public void keyU() {
				
				if(upgradeInteger ==0)
				{
				upgradeInteger=1;
				int xposition =6;
				int yposition =8;
				if(scene == 4)
				{
					ITower t1 = ((TowerMapSquare) mapSquareLayout[xposition][yposition]).getT();
					System.out.println("Pre upgrade Tower Range: "+ t1.getRange());
					for(int i = Math.max((int)(xposition-t1.getRange()), 0); i<=Math.min((int)(xposition+t1.getRange()), mapSquareLayout.length-1);i++)
					{
			
						for (int j=Math.max((int)(yposition-t1.getRange()),0);j<=Math.min((int)(yposition+t1.getRange()),mapSquareLayout.length-1);j++)
						{
							if(mapSquareLayout[i][j]!=null&&mapSquareLayout[i][j].isRoad())
							{
								((RoadMapSquare)mapSquareLayout[i][j]).removeObserver(((TowerMapSquare)mapSquareLayout[xposition][yposition]));
								towerList.remove(((TowerMapSquare)mapSquareLayout[xposition][yposition]));
			
							}
						}
					}
					
					UpgradeFactory ufactory = UpgradeFactory.getInstance();

					t1 = ufactory.newUpgrade("range", ((TowerMapSquare) mapSquareLayout[xposition][yposition]).getT());
					((TowerMapSquare) mapSquareLayout[xposition][yposition]).setT(t1);
					
					for(int i = Math.max((int)(xposition-t1.getRange()), 0); i<=Math.min((int)(xposition+t1.getRange()), mapSquareLayout.length-1);i++)
					{
			
						for (int j=Math.max((int)(yposition-t1.getRange()),0);j<=Math.min((int)(yposition+t1.getRange()),mapSquareLayout.length-1);j++)
						{
							if(mapSquareLayout[i][j]!=null&&mapSquareLayout[i][j].isRoad())
							{
								//((TowerMapSquare)mapSquareLayout[xposition][yposition]).
								((RoadMapSquare)mapSquareLayout[i][j]).addObserver(((TowerMapSquare)mapSquareLayout[xposition][yposition]));
								towerList.add(((TowerMapSquare)mapSquareLayout[xposition][yposition]));
							}
						}
					}
					System.out.println("Pre upgrade Tower Range: "+ t1.getRange());
				}
			}
				
			}


		private void convertRoute(ArrayList<Integer> roadRoute)
		{
			if (convertedRoute.isEmpty())
			{
			for (int i = 0; i<roadRoute.size(); i+=2)				// this method fills the new array list with the old array list values, but reduced to integers 1,2,3... 
			{
					convertedRoute.add((((XgridNumber+4)*roadRoute.get(i))/frameWidth));

					convertedRoute.add((((YgridNumber+4)*roadRoute.get(i+1))/frameHeight));
					System.out.println(roadRoute.size());
			}

			}
		}
		private void isValid() {		// this method checks to see if the map entered by the user is valid
					
			/*for (int i = 0; i<roadRoute.size(); i+=2)				// this method fills the new array list with the old array list values, but reduced to integers 1,2,3... 
			{

					convertedRoute.add(((XgridNumber+4)*roadRoute.get(i))/frameWidth);

					convertedRoute.add(((YgridNumber+4)*roadRoute.get(i+1))/frameHeight);

			}*/
			if (isValid == 0)
			{
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
						i=convertedRoute.size();
					}
			}
			
			if (b == true)
					{
						validity = "This map is valid";	// this prints on screen if map is valid
						System.out.println("ConvertedRoute Size = "+ convertedRoute.size());
						convertMapSqaures(convertedRoute);							//*************************WRONG PLACE
						scene =4;
						saveMap(convertedRoute,XgridNumber,YgridNumber, mapNameText);
						isValid =1;
						
					} 
			else
			{
				validity = "This map was invalid try again";				// this prints on screen if map is invalid
				scene =0;
/*				roadSquares.clear();
				convertedRoute.clear();*/
			}
			}
			
		}
			
			

		private void saveMap(ArrayList<Integer> conRoute, int xgridNumber, int ygridNumber, String mapName) 
		{ 
			if (saveMap==0)	//needed due to multiple button presses being caught
			{
				if(storedMaps.size()<9)
				{
				storedMaps.add(convertedRoute);
				}
				else
				{
					storedMaps.remove(0);
					mapNames.remove(0);
					storedMaps.add(convertedRoute);
				}
			try {
				FileWriter fw = new FileWriter("Maps.csv");
				PrintWriter outputFile = new PrintWriter(fw);
				mapNames.add(mapName);
				//for(int i = 0; i<conRoute.size();i++)
				//outputFile.print(XgridNumber+","+YgridNumber +",");
				for (int y = 0; y<storedMaps.size();y++)
				{
				outputFile.print(mapNames.get(y)+",");
				if(y==storedMaps.size()-1)
				{
				outputFile.print(XgridNumber+","+YgridNumber +",");
				}
				for(Integer a : storedMaps.get(y))
				//for(int i = 0; i<conRoute.size();i++)
				{
					
				outputFile.print(a+",");
				//	outputFile.print(conRoute.get(i)+",");
				}
				outputFile.println();
				}
				outputFile.close();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.out.println("conroute size: " + conRoute.size());
/*			System.out.println(XgridNumber+" , "+YgridNumber +",");
			for(int x = 0; x<conRoute.size();x++)
			{
				System.out.print(conRoute.get(x) +",");
			}
			System.out.println("It Should have saved this data");*/
			saveMap=1;
		}
		}

		private void convertMapSqaures (ArrayList<Integer> conRoute) 
		{
			if (convertMap==0)	//needed due to multiple button presses being caught
			{
			System.out.println("YGridNumber = " + YgridNumber + " XgridNumber = " + XgridNumber);
			mapSquareLayout = new MapSquare[XgridNumber][YgridNumber];
			for (int i =0; i<conRoute.size(); i+=2)
			{
				int a = conRoute.get(i+1)-1;
				int b = conRoute.get(i)-1;
				if ( i==0)
				{
					firstRoadX = b;
					firstRoadY = a;
				}
				//System.out.println("conRoute.geti+1 = " + a);
				//System.out.println("conRoute.geti = " + b);
					mapSquareLayout[conRoute.get(i)-1][conRoute.get(i+1)-1] = new RoadMapSquare(conRoute.get(i)-1, conRoute.get(i+1)-1);
			}
			for (int i =0; i<conRoute.size(); i+=2)
			{
				if (i ==0)
				{
					RoadMapSquare m = (RoadMapSquare) mapSquareLayout[conRoute.get(i)-1][conRoute.get(i+1)-1];
					m.setNextSquare((RoadMapSquare)mapSquareLayout[conRoute.get(i+4)-1][conRoute.get(i+5)-1]);
					m.setFirstRoad(true);
					
				}
				else if (i == conRoute.size()-2)
				{
					RoadMapSquare m = (RoadMapSquare) mapSquareLayout[conRoute.get(i)-1][conRoute.get(i+1)-1];
					m.setNextSquare((RoadMapSquare)mapSquareLayout[conRoute.get(2)-1][conRoute.get(3)-1]);
				}
				else if (i ==2)
				{
					
				}
				else
				{
					RoadMapSquare m = (RoadMapSquare) mapSquareLayout[conRoute.get(i)-1][conRoute.get(i+1)-1];
					m.setNextSquare((RoadMapSquare)mapSquareLayout[conRoute.get(i+2)-1][conRoute.get(i+3)-1]);
				}
			}
			convertMap=1;
/*			System.out.println("the list being added is:");
			for (int j=0; j<conRoute.size();j++)
			{
				System.out.println(""+ conRoute.get(j));
			}
			System.out.println("The squares that make up the road are: ");
			
			RoadMapSquare m = (RoadMapSquare) mapSquareLayout[0][0];
			System.out.println("RoadSquare is at ("+m.getxPosition()+","+m.getyPosition()+")");
			while (m.getNextSquare()!=null)
			{
				m=(RoadMapSquare) m.getNextSquare();
				System.out.println("RoadSquare is at ("+m.getxPosition()+","+m.getyPosition()+")");
				
			}*/
			
		}
		}
}
}