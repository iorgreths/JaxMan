package IO.sound;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * 
 * A Class that represents a SoundClip, which can be played, stopped, looped, etc. <br/>
 * <br/>
 * Uses the following packages:
 * <ul>
 * <li> JLayer 1.0.1 </li>
 * <li> MP3SPI 1.9.5 </li>
 * <li> Tritonus (share) .3.6 </li>
 * </ul>
 * 
 * @author Iorgreths
 * @version 1.0
 *
 */
public class SoundClip {

	private File file; // The file which shall be played
	private AudioFormat format; // The format of the clip
	private SourceDataLine line; // The line to play on
	private DataLine.Info info; // information about the format
	
	/**
	 * Creates a new SoundClip from the specified File behind the filepath <br/>
	 * Supported: .wav, .au, .aiee, .mp3 <br/>
	 * <br/>
	 * Throws an exception if the file is from an unsupported format, or <br/>
	 * the filepath is not pointing to a legal file
	 * @param filepath - Path to the AudioFile
	 * @throws UnsupportedAudioFileException
	 * @throws IOException
	 */
	public SoundClip(String filepath) throws UnsupportedAudioFileException, IOException{
		
		file = new File(filepath); // The Audio file
		
		AudioInputStream audio_in = AudioSystem.getAudioInputStream(file); // Get the Inputstream for the file
		
		//Create the format for the OUT-Stream
		format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, audio_in.getFormat().getSampleRate(), 16, audio_in.getFormat().getChannels(),
				                      audio_in.getFormat().getChannels() * 2, audio_in.getFormat().getSampleRate(), false);
		
		info = new DataLine.Info(SourceDataLine.class, format);
		
		audio_in.close();
		
		
	}
	
	/**
	 * Plays the SoundClip one time <br/>
	 * <br/>
	 * Throws an error if there is no free line to play the sound
	 * @throws LineUnavailableException
	 */
	public void play() throws LineUnavailableException{
		
		AudioInputStream din = null;
		// Open InputStream
		try{
			
			AudioInputStream audio_in = AudioSystem.getAudioInputStream(file);
			din = AudioSystem.getAudioInputStream(format, audio_in);
			
			// Get the line to play the file
			line = null;
			line = (SourceDataLine) AudioSystem.getLine(info);
			
			// Open the AudioLine to play
			line.open(format);
			
			
			byte[] data = new byte[4096];
			line.start(); // Starting the line
			
			int nBytesRead;
			while ((nBytesRead = din.read(data, 0, data.length)) != -1) {	
				line.write(data, 0, nBytesRead);
			}
			
			line.drain();
			line.close();
			
			din.close();
			audio_in.close();
			
		}catch(UnsupportedAudioFileException uafe){
			
			// If this place is reached the file format has been changed while playing (oO)
			uafe.printStackTrace();
			
		}catch(IOException ioe){
			
			// If this place is reached the file has been deleted while playing
			ioe.printStackTrace();
			
		}
		
	}
	
	
	
	
}
