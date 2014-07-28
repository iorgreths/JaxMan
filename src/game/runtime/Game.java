package game.runtime;

import io.InputListener;
import io.graphics.GameFrame;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public abstract class Game implements Observer{

	protected Mode gameMode;
	protected static Game instance;
	protected InputListener inListener;
	
	protected Game(InputListener in){
		GameFrame.getInstance().setInputListener(in);
		this.inListener = in;
		this.inListener.setGame(this);
	}
	
	public abstract void gameLoop();
	
	public GameFrame getFrame(){
		return GameFrame.getInstance();
	}
	
	public Mode getMode(){
		return this.gameMode;
	}
	
	public void setInputListener(InputListener in){
		this.inListener = in;
	}
	
	public abstract void mouseDragged(MouseEvent e);
	
	public abstract void mouseClicked(MouseEvent e);
	
	public abstract void mousePressed(MouseEvent e);
	
	public abstract void mouseReleased(MouseEvent e);
	
	public abstract void mouseMoved(MouseEvent e);
	
	public abstract void keyPressed(KeyEvent e);
	
	public abstract void keyReleased(KeyEvent e);
	
	public abstract void keyTyped(KeyEvent e);
}
