package io.graphics;

import java.awt.Graphics;

public abstract class Drawable {

	protected Animations<String> animations;
	protected int xPos, yPos;
	
	/**
	 * 
	 * @param g
	 */
	public abstract void draw(Graphics g);
	
	/**
	 * 
	 * @return
	 */
	public int getX(){
		return this.xPos;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getY(){
		return this.yPos;
	}
	
	/**
	 * 
	 * @param dx
	 * @param dy
	 */
	public abstract void move(int dx, int dy);
}
