package domainLayer.player;

import java.util.ArrayList;

public class Player {
	private int money;
	private int lives;
	private int level;
	private ArrayList<IPlayerObserver> playerObservers;
	
	private static Player uniqueInstance;
	
	public int getMoney() {
		return money;
	}
	
	public void setPlayerObserver(IPlayerObserver playerObserver) {
		this.playerObservers.add(playerObserver);
		this.notifyObservers();
	}

	public void setMoney(int money) {
		this.money = money;
		this.notifyObservers();
	}

	public int getLives() {
		return lives;
	}

	public void setLives(int lives) {
		this.lives = lives;
		this.notifyObservers();
		if (this.lives<=0)
		{
			System.out.println("GAME OVER!! Come on Professor Sinning, you can do better than that!!");
			System.exit(0);
		}
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
		this.notifyObservers();
	}

	private Player()
	{
		this.money= 500;
		this.lives = 20;
		this.level = 1;
		this.playerObservers = new ArrayList<>();
	}
	
	public static Player getUniqueInstance()
	{
		if (uniqueInstance==null)
		{
			uniqueInstance = new Player();
		}

		return uniqueInstance;
	}
	
	public void notifyObservers() {
		for (IPlayerObserver playObserver: playerObservers) {
			playObserver.updateLivesMoneyLevel();
		}
	}

}
