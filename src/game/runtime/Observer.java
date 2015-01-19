package game.runtime;

/**
 * An interface representing the connection towards objects, which can 'Observer'
 * other objects.
 * 
 * @author Maxmanski, Iorgreths
 *
 */
public interface Observer {

	/**
	 * Notifies this observer about an change in one of it's observed objects.
	 * @param object - The objects which changed.
	 */
	public void update(Observable object);
	
}
