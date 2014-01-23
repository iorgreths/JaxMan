package io.sound;

import java.io.IOException;

import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * This Class represents the BackgroundMusic. <br/>
 * It is a SoundClip which is indefinitely played, until stopped. <br/>
 * This is achieved by observing the AudioLine and getting interrupts every time the line changes. <br/>
 * 
 * 
 * @author Iorgreths
 * @version 1.2
 *
 */
class BGM implements LineListener{

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
		sound.addLineListener(this);
		
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
	 * The start of the play-cicle. <br/>
	 * It has only to be called once, since afterwards everything will be handled via Notifications. <br/>
	 * If the line is already playing this method does nothing.
	 *
	 * @throws LineUnavailableException
	 * @throws IOException 
	 */
	public void play() throws LineUnavailableException, IOException{
		
		if(!sound.isLinePlaying()){
			
			sound.play();
			
		}
		
	}

	/**
	 * NOTICE!: This method is not supposed to be called from the CLIENT.
	 * 
	 * Every time the state of the line changes this method is called. <br/>
	 * If the BGM is supposed to still loop this method will invoke another playback.
	 * 
	 */
	@Override
	public void update(LineEvent event) {
		
		System.out.println(event.getType());
		
		if(looping){
			
			if(event.getType() == LineEvent.Type.STOP){
				
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
	
	
}
