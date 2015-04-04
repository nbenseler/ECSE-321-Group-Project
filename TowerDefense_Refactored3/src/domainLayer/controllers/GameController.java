package domainLayer.controllers;

import java.util.ArrayList;

import domainLayer.controllers.GameController;
import domainLayer.critter.AerialCritter;
import domainLayer.critter.Critter;
import domainLayer.critter.GroundCritter;
import domainLayer.mapSquares.IMapSquareObserver;
import domainLayer.mapSquares.MapSquare;
import domainLayer.mapSquares.RoadMapSquare;
import domainLayer.mapSquares.TowerMapSquare;
import domainLayer.player.Player;
import domainLayer.towers.strategys.*;

public class GameController {

	private int numberOfCrittersInThisWave;
	private long timeOfLastCritter;
	private long timeOfGameStart;
	private int xSizeOfSquares;
	private int ySizeOfSquares;
	private Player player;
	private MapSquare[][] mapSquares;
	private ArrayList<RoadMapSquare> roadMapSquareList;
	private ArrayList<TowerMapSquare> towerMapSquareList;
	private ArrayList<Critter> critterList;

	private static GameController uniqueInstance;

	public static GameController getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new GameController();
		}
		return uniqueInstance;
	}

	private GameController() {
		critterList = new ArrayList<>();
		roadMapSquareList = new ArrayList<>();
		towerMapSquareList = new ArrayList<>();
		timeOfGameStart = System.currentTimeMillis();
		timeOfLastCritter = 0;
		player = Player.getUniqueInstance();
	}

	public void addTower(String type, int xPosition, int yPosition) {
		assert xPosition < mapSquares.length && yPosition < mapSquares[xPosition].length;

		if (mapSquares[xPosition][yPosition] == null) {
			mapSquares[xPosition][yPosition] = new TowerMapSquare(type);
			mapSquares[xPosition][yPosition].setxPosition(xPosition); // Possible to pass into the constructor ?
			mapSquares[xPosition][yPosition].setyPosition(yPosition);
			this.towerMapSquareList.add((TowerMapSquare) mapSquares[xPosition][yPosition]);
			this.addIMapSquareObservers(xPosition, yPosition);
		}
	}

	public void upgradeTower(String type, int xPosition, int yPosition) {
		assert mapSquares[xPosition][yPosition] != null && mapSquares[xPosition][yPosition].getClass() == TowerMapSquare.class;

		// REMIND NICK THAT THIS URGENTLY NEEDS CHANGING. REMOVE THIS COMMENT ONCE DONE.
		((TowerMapSquare) mapSquares[xPosition][yPosition]).upgradeTower(type);
		if (type.toLowerCase().equals("range"))
			this.addIMapSquareObservers(xPosition, yPosition);
	}

	private void addIMapSquareObservers(int xPosition, int yPosition) {
		assert (mapSquares[xPosition][yPosition] != null) && (mapSquares[xPosition][yPosition].getClass() == TowerMapSquare.class);

		double range = ((TowerMapSquare) mapSquares[xPosition][yPosition]).getTower().getRange();

		for (int i = (int) Math.max(xPosition - range, 0); i <= Math.min(xPosition + range, mapSquares.length - 1); i++) {
			for (int j = (int) Math.max(yPosition - range, 0); j <= Math.min(yPosition + range, mapSquares.length - 1); j++){
				if (mapSquares[i][j] != null && mapSquares[i][j].getClass() == RoadMapSquare.class) {
					((RoadMapSquare) mapSquares[i][j]).addIMapSquareObserver((IMapSquareObserver) mapSquares[xPosition][yPosition]);
				}
			}
		}
	}
	
	private void removeIMapSquareObservers(int xPosition, int yPosition) {
		assert (mapSquares[xPosition][yPosition] != null) && (mapSquares[xPosition][yPosition].getClass() == TowerMapSquare.class);

		double range = ((TowerMapSquare) mapSquares[xPosition][yPosition]).getTower().getRange();

		for (int i = (int) Math.max(xPosition - range, 0); i <= Math.min(xPosition + range, mapSquares.length - 1); i++) {
			for (int j = (int) Math.max(yPosition - range, 0); j <= Math.min(yPosition + range, mapSquares.length - 1); j++){
				if (mapSquares[i][j] != null && mapSquares[i][j].getClass() == RoadMapSquare.class) {
					((RoadMapSquare) mapSquares[i][j]).removeIMapSquareObserver((IMapSquareObserver) mapSquares[xPosition][yPosition]);
				}
			}
		}
	}

	public void addRoadMapSquares(ArrayList<Integer> conRoute, int numberOfColumns, int numberOfRows) {
		assert conRoute.size() >= 6;
		
		mapSquares = new MapSquare[numberOfColumns][numberOfRows];

		for (int i = 0; i< conRoute.size(); i += 2)
		{
			mapSquares[conRoute.get(i)-1][conRoute.get(i+1)-1] = new RoadMapSquare();
			roadMapSquareList.add((RoadMapSquare) (mapSquares[conRoute.get(i)-1][conRoute.get(i+1)-1]));
			mapSquares[conRoute.get(i)-1][conRoute.get(i+1)-1].setxPosition(conRoute.get(i)-1);
			mapSquares[conRoute.get(i)-1][conRoute.get(i+1)-1].setyPosition(conRoute.get(i+1)-1);
		}

		for (int i = 0; i < conRoute.size(); i+=2)
		{
			if (i == 0)
			{
				((RoadMapSquare) mapSquares[conRoute.get(i)-1][conRoute.get(i+1)-1]).setNextRoadMapSquare((RoadMapSquare) mapSquares[conRoute.get(i+4)-1][conRoute.get(i+5)-1]);
				((RoadMapSquare) mapSquares[conRoute.get(i)-1][conRoute.get(i+1)-1]).setPositionInRoad(i/2);
			}
			else if (i == conRoute.size() - 2)
			{
				((RoadMapSquare) mapSquares[conRoute.get(i)-1][conRoute.get(i+1)-1]).setNextRoadMapSquare((RoadMapSquare) mapSquares[conRoute.get(2)-1][conRoute.get(3)-1]);
				((RoadMapSquare) mapSquares[conRoute.get(i)-1][conRoute.get(i+1)-1]).setPositionInRoad((i/2) - 1);
			}
			else if (i == 2)
			{
				((RoadMapSquare) mapSquares[conRoute.get(i)-1][conRoute.get(i+1)-1]).setPositionInRoad((conRoute.size()/2) - 1);
			}
			else
			{
				((RoadMapSquare) mapSquares[conRoute.get(i)-1][conRoute.get(i+1)-1]).setNextRoadMapSquare((RoadMapSquare)mapSquares[conRoute.get(i+2)-1][conRoute.get(i+3)-1]);
				((RoadMapSquare) mapSquares[conRoute.get(i)-1][conRoute.get(i+1)-1]).setPositionInRoad((i/2) - 1);
			}
		}
	}

	public int getFirstX(ArrayList<Integer> conRoute) {
		return conRoute.get(0)-1;
	}

	public int getFirstY(ArrayList<Integer> conRoute) {
		return conRoute.get(1)-1;
	}

	public ArrayList<Critter> getCritterList() {
		return critterList;
	}

	public MapSquare[][] getMapSquares() {
		return mapSquares;
	}

	public ArrayList<TowerMapSquare> getTowerMapSquareList() {
		return towerMapSquareList;
	}

	public void startWave() {
		int waveMultiplier = (Player.getUniqueInstance().getLevel()*2);

		for (Critter critter: critterList)
		{
			critter.setSteps(critter.getSteps()+1);
			critter.checkDirection();
			if(critter.isMoveRight())
			{
				critter.setxPostion(critter.getxPosition()+critter.getSpeed());
			}
			else if(critter.isMoveLeft())
			{
				critter.setxPostion(critter.getxPosition()-critter.getSpeed());
			}
			else if(critter.isMoveUp())
			{
				critter.setyPosition(critter.getyPosition()+critter.getSpeed());
			}
			else if (critter.isMoveDown())
			{
				critter.setyPosition(critter.getyPosition()-critter.getSpeed());
			}
		}

		if (this.numberOfCrittersInThisWave == 0)
		{
			if (System.currentTimeMillis() - timeOfGameStart > 10000)
			{
				timeOfLastCritter = System.currentTimeMillis();
				RoadMapSquare start = roadMapSquareList.get(0);
				GroundCritter newGroundCritter = new GroundCritter(start);
				critterList.add(newGroundCritter);
				numberOfCrittersInThisWave += 1;
			}
		}
		else if (numberOfCrittersInThisWave == 10*waveMultiplier)
		{
			if (critterList.isEmpty())
			{
				Player.getUniqueInstance().setLevel(Player.getUniqueInstance().getLevel()+1);
				timeOfGameStart = System.currentTimeMillis();
				numberOfCrittersInThisWave = 0;
			}
		}
		else if (System.currentTimeMillis() - timeOfLastCritter > 1000 / waveMultiplier)
		{
			
			if (numberOfCrittersInThisWave % 2 == 0)
			{
				timeOfLastCritter = System.currentTimeMillis();
				RoadMapSquare start = roadMapSquareList.get(0);
				AerialCritter newAerialCritter = new AerialCritter(start);
				critterList.add(newAerialCritter);
				numberOfCrittersInThisWave++;
			}
			
			else
			{
				timeOfLastCritter = System.currentTimeMillis();
				RoadMapSquare start = roadMapSquareList.get(0);
				GroundCritter newGroundCritter = new GroundCritter(start);
				critterList.add(newGroundCritter);
				numberOfCrittersInThisWave++;
			}

		}
		for (TowerMapSquare a: towerMapSquareList)
		{
			a.attackCritters();
		}
	}

	public void removeCritterFromScreen(Critter critter)
	{
		critterList.remove(critter);
		((RoadMapSquare) mapSquares[(int) critter.getSquare().getxPosition()][critter.getSquare().getyPosition()]).removeCritter(critter);
	}

	public void sellTower(int towerXPosition,int towerYPosition) {
		
		towerMapSquareList.remove(mapSquares[towerXPosition][towerYPosition]);
		this.removeIMapSquareObservers(towerXPosition, towerYPosition);
		player.setMoney((int) 0.5*(player.getMoney() + (((TowerMapSquare) mapSquares[towerXPosition][towerYPosition]).getTower().getValue())));
		mapSquares[towerXPosition][towerYPosition] = null;
	}
	
	public void setXandYSizeOfSquares(int xSizeOfSquares, int ySizeOfSquares) {
		this.xSizeOfSquares = xSizeOfSquares;
		this.ySizeOfSquares = ySizeOfSquares;
	}
	
	public void setAttackStrategy(IAttackStrategy strategy) {
		for (TowerMapSquare towerMapSquare : towerMapSquareList) {
			towerMapSquare.setAttackStrategy(strategy);
		}
	}
}
