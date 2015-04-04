package domainLayer.player;

public class Player {
	private int money;
	private int lives;
	private int level;
	
	private static Player uniqueInstance;
	
	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public int getLives() {
		return lives;
	}

	public void setLives(int lives) {
		this.lives = lives;
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
	}

	private Player()
	{
		this.money= 500;
		this.lives = 100;
		this.level = 0;
	}
	
	public static Player getUniqueInstance()
	{
		if (uniqueInstance==null)
		{
			uniqueInstance = new Player();
		}

		return uniqueInstance;
		
	}

}
