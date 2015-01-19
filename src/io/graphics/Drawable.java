package io.graphics;

import java.awt.Graphics;

/**
 * 
 * @author Maxmanski
 */
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
	public void draw(Graphics g, double scaleX, double scaleY);
	
	/**
	 * Has to return the Drawable's X coordinate on a 2D plain.
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
