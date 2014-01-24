package io.sound;

import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Represents the thread which plays SoundEffects.
 * 
 * @author Iorgreths
 * @version 1.1
 *
 */
class SoundEffectsThread implements Runnable {

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
	@Override
	public void run(){
		
		if(sound != null){
			
			try {
				
				sound.play();
				
			} catch (LineUnavailableException e) {

				e.printStackTrace();
				
			} catch (IOException e) {
				
				e.printStackTrace();
				
			} catch (UnsupportedAudioFileException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	
}
