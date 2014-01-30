package game.runtime;

public interface Observable {

	/**
	 * 
	 * @param o
	 */
	public void addObserver(Observer o);
	
	/**
	 * 
	 */
	public void notifyObservers();
}
