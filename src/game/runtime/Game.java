package game.runtime;

public abstract class Game implements Observer{

	protected Mode gameMode;
	
	public Game(){
		
	}
	
	public abstract void gameLoop();
}
