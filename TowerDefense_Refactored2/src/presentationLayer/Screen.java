package presentationLayer;

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

import domainLayer.controllers.GameController;
import domainLayer.critter.AerialCritter;
import domainLayer.critter.Critter;
import domainLayer.critter.GroundCritter;
import domainLayer.mapSquares.IMapSquareObserver;
import domainLayer.mapSquares.MapSquare;
import domainLayer.mapSquares.RoadMapSquare;
import domainLayer.mapSquares.TowerMapSquare;
import domainLayer.player.Player;
import domainLayer.towers.AerialTower;
import domainLayer.towers.CompositeTower;
import domainLayer.towers.GroundTower;
import domainLayer.towers.ITower;
import domainLayer.towers.IceTower;
import domainLayer.towers.TowerFactory;
import domainLayer.towers.upgrades.DamageUpgrade;
import domainLayer.towers.upgrades.RangeUpgrade;
import domainLayer.towers.upgrades.RateOfFireUpgrade;
import domainLayer.towers.upgrades.UpgradeFactory;


public class Screen extends JPanel implements Runnable{						//this extends the JPanel, and implements Runnable - meaning it calls the run method
	// this class will be the central class for the game
	Thread thread = new Thread(this);

	Frame frame;
	public int scene;					// the scene defines what screen you are on (EX. main menu, map creator...)
	MapNameFrame namePopUpWindow = new MapNameFrame("hi");
	private static boolean mapNameViewable = false;
	private static String mapNameText = "";

	public static void setMapNameViewable(boolean newMapNameViewable) {
		mapNameViewable = newMapNameViewable;
	}

	public static void setMapNameText(String newMapNameText) {
		mapNameText = newMapNameText;
	}

	public int hand = 0;				// this hand is used for the mouse clicking detection
	public int XPosOfCursor = 0;		// this variable represents the X position of the cursor on the screen	
	public int YPosOfCursor = 0;		// this variable represents the Y position of the cursor on the screen
	private boolean groundTowerSelected = false;
	private boolean aerialTowerSelected = false;
	private boolean iceTowerSelected = false;
	private boolean compositeTowerSelected = false;
	private int numberOfColumns;			// this variable represents the number of columns the user decided to have on the map
	private int numberOfRows;			// this variable represents the number of rows the user decided to have on the map
	private int horizontalSizeOfSqures; // this variable defines the width of one square of the map
	private int verticalSizeOfSqures;	// this variable defines the height of one square on the map
	private ArrayList<Integer> roadSquares = new ArrayList<>();	// this arrayList keeps track of where the user wanted to have the road
	private int frameWidth;				// this variable saves the users computer screen width
	private int frameHeight;			// this variable saves the users computer screen height

	private String validity = "";		// this string will be changed and used to print whether or not a map is valid
	private ArrayList<Integer> convertedRoute = new ArrayList<Integer>();
	private ArrayList<String> mapNames = new ArrayList<String>();
	private ArrayList<ArrayList<Integer>> storedMaps = new ArrayList<ArrayList<Integer>>();
	private GameController gameController;
	private Player player;
	private int towerSelectedForUpgradeOrSaleX;
	private int towerSelectedForUpgradeOrSaleY;
	private boolean towerSelectedForUpgradeOrSale;

