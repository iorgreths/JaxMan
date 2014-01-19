package io.sound;

import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * This Class represents the BackgroundMusic. <br/>
 * It is a SoundClip which is indefinitely played, until stopped. <br/>
 * Because of it's nature it's highly discouraged to use this class <br/>
 * outside of another thread (resulting in an infinite loop).
 * 
 * @author Iorgreths
 * @version 1.0
 *
 */
public class BGM {

	private SoundClip sound; // The SoundClip which shall be played
	private boolean looping = true; // Playes the clip until looping = false
	
	/**
	 * Creates a new BGM from the specified file. <br/>
	 * Throws an Error, if the Path is illegable, or <br/>
	 * the format of the AudioFile is not supported.
	 * 
	 * @param filepath - The path to the AudioFile
	 * @throws UnsupportedAudioFileException
	 * @throws IOException
	 */
	public BGM(String filepath)throws UnsupportedAudioFileException, IOException{
		
		sound = new SoundClip(filepath);
		
	}
	
	/**
	 * Stops the Loop-Play.
	 */
	public void stop(){
		
		looping = false;
		
	}
	
	/**
	 * Plays the SoundClip indefinitely. <br/>
	 * If this Method is called in a program, which <br/>
	 * uses only one thread it results in an endless loop. <br/>
	 * Throws an exception if there is no available AudioLine.
	 *
	 * @throws LineUnavailableException
	 */
	public void play() throws LineUnavailableException{
		
		while(looping){
			
			sound.play();
			
		}
		
	}
	
	
}
