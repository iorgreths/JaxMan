package game.runtime;

/**
 * An interface representing the connections towards 'Observable' objects
 * 
 * @author Maxmanski, Iorgreths
 *
 */
public interface Observable {

	/**
	 * Adds an observer to the list of observers of this object.
	 * 
	 * @param o - The object, which shall observe this object.
	 */
	public void addObserver(Observer o);
	
	/**
	 * Notifies all current observers, that something has changed in this object.
	 */
	public void notifyObservers();
}
