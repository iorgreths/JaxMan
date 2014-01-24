package io.sound;

import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * 
 * This class represents the thread, which plays the BackgroundMusic.
 * 
 * @author Iorgreths
 * @version 1.1
 *
 */
class BGMThread implements Runnable {

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
	 * Sets the volume for the line (in dB). <br/>
	 * <br/>
	 * The volume on the line is between: <br/>
	 * -80dB - 6.0206dB <br/>
	 * If the parameter is out of this range it will be adjusted to minimum  or maximum, whichever is closer.
	 * @param volume - The volume on the line in dB ( between -80 and 6.0206 )
	 */
	public void setVolume(float volume){
		
		music.setVolume(volume);
		
	}

	/**
	 * If this thread has a music != null,
	 * it initializes the BGM.
	 */
	@Override
	public void run() {
		
		if(music != null){
			
			try {
				
				music.play();
				
			} catch (LineUnavailableException e) {

				e.printStackTrace();
				
			} catch (IOException e) {
				
				e.printStackTrace();
				
			} catch (UnsupportedAudioFileException e) {
				
				e.printStackTrace();
				
			}
			
		}
		
	}
	

	
}
