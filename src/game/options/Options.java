package game.options;

import java.util.Properties;

/**
 * A class representing the options of the game. <br/>
 * There are two types of options: <br/>
 * <ul>
 * <li> Options - Valid for the whole game (e.g. resolution)</li>
 * <li> PlayerOptions - Options for every player (e.g. controls)</li>
 * </ul>
 * 
 * @author Iorgreths
 * @version 1.0
 *
 */
public class Options {
	
	private static Options instance;
	private Properties option;
	private Properties player;
	
	/*
	 * Singleton-Pattern
	 * Only one instance of this class
	 */
	private Options(){
		
		option = PropertiesManager.getInstance().getOptionProperties();
		player = PropertiesManager.getInstance().getPlayerProperties();
		
	}

	/**
	 * Returns an instance of this class
	 * @return An instance of Options
	 */
	public static Options getInstance(){
		
		if(instance==null){
		
			instance=new Options();
		
		}
		
		return instance;
		
	}
	
	/**
	 * Returns the resolution for the WindowFrame. <br/>
	 * int[0] = width <br/>
	 * int[1] = height
	 * @return Resolution in format int[2] ( width, height )
	 */
	public int[] getResolution(){
		
		String[] split = option.getProperty("resolution").split("x");
		
		int[] ret = { Integer.valueOf(split[0]) , Integer.valueOf(split[1])};
		
		return ret;
		
	}
	
	/**
	 * Returns the names of the 4 local players in the format: <br/>
	 * String[4] (p1name, p2name, p3name, p4name)
	 * @return Playernames in format String[4] (p1name, p2name, p3name, p4name)
	 */
	public String[] getPlayerNames(){
		
		String[] playernames = new String[4];
		playernames[0] = player.getProperty("p1name");
		playernames[1] = player.getProperty("p2name");
		playernames[3] = player.getProperty("p3name");
		playernames[4] = player.getProperty("p4name");
		
		return playernames;
		
	}
	
	/**
	 * Returns whether or not the game shall be run in full screen mode.
	 * @return Full screen mode active?
	 */
	public boolean isFullscreen(){
		
		return Boolean.valueOf(option.getProperty("fullscreen"));
		
	}
	
	/**
	 * Returns whether or not power ups shall be allowed to spawn.
	 * @return Power-Ups spawning?
	 */
	public boolean isPowerUps(){
		
		return Boolean.valueOf(option.getProperty("power-ups"));
		
	}
	
	/**
	 * Returns the boardsize of the game: <br/>
	 * <ul>
	 * <li> small </li>
	 * <li> medium </li>
	 * <li> large </li>
	 * </ul>
	 * @return Size of the game field
	 */
	public String boardSize(){
		
		return option.getProperty("boardsize");
		
	}
	
	/**
	 * Returns the volume of the background music
	 * @return Volume of background music (in dB)
	 */
	public double getBGMVolume(){
		
		return Double.valueOf(option.getProperty("volume"));
		
	}
	
	/**
	 * Returns the volume of the sound effects within the game
	 * @return Volume of the sound effects (in dB)
	 */
	public double getSoundEffectVolume(){
		
		return Double.valueOf(option.getProperty("soundeffect"));
		
	}
	
	/**
	 * Returns the VK_Key codes of the player 1 controls in the format: <br/>
	 * int[5] (control up, control down, control left, control right, control active)
	 * @return VK_Key codes of p1 in format: int[5] (control up, control down, control left, control right, control active)
	 */
	public int[] getPlayer1Controls(){
		
		int[] controls = new int[5];
		
		controls[0] = Integer.valueOf(player.getProperty("p1up"));
		controls[1] = Integer.valueOf(player.getProperty("p1down"));
		controls[2] = Integer.valueOf(player.getProperty("p1left"));
		controls[3] = Integer.valueOf(player.getProperty("p1right"));
		controls[4] = Integer.valueOf(player.getProperty("p1active"));
		
		return controls;
		
	}
	
	/**
	 * Returns the VK_Key codes of the player 2 controls in the format: <br/>
	 * int[5] (control up, control down, control left, control right, control active)
	 * @return VK_Key codes of p2 in format: int[5] (control up, control down, control left, control right, control active)
	 */
	public int[] getPlayer2Controls(){
		
		int[] controls = new int[5];
		
		controls[0] = Integer.valueOf(player.getProperty("p2up"));
		controls[1] = Integer.valueOf(player.getProperty("p2down"));
		controls[2] = Integer.valueOf(player.getProperty("p2left"));
		controls[3] = Integer.valueOf(player.getProperty("p2right"));
		controls[4] = Integer.valueOf(player.getProperty("p2active"));
		
		return controls;
		
	}
	
	/**
	 * Returns the VK_Key codes of the player 3 controls in the format: <br/>
	 * int[5] (control up, control down, control left, control right, control active)
	 * @return VK_Key codes of p3 in format: int[5] (control up, control down, control left, control right, control active)
	 */
	public int[] getPlayer3Controls(){
		
		int[] controls = new int[5];
		
		controls[0] = Integer.valueOf(player.getProperty("p3up"));
		controls[1] = Integer.valueOf(player.getProperty("p3down"));
		controls[2] = Integer.valueOf(player.getProperty("p3left"));
		controls[3] = Integer.valueOf(player.getProperty("p3right"));
		controls[4] = Integer.valueOf(player.getProperty("p3active"));
		
		return controls;
		
	}
	
	/**
	 * Returns the VK_Key codes of the player 4 controls in the format: <br/>
	 * int[5] (control up, control down, control left, control right, control active)
	 * @return VK_Key codes of p4 in format: int[5] (control up, control down, control left, control right, control active)
	 */
	public int[] getPlayer4Controls(){
		
		int[] controls = new int[5];
		
		controls[0] = Integer.valueOf(player.getProperty("p4up"));
		controls[1] = Integer.valueOf(player.getProperty("p4down"));
		controls[2] = Integer.valueOf(player.getProperty("p4left"));
		controls[3] = Integer.valueOf(player.getProperty("p4right"));
		controls[4] = Integer.valueOf(player.getProperty("p4active"));
		
		return controls;
		
	}
	
	/**
	 * Saves the list of options onto the hard drive of the computer. <br7>
	 * No error is reported if the attempt fails
	 */
	public void savePropertiesOnHardDrive(){
		
		PropertiesManager.getInstance().saveChangedOptions(option);
		PropertiesManager.getInstance().saveChangedPlayerProperties(player);
		
	}
	
	// TODO SET PROPERTIES
	
}
