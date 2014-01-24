package io.sound;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


/**
 * The SoundManager manages the BackgroundMusic, as well as the SoundEffect of the game. <br/>
 * The BackgroundMusic can be changed while playing. <br/>
 * Each SoundEffect is mapped towards a specific Name ( e.g. 'p1.death') and can be accessed through it. <br/>
 * It is always possible to further add SoundEffects.
 * 
 * @author Iorgreths
 * @version 1.2
 */
public class SoundManager {

	private Map<String, SoundClip> sound_effects; // All SoundEffects of the game
	private BGMThread bgmthread; // The thread working the background music
	private SoundEffectsThread soundeff; // The thread working the SoundEffects
	
	/**
	 * Creates a new SoundManager, without SoundEffects and BGM
	 */
	public SoundManager(){
		super();
		
		sound_effects = new HashMap<String, SoundClip>();
		bgmthread = new BGMThread();
		soundeff = new SoundEffectsThread();
		
	}
	
	/**
	 * HotSwaps the current BGM with another file and plays the new file in a loop. <br/>
	 * Throws an Error if the new Path is not legit or the Format of the new file is not supported.
	 * @param filepath - The path to the new AudioFile
	 * @throws IOException - Unable to read from file (maybe locked or deleted)
	 * @throws UnsupportedAudioFileException - Unsupported format of the AudioFile (e.g. .ogg)
	 * @throws LineUnavailableException - No further AudioLines available
	 */
	public void changeBGM(String filepath) throws UnsupportedAudioFileException, IOException, LineUnavailableException{
		
		bgmthread.setMusic(new BGM(filepath));
		Thread bgm = new Thread(bgmthread);
		bgm.start();
		
	}
	
	/**
	 * HotSwaps the current BGM with another file and plays the new file in a loop. <br/>
	 * Throws an Error if the new Path is not legit or the Format of the new file is not supported.
	 * @param filepath - The path to the new AudioFile
	 * @param volume - The volume to use, in dB ( between -80 and 6.0206 )
	 * @throws IOException - Unable to read from file (maybe locked or deleted)
	 * @throws UnsupportedAudioFileException - Unsupported format of the AudioFile (e.g. .ogg)
	 * @throws LineUnavailableException - No further AudioLines available
	 */
	public void changeBGM(String filepath, float volume) throws UnsupportedAudioFileException, IOException, LineUnavailableException{
		
		bgmthread.setMusic(new BGM(filepath));
		bgmthread.setVolume(volume);
		Thread bgm = new Thread(bgmthread);
		bgm.start();
		
	}
	
	/**
	 * Stops the BackgroundMusic from playing.
	 */
	public void stopBackgroundMusic(){
		
		bgmthread.ceasePlaying();
		
	}
	
	/**
	 * Adds a new SoundEffect to the current list. <br/>
	 * Maps the new SoundEffect to a specific name = idenfitier <br/>
	 * The SoundEffect is a AudioFile located locally at the computer <br/>
	 * Throws an error, if the filepath is not legit or the Format of the AudioFile is not supported.
	 * @param identifier - The Name of the SoundEffect, through which it shall be accessible
	 * @param filepath - The Path to the AudioFile
	 * @throws UnsupportedAudioFileException - Unsupported format of the AudioFile (e.g. .ogg)
	 * @throws IOException - Unable to read from file (maybe locked or deleted)
	 * @throws LineUnavailableException  - No further line available for playing sounds
	 */
	public void addSoundEffect(String identifier, String filepath) throws UnsupportedAudioFileException, IOException, LineUnavailableException{
		
		sound_effects.put(identifier, new SoundClip(filepath));
		
	}
	
	/**
	 * Adds a new SoundEffect to the current list. <br/>
	 * Maps the new SoundEffect to a specific name = idenfitier <br/>
	 * The SoundEffect is a AudioFile located locally at the computer <br/>
	 * Throws an error, if the filepath is not legit or the Format of the AudioFile is not supported.
	 * @param identifier - The Name of the SoundEffect, through which it shall be accessible
	 * @param filepath - The Path to the AudioFile
	 * @param volume - The volume to use, in dB ( between -80 and 6.0206 )
	 * @throws UnsupportedAudioFileException - Unsupported format of the AudioFile (e.g. .ogg)
	 * @throws IOException - Unable to read from file (maybe locked or deleted)
	 * @throws LineUnavailableException  - No further line available for playing sounds
	 */
	public void addSoundEffect(String identifier, String filepath, float volume) throws UnsupportedAudioFileException, IOException, LineUnavailableException{
		SoundClip clip = new SoundClip(filepath);
		clip.setVolume(volume);
		sound_effects.put(identifier, clip);
		clip = null;
		
	}
	
	/**
	 * Play the SoundEffect, which is behind the Name (identifier). <br/>
	 * If no SoundEffect matches the name, nothing is played. <br/>
	 * <br/>
	 * Throws an exception if there is no free AudioLine.
	 * @param identifier - The Name of the SoundEffect, to be played
	 * @throws LineUnavailableException - No further line available for playing sounds.
	 */
	public void playSound(String identifier) throws LineUnavailableException{
		
		soundeff.setSoundEffect(sound_effects.get(identifier));
		Thread effect = new Thread(soundeff);
		effect.run();
		
	}
	
}
