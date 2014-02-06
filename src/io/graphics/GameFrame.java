package io.graphics;

import game.options.Options;

import java.awt.Canvas;
import java.awt.Component;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;

/**
 * GameFrame represents the main window of the game.<br>
 * As there usually isn't the need for more than one main window for a game,
 * this class was constructed as a Singleton.<br>
 * 
 * The single existing instance will be created upon the first fetching attempt.<br>
 * If not specified otherwise in the Options, it will be instantiated in windowed mode.
 * 
 * @author Maxmanski
 */
public class GameFrame{
	private final GraphicsDevice dev;
	private final DisplayMode dm;
	private final int width, height;
	private static GameFrame instance;
	private Canvas canvas;
	private final JFrame frame;

	/**
	 * Private constructor of the class GameFrame.
	 * 
	 * @author Maxmanski
	 * @param fullscreen
	 */
	private GameFrame(boolean fullscreen, Canvas canvas){
		frame = new JFrame();
		this.dev = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		this.dm = dev.getDisplayMode();
		Options o = Options.getInstance();

		this.width = o.getResolution()[0];
		this.height = o.getResolution()[1];

		frame.setResizable(false);

		if(fullscreen && dev.isFullScreenSupported()){
			frame.setUndecorated(true);
			dev.setFullScreenWindow(frame);

		}else{

			// TODO
			frame.setBounds((this.dm.getWidth()-this.width)/2, (this.dm.getHeight()-this.height)/2, this.width, this.height);
			frame.setUndecorated(false);
		}

		if(canvas!=null){
			canvas.setFocusable(false);
			frame.getContentPane().add(canvas);
		}

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.requestFocus();
	}

	/**
	 * Private Constructor of the class GameFrame
	 * 
	 * @author Maxmanski
	 * @param fullscreen
	 */
	private GameFrame(boolean fullscreen){
		this(fullscreen, null);
	}

	/**
	 * Sets the fullscreen mode of the game, depending on the passed boolean.<br>
	 * Also returns a pointer to the current instance.
	 * 
	 * @author Maxmanski
	 * @param fullscreen A boolean value to decide whether fullscreen mode should be entered or left: <br>
	 *                   TRUE: Enters fullscreen
	 *                   FALSE: Leaves fullscreen
	 * @return Current instance of the GameFrame
	 */
	public GameFrame setFullscreen(boolean fullscreen){
		if(GameFrame.instance!=null && GameFrame.instance.hasFullscreen()!=fullscreen){

			GameFrame.instance.dispose();
			
		}
		
		if(GameFrame.instance==null){

			GameFrame.instance = new GameFrame(fullscreen, this.canvas);
		}
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		GameFrame.instance.requestFocus();
		return GameFrame.instance;
	}

	/**
	 * Returns the current instance of the GameFrame or instantiates a new one, if none is present yet.
	 * 
	 * @author Maxmanski
	 * @return Current instance of the GameFrame
	 */
	public static GameFrame getInstance(){

		if(GameFrame.instance==null){
			GameFrame.instance = new GameFrame(false);
			GameFrame.instance.requestFocus();
		}
		
		return GameFrame.instance;
	}

	/**
	 * Makes the GameFrame invisible and disposes of it.
	 * 
	 * @author Maxmanski
	 */
	public synchronized void dispose(){
		instance.frame.setVisible(false);
		instance.frame.dispose();
		GameFrame.instance=null;
	}

	/**
	 * Checks if the GameFrame is in fullscreen mode.
	 * 
	 * @author Maxmanski
	 * @return A boolean value, depending on whether the GameFrame is the currently active fullscreen application
	 */
	public boolean hasFullscreen(){
		return dev.getFullScreenWindow()==instance.frame;
	}

	/**
	 * Sets the currently active Canvas of the GameFrame.<br>
	 * If there is already another Canvas on the GameFrame, it is being replaced.<br>
	 * If null is passed as an argumant, nothing happens.
	 * 
	 * @author Maxmanski
	 */
	public void setCanvas(Canvas canvas){
		if(canvas!=null){
			canvas.setFocusable(false);
			if(instance.canvas!=null && instance.frame!=null){
				boolean contains = false;

				for(Component c: instance.frame.getContentPane().getComponents()){
					if(c.equals(this.canvas)){
						contains = true;
						break;
					}
				}

				if(contains){
					instance.frame.getContentPane().remove(this.canvas);
				}
			}

			instance.frame.getContentPane().add(canvas);
			instance.canvas = canvas;
		}
	}
	
	/**
	 * Requests the focus for the GameFrame's window.
	 * 
	 * @author Maxmanski
	 */
	public void requestFocus(){
		if(instance!=null && instance.frame!=null && instance.frame.isVisible()){
			instance.frame.setAlwaysOnTop(true);
			instance.frame.setAlwaysOnTop(false);
			instance.frame.requestFocus();
		}
	}

}
