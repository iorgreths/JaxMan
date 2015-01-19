package io.graphics;

public interface AnimationStrategy {
	
	/**
	 * Determines whether or not the next animation step should be delivered.<br>
	 * There is no specific rule how this value should be calculated, though most likely it can
	 * be determined with timing.
	 * 
	 * @author Maxmanski
	 * @return TRUE, if the next animation step is ready to be displayed. FALSE else.
	 */
	public boolean isNextReady();
	
	public boolean equals(Object other);
	
	/**
	 * Creates a new instance of this AnimationStrategy, which has to be a deep copy.
	 * 
	 * @author Maxmanski
	 */
	public AnimationStrategy clone();
}
