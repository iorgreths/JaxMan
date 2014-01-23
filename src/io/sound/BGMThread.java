package io.sound;

import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;

/**
 * 
 * This class represents the thread, which plays the BackgroundMusic.
 * 
 * @author Iorgreths
 * @version 1.0
 *
 */
class BGMThread extends Thread {

	private BGM music;
	
	/**
	 * Initiates a new object of the type BGMThread, without any music.
	 */
	public BGMThread(){
		
		super();
		music = null;
		
	}
	
	/**
	 * Sets the current background music to music. <br/>
	 * Already playing music ceases playing hereby.
	 * @param music - The new background music.
	 */
	public void setMusic(BGM music){
		
		if( this.music != null){
			
			this.music.stop();
			this.music = music;
			
		}else{
			
			this.music = music;
			
		}
		
	}
	
	/**
	 * Ceases playing of further background music.
	 */
	public void ceasePlaying(){
		
		if(this.music != null){
			
			music.stop();
			
		}
		
	}
	
	/**
	 * If this thread has a music != null,
	 * it initializes the BGM.
	 */
	public void run(){
		
		if(music != null){
			
			try {
				
				music.play();
				
			} catch (LineUnavailableException e) {

				e.printStackTrace();
				
			} catch (IOException e) {
				
				e.printStackTrace();
				
			}
			
		}
		
	}
	
}
