package io.graphics;

import game.options.Options;

import java.awt.Canvas;
import java.awt.Component;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;

public class GameFrame extends JFrame{
	private static final long serialVersionUID = -7489048496141498327L;
	private final GraphicsDevice dev;
	private final DisplayMode dm;
	private final int width, height;
	private static GameFrame instance;
	private Canvas canvas;

	/**
	 * 
	 * @param fullscreen
	 */
	private GameFrame(boolean fullscreen, Canvas canvas){
		this.dev = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		this.dm = dev.getDisplayMode();
		Options o = Options.getInstance();

		// TODO
		this.width = 640;
		this.height = 480;

		this.setResizable(false);

		if(fullscreen && dev.isFullScreenSupported()){
			this.setUndecorated(true);
			dev.setFullScreenWindow(this);

		}else{

			// TODO
			this.setBounds((this.dm.getWidth()-this.width)/2, (this.dm.getHeight()-this.height)/2, this.width, this.height);
			this.setUndecorated(false);
		}

		if(canvas!=null){
			canvas.setFocusable(false);
			this.getContentPane().add(canvas);
		}
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.requestFocus();
		GameFrame.instance = this;
	}
	
	/**
	 * 
	 * @param fullscreen
	 */
	private GameFrame(boolean fullscreen){
		this(fullscreen, null);
	}

	/**
	 * 
	 * @param fullscreen
	 * @return
	 */
	public GameFrame setFullscreen(boolean fullscreen){
		if(GameFrame.instance!=null){
			instance.setVisible(false);
			instance.dispose();
		}

		GameFrame.instance = new GameFrame(fullscreen);
		GameFrame.instance.requestFocus();
		return GameFrame.instance;
	}

	/**
	 * 
	 * @return
	 */
	public static GameFrame getInstance(){

		if(GameFrame.instance==null){
			GameFrame.instance = new GameFrame(false);
		}
		return GameFrame.instance;
	}

	/**
	 * 
	 */
	public void dispose(){
		super.dispose();
		GameFrame.instance=null;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isFullscreen(){
		return dev.getFullScreenWindow()!=null;
	}

	/**
	 * 
	 */
	public void setCanvas(Canvas canvas){
		if(this.canvas!=null){
			boolean contains = false;
			
			for(Component c: this.getContentPane().getComponents()){
				if(c.equals(this.canvas)){
					contains = true;
					break;
				}
			}
			
			if(contains){
				this.getContentPane().remove(this.canvas);
			}
		}
		
		canvas.setFocusable(false);
		this.getContentPane().add(canvas);
		this.canvas = canvas;
		this.requestFocus();
	}

}
