package io.graphics;

public interface AnimationStrategy {
	
	/**
	 * 
	 * @author Max
	 * @return
	 */
	public boolean isNextReady();
	
	/**
	 * 
	 * @param other
	 * @return
	 */
	public boolean equals(Object other);
	
	/**
	 * 
	 */
	public AnimationStrategy clone();
}
