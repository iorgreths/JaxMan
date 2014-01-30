package io.graphics;

import game.options.Options;

import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;

public class GameFrame extends JFrame{
	private final GraphicsDevice dev;
	private final DisplayMode dm;
	private final int width, height;
	private static GameFrame instance;

	/**
	 * 
	 * @param fullscreen
	 */
	private GameFrame(boolean fullscreen){
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

		this.setVisible(true);
		this.requestFocus();
		GameFrame.instance = this;
	}

	/**
	 * 
	 * @param fullscreen
	 * @return
	 */
	public static GameFrame setFullscreen(boolean fullscreen){
		if(GameFrame.instance!=null){
			instance.setVisible(false);
			instance.dispose();
		}

		GameFrame.instance = new GameFrame(fullscreen);
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
		this.instance=null;
	}

	public static void main(String...strings ) throws InterruptedException{
		Thread.sleep(1000);
		GameFrame.setFullscreen(true);
		Thread.sleep(1000);
		GameFrame.setFullscreen(false);
	}

}
