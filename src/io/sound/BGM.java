package io.sound;

import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * This Class represents the BackgroundMusic. <br/>
 * It is a SoundClip which is indefinitely played, until stopped. <br/>
 * This is achieved by observing the AudioLine and getting interrupts every time the line changes. <br/>
 * 
 * 
 * @author Iorgreths
 * @version 1.3
 *
 */
class BGM{

	private SoundClip sound; // The SoundClip which shall be played
	private boolean looping; // Playes the clip until looping = false
	
	/**
	 * Creates a new BGM from the specified file. <br/>
	 * Throws an Error, if the Path is illegable, or <br/>
	 * the format of the AudioFile is not supported, or <br/>
	 * no further line for AudioOutput is available.
	 * 
	 * @param filepath - The path to the AudioFile
	 * @throws UnsupportedAudioFileException - Format of the AudioFile is not supported (e.g. .ogg)
	 * @throws IOException - Unable to read from file (maybe locked or deleted)
	 * @throws LineUnavailableException - No free line for further AudioOutput
	 */
	public BGM(String filepath)throws UnsupportedAudioFileException, IOException, LineUnavailableException{
		
		looping = true;
		sound = new SoundClip(filepath);
		
	}
	
	/**
	 * Stops the BackgroundMusic. <br/>
	 * Resulting in no further playback invokes and the immediate stop of the music.
	 */
	public void stop(){
		
		looping = false;
		sound.stopPlaying();
		
	}
	
	/**
	 * Starts playing the music. <br/>
	 * The music will be played until stopped. <br/>
	 * Because of it's nature it's highly recommended not too use this method within the main-Thread.
	 *
	 * @throws LineUnavailableException  - no further AudioLines available
	 * @throws IOException - Unable to read from file (changed dir?, deleted?, locked?)
	 * @throws UnsupportedAudioFileException - Unsupported format of audio file (e.g. .ogg)
	 */
	public void play() throws LineUnavailableException, IOException, UnsupportedAudioFileException{
		
		while (looping){
			
			sound.play();
			
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
		
		sound.setVolume(volume);
		
	}
	
	
}
