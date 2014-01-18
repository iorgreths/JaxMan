package io.sound;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * An internal class of SoundManager <br/>
 * It represents the Thread, which plays the BackgroundMusic of the game.
 * 
 * @author Iorgreths
 * @version 1.0
 */
class BGM_Thread extends Thread{
	
	private BGM bgm; // The BGM to be played
	
	/**
	 * Creates a new BGM_Thread with a specified BGM file, <br/>
	 * which shall be played.
	 * 
	 * @param bgm - The BackgroundMusic to be played
	 */
	public BGM_Thread(BGM bgm){
		
		this.bgm = bgm;
		
	}
	
	/**
	 * Starts the thread
	 */
	public void run(){
		
		try {
			
			bgm.play();
			
		} catch (LineUnavailableException e) {
			
			e.printStackTrace();
			
		}
		
	}
	
}

/**
 * An internal class of SoundManager <br/>
 * It represents the Thread, which plays the SoundEffects of the game.
 * 
 * @author Iorgreths
 * @version 1.0
 *
 */
class SoundEffect_Thread extends Thread{
	
	private SoundClip sound; // The Sound to be played
	
	/**
	 * Creates a new SoundEffect_Thread, with a specified SoundClip.
	 * @param sound - The SoundClip, which can be played
	 */
	public SoundEffect_Thread(SoundClip sound){
		
		this.sound = sound;
		
	}
	
	/**
	 * Plays a SoundEffect, if sound is not empty
	 */
	public void run(){
		
		if(sound != null){
			
			this.playEffect();
			
		}
		
	}
	
	/*
	 * actually plays the SoundClip
	 * INTERNAL METHOD
	 */
	private void playEffect(){
		
		try {
			
			sound.play();
			
		} catch (LineUnavailableException e) {
			
			e.printStackTrace();
			
		}
		
	}
	
	/**
	 * Replaces the current SoundClip with a new one and plays it.<br/>
	 * The SoundClip will not be swapped if the new one is empty
	 * @param sound - The new SoundClip
	 */
	public void changeSoundEffect(SoundClip sound){
		
		if(sound != null){
			
			this.sound = sound;
			this.playEffect();
			
		}
		
	}
	
}

/**
 * The SoundManager manages the BackgroundMusic, as well as the SoundEffect of the game. <br/>
 * The BackgroundMusic can be changed while playing. <br/>
 * Each SoundEffect is mapped towards a specific Name ( e.g. 'p1.death') and can be accessed through it. <br/>
 * It is always possible to further add SoundEffects.
 * 
 * @author Iorgreths
 * @version 1.0
 */
public class SoundManager {

	private BGM bgm; // The current BGM
	private BGM_Thread bgthread; // The Thread, which plays the BGM 
	private SoundEffect_Thread sethread; // The Thread, which plays SoundEffects
	private Map<String, SoundClip> sound_effects; // All SoundEffects of the game
	
	/**
	 * Creates a new SoundManager, without SoundEffects and BGM
	 */
	public SoundManager(){
		super();
		
		bgm = null;
		bgthread = null;
		sethread = null;
		sound_effects = new HashMap<String, SoundClip>();
		
	}
	
	/**
	 * HotSwaps the current BGM with another file and plays the new file in a loop. <br/>
	 * Throws an Error if the new Path is not legit or the Format of the new file is not supported.
	 * @param filepath - The path to the new AudioFile
	 * @throws IOException 
	 * @throws UnsupportedAudioFileException 
	 */
	public void changeBGM(String filepath) throws UnsupportedAudioFileException, IOException{
		
		if(bgm != null){
			
			bgm.stop();
			
		}
			
		bgm = new BGM(filepath);
		bgthread = new BGM_Thread(bgm);
		bgthread.run();
		
	}
	
	/**
	 * Adds a new SoundEffect to the current list. <br/>
	 * Maps the new SoundEffect to a specific name = idenfitier <br/>
	 * The SoundEffect is a AudioFile located locally at the computer <br/>
	 * Throws an error, if the filepath is not legit or the Format of the AudioFile is not supported.
	 * @param identifier - The Name of the SoundEffect, through which it shall be accessible
	 * @param filepath - The Path to the AudioFile
	 * @throws UnsupportedAudioFileException
	 * @throws IOException
	 */
	public void addSoundEffect(String identifier, String filepath) throws UnsupportedAudioFileException, IOException{
		
		sound_effects.put(identifier, new SoundClip(filepath));
		
	}
	
	/**
	 * Play the SoundEffect, which is behind the Name (identifier). <br/>
	 * If no SoundEffect matches the name, nothing is played. <br/>
	 * <br/>
	 * Throws an exception if there is no free AudioLine.
	 * @param identifier - The Name of the SoundEffect, to be played
	 * @throws LineUnavailableException
	 */
	public void playSound(String identifier) throws LineUnavailableException{
		
		if(sethread != null){
			
			sethread.changeSoundEffect(sound_effects.get(identifier));
			
		}else{
			
			sethread = new SoundEffect_Thread(sound_effects.get(identifier));
			sethread.run();
			
		}
		
	}
	
}
