package io.graphics;

import game.options.Options;
import game.runtime.Game;
import io.InputListener;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferStrategy;
import java.util.List;

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
	private static boolean fullscreen;
	private final JFrame frame;
	private final Canvas canvas;
	private BufferStrategy bufferStrategy;
	private static boolean visible;
	private boolean alive;
	private InputListener in;
	private Game game;
	
	/**
	 * Private constructor of the class GameFrame.
	 * 
	 * @author Maxmanski
	 * @param fullscreen
	 */
	private GameFrame(boolean fullscreen){
		GameFrame.fullscreen = fullscreen;
		GameFrame.visible = false;
		frame = new JFrame();
		
		this.dev = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		this.dm = dev.getDisplayMode();
		Options o = Options.getInstance();

		this.width = o.getResolution()[0];
		this.height = o.getResolution()[1];
		this.alive = true;

		frame.setResizable(false);

		this.in = null;
		this.canvas = new Canvas();
		this.canvas.setFocusable(false);
		
		frame.add(canvas);
		
		if(fullscreen && dev.isFullScreenSupported()){
			frame.setUndecorated(true);
			dev.setFullScreenWindow(frame);

		}else{

			// TODO
			canvas.setPreferredSize(new Dimension(this.width, this.height));
			frame.setLocation((this.dm.getWidth()-this.width)/2, (this.dm.getHeight()-this.height)/2);
			frame.setUndecorated(false);
		}

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.requestFocus();
	}
	
	/**
	 * TODO
	 */
	public void setVisible(boolean visible) throws DeadInstanceException{
		if(!this.alive){
			throw new DeadInstanceException("The used GameFrame is dead");
		}
		
		if(!GameFrame.fullscreen && visible){
			this.frame.pack();
		}
		
		this.frame.setVisible(visible);
		this.getBufferStrategy();
		GameFrame.visible=visible;
	}

	/**
	 * Sets the fullscreen mode of the game, depending on the passed boolean.<br>
	 * If the GameFrame is not set to visible, this method will do nothing and return FALSE.
	 * Else, TRUE is returned.
	 * 
	 * @author Maxmanski
	 * @param fullscreen A boolean value to decide whether fullscreen mode should be entered or left: <br>
	 *                   TRUE: Enters fullscreen
	 *                   FALSE: Leaves fullscreen
	 * @return 
	 */
	public boolean setFullscreen(boolean fullscreen) throws DeadInstanceException{
		if(!this.alive){
			throw new DeadInstanceException("The used GameFrame is dead");
		}
		
		InputListener oldListener = GameFrame.instance.in;
		Game oldGame = GameFrame.instance.game;
		
		boolean success = false;
		
		if(GameFrame.visible){
			if(GameFrame.instance!=null && GameFrame.instance.hasFullscreen()!=fullscreen){

				GameFrame.instance.dispose();
				
			}
			
			if(GameFrame.instance==null){

				GameFrame.instance = new GameFrame(fullscreen);
				GameFrame.instance.setListener(oldListener);
				GameFrame.instance.setGame(oldGame);
				GameFrame.instance.setVisible(true);
				GameFrame.instance.getBufferStrategy();
			}

			GameFrame.fullscreen = fullscreen;
			GameFrame.instance.requestFocus();
			
			success=true;
		}
		
		return success;
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
			try {
				GameFrame.instance.requestFocus();
			} catch (DeadInstanceException e) {
				e.printStackTrace();
			}
		}
		
		return GameFrame.instance;
	}

	/**
	 * Makes the GameFrame invisible and disposes of it.
	 * 
	 * TODO
	 * @author Maxmanski
	 */
	public synchronized void dispose() throws DeadInstanceException{
		if(!this.alive){
			throw new DeadInstanceException("The used GameFrame is dead");
		}
		
		instance.frame.setVisible(false);
		instance.bufferStrategy.dispose();
		instance.frame.dispose();
		instance.bufferStrategy=null;
		instance.alive = false;
		GameFrame.instance=null;
		GameFrame.fullscreen = false;
		GameFrame.visible = false;
	}

	/**
	 * Checks if the GameFrame is in fullscreen mode.
	 * 
	 * @author Maxmanski
	 * @return A boolean value, depending on whether the GameFrame is the currently active fullscreen application
	 */
	public boolean hasFullscreen() throws DeadInstanceException{
		if(!this.alive){
			throw new DeadInstanceException("The used GameFrame is dead");
		}
		
		return dev.getFullScreenWindow()==instance.frame || GameFrame.fullscreen;
	}

	/**
	 * Requests the focus for the GameFrame's window.
	 * 
	 * @author Maxmanski
	 */
	public void requestFocus() throws DeadInstanceException{
		if(!this.alive){
			throw new DeadInstanceException("The used GameFrame is dead");
		}
		
		if(instance!=null && instance.frame!=null && instance.frame.isVisible()){
			instance.frame.setAlwaysOnTop(true);
			instance.frame.setAlwaysOnTop(false);
			instance.frame.requestFocus();
		}
	}
	
	/**
	 * TODO
	 * @return
	 */
	public BufferStrategy getBufferStrategy() throws DeadInstanceException{
		if(!this.alive){
			throw new DeadInstanceException("The used GameFrame is dead");
		}
		
		if(this.bufferStrategy==null && GameFrame.visible){
			this.canvas.createBufferStrategy(2);
			this.bufferStrategy = canvas.getBufferStrategy();
		}else if(!GameFrame.visible){
			this.bufferStrategy=null;
		}
		
		return this.bufferStrategy;
	}
	
	/**
	 * 
	 * @param drawList
	 * @throws DeadInstanceException
	 */
	public synchronized void draw(List<Drawable> drawList) throws DeadInstanceException{
		this.draw(drawList, 1f);
	}
	
	/**
	 * 
	 * @param drawList
	 * @param scale
	 * @throws DeadInstanceException
	 */
	public synchronized void draw(List<Drawable> drawList, float scale) throws DeadInstanceException{
		BufferStrategy bs = this.getBufferStrategy();
		Graphics2D g = (Graphics2D) bs.getDrawGraphics();
		
		g.clearRect(0, 0, this.getCanvasDimension().width, this.getCanvasDimension().height);
		for(Drawable d: drawList){
			d.draw(g, scale);
		}
		g.dispose();
		bs.show();
	}
	
	/**
	 * TODO
	 * @return
	 */
	public Dimension getCanvasDimension() throws DeadInstanceException{
		if(!this.alive){
			throw new DeadInstanceException("The used GameFrame is dead");
		}
		
		Dimension dim = null;
		if(this.canvas!=null && GameFrame.visible){
			dim = this.canvas.getSize();
		}
		return dim;
	}
	
	/**
	 * TODO
	 * @return
	 */
	public Dimension getDimension() throws DeadInstanceException{
		if(!this.alive){
			throw new DeadInstanceException("The used GameFrame is dead");
		}
		
		Dimension dim = null;
		if(GameFrame.visible){
			dim = this.frame.getSize();
		}
		return dim;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isAlive(){
		return this.alive;
	}
	
	public void setGame(Game g){
		this.game = g;
		if(g!=null){
			g.setInputListener(this.in);
		}
	}
	
	/**
	 * 
	 * @param i
	 */
	public void setInputListener(InputListener i){
		this.setListener(i);
		this.in = i;
	}
	
	/**
	 * 
	 * @param i
	 */
	private void setListener(InputListener i){
		this.frame.addKeyListener(i);
		this.frame.addMouseListener(i);
		this.frame.addMouseMotionListener(i);
		this.canvas.addKeyListener(i);
		this.canvas.addMouseListener(i);
		this.canvas.addMouseMotionListener(i);
		this.frame.getContentPane().addMouseListener(i);
		this.frame.getContentPane().addKeyListener(i);
		this.frame.getContentPane().addMouseMotionListener(i);
	}
}
