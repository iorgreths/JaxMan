package io.sound;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * 
 * A Class that represents a SoundClip, which can be played. <br/>
 * Furthermore it is possible to be noticed if the state of the AudioLine changes. <br/>
 * For this purpose the listening class has to register itself as LineListener (use .addLineListener(LineListener)).
 * <br/>
 * Uses the following packages:
 * <ul>
 * <li> JLayer 1.0.1 </li>
 * <li> MP3SPI 1.9.5 </li>
 * <li> Tritonus (share) .3.6 </li>
 * </ul>
 * 
 * @author Iorgreths
 * @version 2.0
 *
 */
class SoundClip {

	protected SourceDataLine line; // The input line for the audio device
	private AudioInputStream audio_in; // The input-stream from the audio file
	private AudioFormat format; // The format of the AudioFile
	
	/**
	 * Creates a new SoundClip from the specified File behind the filepath <br/>
	 * Supported: .wav, .au, , .mp3 <br/>
	 * <br/>
	 * Throws an exception if the file is from an unsupported format, or <br/>
	 * the filepath is not pointing to a legal file, or <br/>
	 * no line is availlable
	 * @param filepath - Path to the AudioFile
	 * @throws UnsupportedAudioFileException - The format of the AudioFile is not supported (e.g. .ogg)
	 * @throws IOException - Unable to read from file (maybe locked, or deleted)
	 * @throws LineUnavailableException  - No further line available for AudioOutput
	 */
	public SoundClip(String filepath) throws UnsupportedAudioFileException, IOException, LineUnavailableException{
		
		audio_in = AudioSystem.getAudioInputStream(new File(filepath));
		// Returns an InputStream to the specified AudioFile
		
		//Create the format for the INPUT STREAM-Stream (MP3, etc.)
		format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, audio_in.getFormat().getSampleRate(), 16, audio_in.getFormat().getChannels(),
				                      audio_in.getFormat().getChannels() * 2, audio_in.getFormat().getSampleRate(), false);
		
		//Set the new format in the AudioInputStream
		audio_in = AudioSystem.getAudioInputStream(format, audio_in);
		
		//Get the information for creating a DataLine
		DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
		
		//Get the input line
		line = (SourceDataLine) AudioSystem.getLine(info);
		line.open();
		
	}
	
	/**
	 * Plays the SoundClip one time <br/>
	 * <br/>
	 * Throws an error if there is no free line to play the sound
	 * @throws LineUnavailableException - No further line available for AudioOutput
	 * @throws IOException  - Unable to read from file (mabye locked, or deleted)
	 */
	public void play() throws LineUnavailableException, IOException{
		
		line.open();
		byte[] data = new byte[line.getBufferSize()];
		line.start(); // Starting the line
		
		int nBytesRead;
		while ((nBytesRead = audio_in.read(data, 0, data.length)) != -1) {	
			line.write(data, 0, nBytesRead);
		}
		
		line.close();
		
	}
	
	/**
	 * Closes the InputStream from the AudioFile. <br/>
	 * Please notice that after closing the AudioInputStream no further <br/>
	 * reading from this Stream is possible and afterwards called Methods of this <br/>
	 * class result in IOExceptions.
	 * @throws IOException - Unable to read from file (line already closed?)
	 */
	public void closeAudioInput() throws IOException{
		
		audio_in.close();
		
	}
	
	/**
	 * Adds the listener to the list of listeners of this AudioLine. <br/>
	 * Each time the state of the line changes the listeners will be noticed. <br/>
	 * The line has the following states: <br/>
	 * <ul>
	 * <li> CLOSE - The line has been closed</li>
	 * <li> START - The line has been made ready for playback </li>
	 * <li> OPEN  - The line has been opened </li>
	 * <li> STOP  - The line has stopped playing the playback </li>
	 * </ul>
	 * @param listener - The class, which shall monitor this AudioLine
	 */
	public void addLineListener(LineListener listener){
		
		line.addLineListener(listener);
		
	}
	
	/**
	 * Removes the specified LineListener (listener) from the list of observers.
	 * @param listener - The LineListener, which shall be removed
	 */
	public void removeLineListener(LineListener listener){
		
		line.removeLineListener(listener);
		
	}
	
	/**
	 * Returns whether the AudioLine is currently playing or not
	 * @return - true, if the line is currently playing something.
	 */
	public boolean isLinePlaying(){
		
		return line.isActive();
		
	}
	
	/**
	 * Immediately stops the playback on the line.
	 */
	public void stopPlaying(){
		
		line.flush();
		line.stop();
		
	}
	
	
	
	
}
