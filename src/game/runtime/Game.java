package game.runtime;

import io.graphics.GameFrame;

public abstract class Game implements Observer{

	protected Mode gameMode;
	protected GameFrame frame;
	protected static Game instance;
	
	protected Game(){
		frame = GameFrame.getInstance();
	}
	
	public abstract void gameLoop();
	
	public GameFrame getFrame(){
		return this.frame;
	}
	
	public Mode getMode(){
		return this.gameMode;
	}
}
