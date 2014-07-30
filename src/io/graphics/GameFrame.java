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
import java.awt.Point;
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
	private static boolean visible = false;
	private boolean alive;
	private InputListener in;
	private Game game;
	private double scaleX, scaleY;
	private Dimension resolution;
	
	/**
	 * Private constructor of the class GameFrame.
	 * 
	 * @author Maxmanski
	 * @param fullscreen
	 */
	private GameFrame(boolean fullscreen){
		synchronized(this){
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


			if(GameFrame.instance != null && GameFrame.instance.resolution != null){

				this.resolution = GameFrame.instance.resolution;
			}else{

				this.resolution = new Dimension(640,480);
			}

			this.canvas.setPreferredSize(new Dimension(640,480));

			frame.add(canvas);

			if(fullscreen && dev.isFullScreenSupported()){
				frame.setUndecorated(true);
				dev.setFullScreenWindow(frame);

			}else{

				// TODO
//				canvas.setPreferredSize(new Dimension(this.width, this.height));
				frame.setLocation((this.dm.getWidth()-this.width)/2, (this.dm.getHeight()-this.height)/2);
				frame.setUndecorated(false);
			}

			this.scaleX = canvas.getWidth() / this.resolution.getWidth();
			this.scaleY = canvas.getHeight() / this.resolution.getHeight();
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.requestFocus();
		}
	}
	
	/**
	 * TODO
	 */
	public synchronized void setVisible(boolean visible) throws DeadInstanceException{
		if(!this.alive){
			throw new DeadInstanceException("The used GameFrame is dead");
		}
		
		if(!GameFrame.fullscreen && visible){
			this.frame.pack();
		}
		
		this.frame.setVisible(visible);
		this.getBufferStrategy();
		GameFrame.visible=visible;
		this.scaleX = canvas.getWidth() / this.resolution.getWidth();
		this.scaleY = canvas.getHeight() / this.resolution.getHeight();
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
	public synchronized boolean setFullscreen(boolean fullscreen) throws DeadInstanceException{
		if(!this.alive){
			throw new DeadInstanceException("The used GameFrame is dead");
		}
		
		InputListener oldListener=null;
		Game oldGame=null;
		Dimension oldDimension=null;
		boolean oldVis = GameFrame.visible, success = false;
		
		if(GameFrame.instance != null){

			oldListener = GameFrame.instance.in;
			oldGame = GameFrame.instance.game;
			oldDimension = GameFrame.instance.resolution;
			
			if(GameFrame.instance.hasFullscreen()!=fullscreen){
				GameFrame.instance.dispose();
			}
			
		}
		
		// if there is no old instance or it has been disposed of...
		if(GameFrame.instance == null){

			GameFrame.instance = new GameFrame(fullscreen);
			GameFrame.instance.setVisible(oldVis);
			GameFrame.instance.getBufferStrategy();
		}
		
		
		if(!GameFrame.fullscreen){
			
			GameFrame.instance.setVisible(false);
			GameFrame.visible = false;
			GameFrame.instance.canvas.setPreferredSize(oldDimension);
			GameFrame.instance.frame.pack();
			GameFrame.instance.setVisible(oldVis);
			GameFrame.visible = oldVis;
		}
		
		GameFrame.instance.setInputListener(oldListener);
		GameFrame.instance.setGame(oldGame);
		
		if(GameFrame.visible){
			
			GameFrame.instance.resolution = oldDimension;
			GameFrame.instance.scaleX = GameFrame.instance.canvas.getWidth() / oldDimension.getWidth();
			GameFrame.instance.scaleY = GameFrame.instance.canvas.getHeight() / oldDimension.getHeight();

			GameFrame.fullscreen = fullscreen;
			GameFrame.instance.requestFocus();
			
			success=true;
		}
		
		return success;
	}

	/**
	 * 
	 * @param d
	 * @throws DeadInstanceException 
	 */
	public synchronized void setResolution(Dimension d) throws DeadInstanceException{
		if(!this.alive){
			throw new DeadInstanceException();
		}
		
		if(d!=null){
			boolean oldVis = GameFrame.visible;
			
			this.setVisible(false);
			GameFrame.visible = false;
			
			if(!GameFrame.fullscreen){

				this.canvas.setPreferredSize(d);
				this.frame.pack();
			}
			
			this.setVisible(oldVis);
			GameFrame.visible = oldVis;

			this.resolution = d;
			this.scaleX = this.canvas.getWidth() / this.resolution.getWidth();
			this.scaleY = this.canvas.getHeight() / this.resolution.getHeight();
			this.getBufferStrategy();
		}
	}
	
	/**
	 * 
	 * @return
	 * @throws DeadInstanceException
	 */
	public synchronized Dimension getResolution() throws DeadInstanceException{
		if(!this.alive){
			throw new DeadInstanceException();
		}
		
		return this.resolution;
	}
	
	/**
	 * Returns the current instance of the GameFrame or instantiates a new one, if none is present yet.
	 * 
	 * @author Maxmanski
	 * @return Current instance of the GameFrame
	 */
	public synchronized static GameFrame getInstance(){

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
	public synchronized boolean hasFullscreen() throws DeadInstanceException{
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
	public synchronized void requestFocus() throws DeadInstanceException{
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
	public synchronized BufferStrategy getBufferStrategy() throws DeadInstanceException{
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
		this.draw(drawList, scaleX, scaleY);
	}
	
	/**
	 * 
	 * @param drawList
	 * @param scale
	 * @throws DeadInstanceException
	 */
	public synchronized void draw(List<Drawable> drawList, double scaleX, double scaleY) throws DeadInstanceException{
		BufferStrategy bs = this.getBufferStrategy();
		Graphics2D g = (Graphics2D) bs.getDrawGraphics();
		
		g.clearRect(0, 0, this.getCanvasDimension().width, this.getCanvasDimension().height);
		for(Drawable d: drawList){
			d.draw(g, scaleX, scaleY);
		}
		g.dispose();
		bs.show();
	}
	
	/**
	 * TODO
	 * @return
	 */
	public synchronized Dimension getCanvasDimension() throws DeadInstanceException{
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
	public synchronized Dimension getDimension() throws DeadInstanceException{
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
	public synchronized boolean isAlive(){
		return this.alive;
	}
	
	public synchronized void setGame(Game g){
		this.game = g;
		if(g!=null){
			g.setInputListener(this.in);
		}
	}
	
	/**
	 * 
	 * @param i
	 */
	public synchronized void setInputListener(InputListener i){
		this.setListener(i);
		this.in = i;
	}
	
	/**
	 * 
	 * @param i
	 */
	private synchronized void setListener(InputListener i){
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
	
	/**
	 * 
	 * @param location
	 * @return
	 */
	public synchronized static Point toCanvasCoordinates(Point location){
		if(location == null || !GameFrame.visible){
			return null;
		}
		
		Point p=null;
		
		if(GameFrame.getInstance().frame.isVisible()){
			
			int x = location.x, y = location.y;
			double sX = GameFrame.getInstance().scaleX, sY = GameFrame.getInstance().scaleY;
			
			Point cP = GameFrame.getInstance().canvas.getLocationOnScreen();
			int cX = cP.x, cY = cP.y;
			
			p = new Point((int)((x-cX)*sX), (int)((y-cY)*sY));
		}
		
		return p;
	}
	
	/**
	 * 
	 * @return
	 */
	public synchronized static boolean isVisible(){
		return GameFrame.getInstance().frame.isVisible();
	}
}
