package io.graphics;

import java.awt.Graphics;

public interface Drawable {

	/**
	 * 
	 * @param g
	 */
	public void draw(Graphics g);

	/**
	 * 
	 * @param g
	 * @param scale
	 */
	public void draw(Graphics g, float scale);
	
	/**
	 * 
	 * @return
	 */
	public int getX();
	
	/**
	 * 
	 * @return
	 */
	public int getY();
	
	/**
	 * 
	 * @param dx
	 * @param dy
	 */
	public void move(int dx, int dy);
}