	public Screen(Frame frame, int numberOfColumns, int numberOfRows)
	{
		this.numberOfColumns = numberOfColumns;		// sets the number of columns to that specified by the user
		this.numberOfRows = numberOfRows;		// sets the number of rows to that specified by the user
		namePopUpWindow.setVisible(mapNameViewable);
		this.frame = frame;
		thread.start();					// runs the run method
		gameController = GameController.getInstance();
		player = Player.getUniqueInstance();
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

		else if (scene == 2)		// scene 2 is the map creator
		{
			namePopUpWindow.setVisible(mapNameViewable);
			gr.setColor(Color.WHITE);
			gr.fillRect(0, 0, this.frame.getWidth(), this.frame.getHeight());

			int horizontalSizeOfSqures = this.frame.getWidth()/(4 + numberOfColumns);
			int verticalSizeOfSqures = this.frame.getHeight()/(4 + numberOfRows);

			for (int x = 0; x<numberOfColumns;x++)			// this creates the graph
				for (int y=0; y<numberOfRows; y++)
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
			gr.drawString("Instructions:", horizontalSizeOfSqures +(int)((numberOfColumns+0.5)*horizontalSizeOfSqures),verticalSizeOfSqures) ;
			gr.drawString("1) Select start point", horizontalSizeOfSqures +(int)((numberOfColumns+0.5)*horizontalSizeOfSqures),verticalSizeOfSqures+35);
			gr.drawString(" - must be in column 1", horizontalSizeOfSqures +(int)((numberOfColumns+0.5)*horizontalSizeOfSqures),verticalSizeOfSqures+70);
			gr.drawString("2) Select endpoint", horizontalSizeOfSqures +(int)((numberOfColumns+0.5)*horizontalSizeOfSqures),verticalSizeOfSqures+105);
			gr.drawString(" - must be in last column", horizontalSizeOfSqures +(int)((numberOfColumns+0.5)*horizontalSizeOfSqures),verticalSizeOfSqures+140);
			gr.drawString("3) Connect start to end point", horizontalSizeOfSqures +(int)((numberOfColumns+0.5)*horizontalSizeOfSqures),verticalSizeOfSqures+175);
			gr.drawString(" - must connect smoothly", horizontalSizeOfSqures +(int)((numberOfColumns+0.5)*horizontalSizeOfSqures),verticalSizeOfSqures+210);
			gr.drawString(" - must go start to end", horizontalSizeOfSqures +(int)((numberOfColumns+0.5)*horizontalSizeOfSqures),verticalSizeOfSqures+245);
			gr.drawString(" Press F when finished", horizontalSizeOfSqures +(int)((numberOfColumns+0.5)*horizontalSizeOfSqures),verticalSizeOfSqures+350);
			gr.drawString(validity, horizontalSizeOfSqures +(int)((numberOfColumns+0.5)*horizontalSizeOfSqures),verticalSizeOfSqures+500);


		}
		else if (scene == 3)				// Load Prebuilt Map
		{
			gr.setFont(gr.getFont().deriveFont(25, 20));
			gr.drawString("Select a Map From the List", (int)frameWidth/3, verticalSizeOfSqures);
			for (int i=0;i<storedMaps.size();i++)
			{
				gr.drawString("" + (i+1) + "     " + mapNames.get(i)+" 		Size = " + storedMaps.get(i).get(0) + " x "+storedMaps.get(i).get(1), (int)frameWidth/3,verticalSizeOfSqures+(35*(i+1)));
			}

		}
		else if (scene == 4)				// Load Game Starts
		{
			if (groundTowerSelected)
			{
				int x = 0;			
				int y = 0;
				gr.setColor(Color.ORANGE);
				gr.fillRect((int)(horizontalSizeOfSqures*3.5) + (int)(x*horizontalSizeOfSqures) , (int)((numberOfRows+1.4)*verticalSizeOfSqures +y*verticalSizeOfSqures),  (int)(horizontalSizeOfSqures),verticalSizeOfSqures);
			}
			else if (aerialTowerSelected)
			{
				int x = 0;
				int y = 1;
				gr.setColor(Color.RED);
				gr.fillRect((int)(horizontalSizeOfSqures*3.5) + (int)(x*horizontalSizeOfSqures) , (int)((numberOfRows+1.4)*verticalSizeOfSqures +y*verticalSizeOfSqures),  (int)(horizontalSizeOfSqures),verticalSizeOfSqures);
			}
			else if (iceTowerSelected)
			{
				int x = 1;	
				int y = 0;
				gr.setColor(Color.BLUE);
				gr.fillRect((int)(horizontalSizeOfSqures*3.5) + (int)(x*horizontalSizeOfSqures) , (int)((numberOfRows+1.4)*verticalSizeOfSqures +y*verticalSizeOfSqures),  (int)(horizontalSizeOfSqures),verticalSizeOfSqures);
			}
			else if (compositeTowerSelected)
			{
				int x = 1;		
				int y = 1;
				gr.setColor(Color.gray);
				gr.fillRect((int)(horizontalSizeOfSqures*3.5) + (int)(x*horizontalSizeOfSqures) , (int)((numberOfRows+1.4)*verticalSizeOfSqures +y*verticalSizeOfSqures),  (int)(horizontalSizeOfSqures),verticalSizeOfSqures);
			}
			for (int x = 0; x<numberOfColumns;x++)				// double for loop creates the map
				for (int y=0; y<numberOfRows; y++)
				{
					gr.setColor(Color.GREEN);
					gr.fillRect(horizontalSizeOfSqures +(x*horizontalSizeOfSqures), verticalSizeOfSqures + (y*verticalSizeOfSqures),
							horizontalSizeOfSqures, verticalSizeOfSqures);
					gr.setColor(Color.BLACK);
					gr.drawRect(horizontalSizeOfSqures +(x*horizontalSizeOfSqures), verticalSizeOfSqures + (y*verticalSizeOfSqures),
							horizontalSizeOfSqures, verticalSizeOfSqures);
					//draw in road
				}
			for (int x = 0; x<numberOfColumns;x++)			// this creates the graph
				for (int y=0; y<numberOfRows; y++)
				{
					gr.setColor(Color.GREEN);
					gr.fillRect(horizontalSizeOfSqures +(x*horizontalSizeOfSqures), verticalSizeOfSqures + (y*verticalSizeOfSqures),
							horizontalSizeOfSqures, verticalSizeOfSqures);	
					for (int z = 0; z<convertedRoute.size(); z+=2)				// this searches through the roadSquares array (which has the positions the user indicated for road) and paints those spots on the map
					{
						if ((convertedRoute.get(z)*frameWidth/(numberOfColumns+4))+1>horizontalSizeOfSqures +(x*horizontalSizeOfSqures)&&(convertedRoute.get(z+1)*frameHeight/(numberOfRows+4))+1>(verticalSizeOfSqures + (y*verticalSizeOfSqures))&&
								(convertedRoute.get(z)*frameWidth/(numberOfColumns+4))+1<(2*horizontalSizeOfSqures +(x*horizontalSizeOfSqures)) && (convertedRoute.get(z+1)*frameHeight/(numberOfRows+4))+1<(2*verticalSizeOfSqures + (y*verticalSizeOfSqures)))
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
			gr.drawRect(horizontalSizeOfSqures, (int)((numberOfRows+1.25)*verticalSizeOfSqures),  (int)(1.5*horizontalSizeOfSqures),verticalSizeOfSqures);
			gr.drawString("Money:  " + Player.getUniqueInstance().getMoney(), horizontalSizeOfSqures+2, (int)((numberOfRows+1.25)*verticalSizeOfSqures)+13);
			gr.drawRect(horizontalSizeOfSqures, (int)((numberOfRows+2.5)*verticalSizeOfSqures),  (int)(1.5*horizontalSizeOfSqures),verticalSizeOfSqures);
			gr.drawString("Lives: " + Player.getUniqueInstance().getLives(), horizontalSizeOfSqures+2, (int)((numberOfRows+2.5)*verticalSizeOfSqures)+13);
			gr.setFont(gr.getFont().deriveFont(18, 15));			

			for (int x = 0; x < 2; x++)			// this draws the boxes for the towers
			{
				for (int y = 0; y < 2; y++)
				{
					gr.drawRect((int)(horizontalSizeOfSqures*3.5) + (int)(x*horizontalSizeOfSqures) , (int)((numberOfRows+1.4)*verticalSizeOfSqures +y*verticalSizeOfSqures),  (int)(horizontalSizeOfSqures),verticalSizeOfSqures);
				}
			}
			gr.drawString("Towers:", (int)(horizontalSizeOfSqures*3.5), (int)((numberOfRows+1.4)*verticalSizeOfSqures)-1);
			gr.drawString("LEVEL:"+Player.getUniqueInstance().getLevel(), (int)(horizontalSizeOfSqures*numberOfColumns)-(2*horizontalSizeOfSqures), (int)((numberOfRows+1.4)*verticalSizeOfSqures)-1);

			gameController.startWave();

			for(Critter critter: gameController.getCritterList())
			{
				gr.setColor(critter.getColor());
				gr.fillOval((int)(critter.getxPosition()*horizontalSizeOfSqures+(1*horizontalSizeOfSqures)), (int)(critter.getyPosition()*verticalSizeOfSqures+1.25*verticalSizeOfSqures), (int)(critter.getRadius()*horizontalSizeOfSqures), (int)(critter.getRadius()*verticalSizeOfSqures));
			}
			for (TowerMapSquare towerMapSquare: gameController.getTowerMapSquareList())
			{
				if (towerMapSquare.getTower().getClass() == CompositeTower.class)
				{
					gr.setColor(Color.gray);
				}
				else if(towerMapSquare.getTower().isGroundShootingAbility())
				{
					gr.setColor(Color.ORANGE);
				}
				else if(towerMapSquare.getTower().isAerialShootingAbility())
				{
					gr.setColor(Color.RED);
				}
				else if(towerMapSquare.getTower().isIceShootingAbility())
				{
					gr.setColor(Color.BLUE);
				}
				gr.fill3DRect((int)(towerMapSquare.getxPosition()*horizontalSizeOfSqures+(horizontalSizeOfSqures)), (int)(towerMapSquare.getyPosition()*verticalSizeOfSqures+verticalSizeOfSqures), (int) horizontalSizeOfSqures, (int)(verticalSizeOfSqures), true);
			}
		}
		gr.setColor(Color.black);
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
			horizontalSizeOfSqures = this.frame.getWidth()/(4 + numberOfColumns);
			verticalSizeOfSqures = this.frame.getHeight()/(4 + numberOfRows);
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
			Scanner scanner = new Scanner(file);						// initializes scanner on file
			scanner.useDelimiter(",");								// specifies new space to be separated by ,
			while (scanner.hasNext())								// loops through to get all entries
			{
				ArrayList<Integer> tempMap = new ArrayList<Integer>();
				String input = new String(scanner.nextLine());			// pulls the string as one piece from the csv file
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
			scanner.close();												// scanner closed
		} 
		catch (FileNotFoundException e) {							// scanner must be in try catch
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
			if (scene == 2)				// this checks to see if the mouse is being used in scene 2 and on the the map 
			{
				if (mouseDown && hand == 0)
				{
					if(e.getXOnScreen() >=horizontalSizeOfSqures && e.getXOnScreen()<= horizontalSizeOfSqures +((numberOfColumns)*horizontalSizeOfSqures))
					{
						if (e.getYOnScreen() >= verticalSizeOfSqures && e.getYOnScreen()<= verticalSizeOfSqures + (numberOfRows*verticalSizeOfSqures))
						{

							if (roadSquares.isEmpty()) // this means you are clicking the entry point
							{	// this if statement checks if the entry point is valid (in leftmost column)
								if(e.getXOnScreen()>=horizontalSizeOfSqures&&e.getXOnScreen()<= horizontalSizeOfSqures +horizontalSizeOfSqures&&
										(e.getYOnScreen() >= verticalSizeOfSqures && e.getYOnScreen()<= verticalSizeOfSqures + (numberOfRows*verticalSizeOfSqures)))
								{
									roadSquares.add(e.getXOnScreen());
									roadSquares.add(e.getYOnScreen());
								}

							}
							else if (roadSquares.size() == 2)		// if you are element 2 of the list you are typing in the end point (0 is x of entry, 1 i y of entry)
							{		//this checks if the selected point is a valid exit point
								if(e.getXOnScreen()>=horizontalSizeOfSqures +((numberOfColumns-1)*horizontalSizeOfSqures)&&e.getXOnScreen()<=horizontalSizeOfSqures +((numberOfColumns)*horizontalSizeOfSqures)&&
										(e.getYOnScreen() >= verticalSizeOfSqures && e.getYOnScreen()<= verticalSizeOfSqures + (numberOfRows*verticalSizeOfSqures)))
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
			if (scene == 4)
			{
				if (groundTowerSelected||aerialTowerSelected||iceTowerSelected||compositeTowerSelected)
				{
					if(e.getXOnScreen() >=horizontalSizeOfSqures && e.getXOnScreen()<= horizontalSizeOfSqures +((numberOfColumns)*horizontalSizeOfSqures))
					{
						if (e.getYOnScreen() >= verticalSizeOfSqures && e.getYOnScreen()<= verticalSizeOfSqures + (numberOfRows*verticalSizeOfSqures))
						{
							int convertedX = (int)(((numberOfColumns+4)* e.getXOnScreen()/frameWidth)-1);
							int convertedY = (int)(((numberOfRows+4)* e.getYOnScreen()/frameHeight)-1);

							if (gameController.getMapSquares()[convertedX][convertedY] == null)
							{
								if (groundTowerSelected && player.getMoney() >= GroundTower.cost)
								{
									gameController.addTower("groundTower", convertedX, convertedY);
								}
								else if(aerialTowerSelected && player.getMoney() >= AerialTower.cost)
								{
									gameController.addTower("aerialTower", convertedX, convertedY);
								}
								else if (iceTowerSelected && player.getMoney() >= IceTower.cost)
								{
									gameController.addTower("iceTower", convertedX, convertedY);
								}
								else if (compositeTowerSelected && player.getMoney() >= CompositeTower.cost)
								{
									// t1 = tfactory.newTower("compositetower");						// **BRENNAN**
								}
								System.out.println("New Tower Added!!!");
							}
						}
					}
				}
				else
				{
					if(e.getXOnScreen() >=horizontalSizeOfSqures && e.getXOnScreen()<= horizontalSizeOfSqures +((numberOfColumns)*horizontalSizeOfSqures))
					{
						if (e.getYOnScreen() >= verticalSizeOfSqures && e.getYOnScreen()<= verticalSizeOfSqures + (numberOfRows*verticalSizeOfSqures))
						{
							int convertedX = (int)(((numberOfColumns+4)* e.getXOnScreen()/frameWidth)-1);
							int convertedY = (int)(((numberOfRows+4)* e.getYOnScreen()/frameHeight)-1);
							if  (gameController.getMapSquares()[convertedX][convertedY]!=null && gameController.getMapSquares()[convertedX][convertedY].getClass()==TowerMapSquare.class)
							{
								towerSelectedForUpgradeOrSaleX = convertedX;
								towerSelectedForUpgradeOrSaleY = convertedY;
								towerSelectedForUpgradeOrSale = true;
								System.out.println("You Selected a tower MapSquare to be upgraded");

							}
							else
							{
								towerSelectedForUpgradeOrSale = false;
								System.out.println("You Un-Selected a tower MapSquare to be upgraded");
							}

						}
						else 
						{
							towerSelectedForUpgradeOrSale = false;
							System.out.println("You Un-Selected a tower MapSquare to be upgraded");
						}
					}
					else 
					{
						towerSelectedForUpgradeOrSale = false;
						System.out.println("You Un-Selected a tower MapSquare to be upgraded");
					}
				}
				if (mouseDown && hand ==0)
				{
					if(e.getXOnScreen() >=(int)(3.5*horizontalSizeOfSqures) && e.getXOnScreen() <=(int)((3.5*horizontalSizeOfSqures)+(2*horizontalSizeOfSqures)))	//<=(int)(3.5*horizontalSizeOfSqures)+(((numberOfTowers/2)-2)*horizontalSizeOfSqures))
					{
						if (e.getYOnScreen() >= (int)((numberOfRows+1.4)*verticalSizeOfSqures) && e.getYOnScreen() <= (int)((numberOfRows+1.4)*verticalSizeOfSqures+(2*verticalSizeOfSqures)))
						{
							towerSelectedForUpgradeOrSale = false;
							System.out.println("You Un-Selected a tower MapSquare to be upgraded");
							if(e.getXOnScreen()<(int)((3.5*horizontalSizeOfSqures)+horizontalSizeOfSqures))
							{
								if (e.getYOnScreen()<(int)((numberOfRows+1.4)*verticalSizeOfSqures+verticalSizeOfSqures)) 
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
								if (e.getYOnScreen()<(int)((numberOfRows+1.4)*verticalSizeOfSqures+verticalSizeOfSqures)) 
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

		public void keyM() {
			roadSquares.clear();
			convertedRoute.clear();	
			scene = 2;				// m goes to map builder
			mapNameViewable = true;
		}

		public void keyF() {
			if(scene ==2)
			{
				convertRoute(roadSquares);
				isValid();	// f checks validity of map
			}
			
			if(scene==4 && towerSelectedForUpgradeOrSale && player.getMoney()>=RateOfFireUpgrade.cost)
			{
				System.out.println("Towers initial frequency of fire = "+ ((TowerMapSquare)(gameController.getMapSquares()[towerSelectedForUpgradeOrSaleX][towerSelectedForUpgradeOrSaleY])).getTower().getRateOfFire());
				gameController.upgradeTower("rateOfFire", towerSelectedForUpgradeOrSaleX, towerSelectedForUpgradeOrSaleY);
				System.out.println("Your TOWER has been rate of Fire upgraded at position ("+towerSelectedForUpgradeOrSaleX+","+towerSelectedForUpgradeOrSaleY+")");
				System.out.println("Towers New rateOfFire = "+ ((TowerMapSquare)(gameController.getMapSquares()[towerSelectedForUpgradeOrSaleX][towerSelectedForUpgradeOrSaleY])).getTower().getRateOfFire());
				player.setMoney(player.getMoney()- RateOfFireUpgrade.cost);
			}
		}

		public void keyL() {
			scene = 3;
		}

		public void key1() {
			if(scene == 3)
			{
				numberOfColumns = storedMaps.get(0).get(0);
				numberOfRows = storedMaps.get(0).get(1);
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
				numberOfColumns = storedMaps.get(1).get(0);
				numberOfRows = storedMaps.get(1).get(1);

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
				numberOfColumns = storedMaps.get(2).get(0);
				numberOfRows = storedMaps.get(2).get(1);
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
				numberOfColumns = storedMaps.get(3).get(0);
				numberOfRows = storedMaps.get(3).get(1);
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
				numberOfColumns = storedMaps.get(4).get(0);
				numberOfRows = storedMaps.get(4).get(1);
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
				numberOfColumns = storedMaps.get(5).get(0);
				numberOfRows = storedMaps.get(5).get(1);
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
				numberOfColumns = storedMaps.get(6).get(0);
				numberOfRows = storedMaps.get(6).get(1);
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
				numberOfColumns = storedMaps.get(7).get(0);
				numberOfRows = storedMaps.get(7).get(1);
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
				numberOfColumns = storedMaps.get(8).get(0);
				numberOfRows = storedMaps.get(8).get(1);
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

		public void keyD() {
			if(scene==4 && towerSelectedForUpgradeOrSale && player.getMoney()>=DamageUpgrade.cost)
			{
				System.out.println("Towers initial damage = "+ ((TowerMapSquare)(gameController.getMapSquares()[towerSelectedForUpgradeOrSaleX][towerSelectedForUpgradeOrSaleY])).getTower().getDamage());
				gameController.upgradeTower("damage", towerSelectedForUpgradeOrSaleX, towerSelectedForUpgradeOrSaleY);
				System.out.println("Your TOWER has been damamge upgraded at position ("+towerSelectedForUpgradeOrSaleX+","+towerSelectedForUpgradeOrSaleY+")");
				System.out.println("Towers New damage = "+ ((TowerMapSquare)(gameController.getMapSquares()[towerSelectedForUpgradeOrSaleX][towerSelectedForUpgradeOrSaleY])).getTower().getDamage());
				player.setMoney(player.getMoney()- DamageUpgrade.cost);
			}
		}

		public void keyR() {
			if(scene==4 && towerSelectedForUpgradeOrSale && player.getMoney()>=RangeUpgrade.cost)
			{
				System.out.println("Towers initial range = "+ ((TowerMapSquare)(gameController.getMapSquares()[towerSelectedForUpgradeOrSaleX][towerSelectedForUpgradeOrSaleY])).getTower().getRange());
				gameController.upgradeTower("range", towerSelectedForUpgradeOrSaleX, towerSelectedForUpgradeOrSaleY);
				System.out.println("Your TOWER has been range upgraded at position ("+towerSelectedForUpgradeOrSaleX+","+towerSelectedForUpgradeOrSaleY+")");
				System.out.println("Towers New range = "+ ((TowerMapSquare)(gameController.getMapSquares()[towerSelectedForUpgradeOrSaleX][towerSelectedForUpgradeOrSaleY])).getTower().getRange());
				player.setMoney(player.getMoney()- RangeUpgrade.cost);
			}
		}
		
		public void keyS() {
			if(scene==4 && towerSelectedForUpgradeOrSale)
			{
				System.out.println("Towers being sold");
				gameController.sellTower(towerSelectedForUpgradeOrSaleX,towerSelectedForUpgradeOrSaleY);
				
			}
		}


		private void convertRoute(ArrayList<Integer> roadRoute)
		{
			if (convertedRoute.isEmpty())
			{
				for (int i = 0; i<roadRoute.size(); i+=2)				// this method fills the new array list with the old array list values, but reduced to integers 1,2,3... 
				{
					convertedRoute.add((((numberOfColumns+4)*roadRoute.get(i))/frameWidth));
					convertedRoute.add((((numberOfRows+4)*roadRoute.get(i+1))/frameHeight));
				}
			}
		}

		private void isValid() {		// this method checks to see if the map entered by the user is valid

			boolean isValid = true;			// this boolean decides at the end shows if a map is valid. the for loops try to make b false, if b is true after all testing then the map is valid
			int greaterX = 0;
			int greaterY = 0;
			for(int i=0; i<convertedRoute.size(); i+=2)
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
					isValid=false;
					i=convertedRoute.size();
				}
			}

			if (isValid)
			{
				validity = "This map is valid";	// this prints on screen if map is valid
				System.out.println("ConvertedRoute Size = "+ convertedRoute.size());
				convertMapSqaures(convertedRoute);							//*************************WRONG PLACE
				scene =4;
				saveMap(convertedRoute,mapNameText);
			} 
			else
			{
				validity = "This map was invalid try again";				// this prints on screen if map is invalid
				scene = 0;
			}
		}



		private void saveMap(ArrayList<Integer> conRoute, String mapName) 
		{ 
			if(storedMaps.size() < 9)
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
				for (int y = 0; y<storedMaps.size();y++)
				{
					outputFile.print(mapNames.get(y)+",");
					if(y==storedMaps.size()-1)
					{
						outputFile.print(numberOfColumns+","+numberOfRows +",");
					}
					for(Integer a : storedMaps.get(y))
					{
						outputFile.print(a+",");
					}
					outputFile.println();
				}
				outputFile.close();
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
		}


		private void convertMapSqaures (ArrayList<Integer> conRoute) 
		{
			gameController.addRoadMapSquares(conRoute, numberOfColumns, numberOfRows);
		}
	}
}