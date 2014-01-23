package io.sound;

import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;

/**
 * Represents the thread which plays SoundEffects.
 * 
 * @author Iorgreths
 * @version 1.0
 *
 */
class SoundEffectsThread extends Thread {

	private SoundClip sound; // The sound to be played
	
	/**
	 * Creates a new Object from type SoundEffectsThread, with no SoundClip.
	 */
	public SoundEffectsThread(){
		
		super();
		sound = null;
		
	}
	
	/**
	 * Sets the "to played SoundClip" to sound.
	 * @param sound - The SoundEffect, which shall be played
	 */
	public void setSoundEffect(SoundClip sound){
		
		this.sound = sound;
		
	}
	
	/**
	 * If this thread has a SoundEffect != null, <br/>
	 * The SoundEffect will be played (once).
	 */
	public void run(){
		
		if(sound != null){
			
			try {
				
				sound.play();
				
			} catch (LineUnavailableException e) {

				e.printStackTrace();
				
			} catch (IOException e) {
				
				e.printStackTrace();
				
			}
			
		}
		
	}
	
}
