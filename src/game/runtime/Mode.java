package game.runtime;

import java.awt.Canvas;

public interface Mode extends Observable, Observer{
	
	public Mode execute();
	
	public Canvas getCanvas();
}
