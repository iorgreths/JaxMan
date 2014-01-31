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
	 * Saves the list of options onto the hard drive of the computer. <br/>
	 * No error is reported if the attempt fails
	 */
	public void savePropertiesOnHardDrive(){
		
		PropertiesManager.getInstance().saveChangedOptions(option);
		PropertiesManager.getInstance().saveChangedPlayerProperties(player);
		
	}
	
	/**
	 * Sets the name for the player identified throught the pindex ( 1 <= pindex <= 4 )
	 * @param pindex - The index of the player ( 1 <= pindex <= 4 )
	 * @param name - The new name of the player
	 */
	public void setPlayerIName(int pindex, String name){
		
		// only 4 players
		if( 1<= pindex && pindex <=4){
			
			player.setProperty("p"+pindex+"name", name);
			
		}
		
	}
	
	/**
	 * Sets the controls for the player identified through the pindex. <br/>
	 * 1 <= pindex <= 4 ( = 4 Players ) <br/>
	 * controls format: int[5] ( CONTROL UP, CONTROL DOWN, CONTROL LEFT, CONTROL RIGHT, CONTROL ACTIVE )
	 * @param pindex - The index of the player ( 1<= pindex <= 4 )
	 * @param controls - The new controls of the player in the format: int[5] ( CONTROL UP, CONTROL DOWN, CONTROL LEFT, CONTROL RIGHT, CONTROL ACTIVE )
	 */
	public void setControlPattern(int pindex, int[] controls){
		
		if( 1 <= pindex && pindex <= 4){
			
			if(controls.length >= 5){
				
				player.setProperty("p"+pindex+"up", String.valueOf(controls[0]));
				player.setProperty("p"+pindex+"down", String.valueOf(controls[1]));
				player.setProperty("p"+pindex+"left", String.valueOf(controls[2]));
				player.setProperty("p"+pindex+"down", String.valueOf(controls[3]));
				player.setProperty("p"+pindex+"active", String.valueOf(controls[4]));
				
			}
			
		}
		
	}
	
	/**
	 * Sets whether or not the programm shall be executed in fullscreen mode
	 * @param isfullscreen - Fullscreen mode activated?
	 */
	public void setFullscreen(boolean isfullscreen){
		
		option.setProperty("fullscreen", String.valueOf(isfullscreen));
		
	}
	
	/**
	 * Seth whether or not the power-ups shall spawn during the game (must be defined at server location)
	 * @param powerupallowed - Spawn power ups?
	 */
	public void setPowerUps(boolean powerupallowed){
		
		option.setProperty("power-ups", String.valueOf(powerupallowed));
		
	}
	
	/**
	 * Sets the resolution of the window
	 * @param width - The width of the resolution in pixel
	 * @param height - The height of the resolution in pixel
	 */
	public void setResolution(int width, int height){
		
		option.setProperty("resolution", width+"x"+height);
		
	}
	
	/**
	 * Sets the volume of the background music
	 * @param volume - new volume of the bgm
	 */
	public void setBGMVolume(double volume){
		double set = volume;
		
		if(volume > 6.0206){
			
			set = 6.0206;
			
		}
		
		option.setProperty("volume", String.valueOf(set));
		
	}
	
	/**
	 * Sets the volume of the sound effect clips
	 * @param volume - new volume of the sound effect clips
	 */
	public void setSoundEffectsVolume(double volume){
		
		double set = volume;
		
		if(volume > 6.0206){
			
			set = 6.0206;
			
		}
		
		option.setProperty("soundeffect", String.valueOf(set));
		
	}
	
	/**
	 * Sets the boardsize of the game (must be done at server location) <br/>
	 * 3 sizes:
	 * <ul>
	 * <li>small </li>
	 * <li>medium </li>
	 * <li>large </li>
	 * </ul>
	 * @param size - the new size of the game board (small/medium/large)
	 */
	public void setBoardSize(String size){
		
		if( size.equals("small") || size.equals("medium") || size.equals("large")){
			
			option.setProperty("boardsize", size);
			
		}
		
	}
	
}
