package game.options;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;

/**
 * 
 * This class handles loading the Options and Player settings, <br/>
 * as well as writing changed setting onto the hard disk.
 * 
 * @author Iorgreths
 * @version 1.0
 *
 */
class PropertiesManager {
	
	private final String optionfile = "/options.properties"; // file containing the game options
	private final String playerfile = "/players.properties"; // file containing the player options
	private String relativepath; // path towards parent dir of the jar
	private static PropertiesManager instance;
	
	/*
	 * Singleton-Pattern
	 * Only one instance of this object
	 */
	private PropertiesManager(){
		
		URL jarpath = PropertiesManager.class.getProtectionDomain().getCodeSource().getLocation();
		try {
			
			relativepath = new File(jarpath.toURI()).getParent();
			
		} catch (URISyntaxException e) {
			
			e.printStackTrace();
			
		}
		
	}
	
	/**
	 * Returns an instance of this class.
	 * @return An instance of PropertiesManager
	 */
	public static PropertiesManager getInstance(){
		
		if(instance == null) instance = new PropertiesManager();
		
		return instance;
		
	}

	/**
	 * Reads the existing options from the hard disk. <br/>
	 * If there are no properties stored, or the access towards the file is denied,<br/>
	 * the default-properties will be assumed. <br/>
	 * There are the following properties: <br/>
	 * <ul>
	 * 	<li>resolution - String (e.g. 640x480) </li>
	 *  <li>fullscreen - boolean </li>
	 *  <li>power-ups  - boolean </li>
	 *  <li>volume     - double </li>
	 *  <li>soundeffect - double
	 *  <li>boardsize  - String ( small,medium,large ) </li>
	 * </ul>
	 * 
	 * @return Properties - A properties object, with the properties mentioned above.
	 */
	public Properties getOptionProperties(){
		
		Properties options = new Properties();
		
		try {
			
			Reader propreader = new FileReader( relativepath + optionfile);
			options.load(propreader);
			
			// Are the options within the file reliable?
			String p = options.getProperty("fullscreen");
			if( p == null || !(p.equals("false") || p.equals("true")) ) options.setProperty("fullscreen", "false"); // default
			
			p = options.getProperty("power-ups");
			if( p == null || !(p.equals("false") || p.equals("true")) ) options.setProperty("power-ups", "false"); // default
			
			p = options.getProperty("volume");
			if( p == null || Double.valueOf(p) == Double.NaN ) options.setProperty("volume", "0"); // default
			
			p = options.getProperty("soundeffect");
			if( p == null || Double.valueOf(p) == Double.NaN ) options.setProperty("soundeffect", "1"); // default
			
			p = options.getProperty("boardsize");
			if( p == null || !(p.equals("small") || (p.equals("medium") || p.equals("large")) ) ) options.setProperty("boardsize", "medium"); //default
			
			p = options.getProperty("resolution");
			if( p == null || (p.indexOf('x') == -1) ){
				
				options.setProperty("resolution", "680x480"); // default
				
			}else{
				
				String[] concat = p.split("x");
				
				try{
					
					Integer.valueOf(concat[0]);
					Integer.valueOf(concat[1]);
					
				}catch(NumberFormatException nfe){
					
					//Why are you tempering with my file? - Programmer
					options.setProperty("resolution", "680x480");
					
				}
				
			}
			
		} catch (IOException e) {
			
			// no properties file? -> set defaults
			options.setProperty("resolution", "640x480");
			options.setProperty("fullscreen", "false");
			options.setProperty("power-ups", "false");
			options.setProperty("volume", "0");
			options.setProperty("soundeffect", "1");
			options.setProperty("boardsize", "medium");
			
		}
		
		
		return options;
		
	}
	
	/**
	 * Reads the existing options from the hard disk. <br/>
	 * If there are no properties stored, or the access towards the file is denied,<br/>
	 * the default-properties will be assumed. <br/>
	 * There are the following properties (I &isin; M = { 1,2,3,4 } ): <br/>
	 * <ul>
	 * 	<li>pIup - CONTROL UP for player I</li>
	 *  <li>pIdown - CONTROL DOWN for player I</li>
	 *  <li>pIleft - CONTROL LEFT for player I</li>
	 *  <li>pIright - CONTROL RIGHT for player I</li>
	 *  <li>pIactive - CONTROL ACTIVE for player I </li>
	 *  <li>pIname - player name for player I </li>
	 * </ul>
	 * 
	 * @return Properties - A properties object, with the properties mentioned above.
	 */
	public Properties getPlayerProperties(){
		
		Properties player = new Properties();
		
		try {
			
			Reader propreader = new FileReader( relativepath + playerfile);
			player.load(propreader);
			
			// player 1 controls reliable?
			String p = player.getProperty("p1up");
			if(p == null || p.length() > 1) player.setProperty("p1up", String.valueOf(KeyEvent.VK_W)); // default
			
			p = player.getProperty("p1down");
			if(p == null ) player.setProperty("p1down", String.valueOf(KeyEvent.VK_S)); // default
			
			p = player.getProperty("p1left");
			if(p == null ) player.setProperty("p1left", String.valueOf(KeyEvent.VK_A)); // default
			
			p = player.getProperty("p1right");
			if(p == null ) player.setProperty("p1right", String.valueOf(KeyEvent.VK_D)); // default
			
			p = player.getProperty("p1active");
			if(p == null ) player.setProperty("p1active", String.valueOf(KeyEvent.VK_F)); // default
			
			// player 2 controls reliable?
			p = player.getProperty("p2up");
			if(p == null ) player.setProperty("p2up", String.valueOf(KeyEvent.VK_I)); // default
			
			p = player.getProperty("p2down");
			if(p == null ) player.setProperty("p2down", String.valueOf(KeyEvent.VK_K)); // default
			
			p = player.getProperty("p2left");
			if(p == null ) player.setProperty("p2left", String.valueOf(KeyEvent.VK_J)); // default
			
			p = player.getProperty("p2right");
			if(p == null ) player.setProperty("p1right", String.valueOf(KeyEvent.VK_L)); // default
			
			p = player.getProperty("p2active");
			if(p == null ) player.setProperty("p1active", String.valueOf(KeyEvent.VK_COLON)); // default
			
			// player 3 controls reliable?
			p = player.getProperty("p3up");
			if(p == null) player.setProperty("p3up", String.valueOf(KeyEvent.VK_UP)); // default
			
			p = player.getProperty("p3down");
			if(p == null) player.setProperty("p3down", String.valueOf(KeyEvent.VK_DOWN)); // default
			
			p = player.getProperty("p3left");
			if(p == null) player.setProperty("p3left", String.valueOf(KeyEvent.VK_LEFT)); // default
			
			p = player.getProperty("p3right");
			if(p == null) player.setProperty("p3right", String.valueOf(KeyEvent.VK_RIGHT)); // default
			
			p = player.getProperty("p3active");
			if(p == null) player.setProperty("p3active", String.valueOf(KeyEvent.VK_SHIFT)); // default
			
			// player 4 controls reliable?
			p = player.getProperty("p4up");
			if(p == null) player.setProperty("p4up", String.valueOf(KeyEvent.VK_8)); // default
			
			p = player.getProperty("p4down");
			if(p == null) player.setProperty("p4down", String.valueOf(KeyEvent.VK_5)); // default
			
			p = player.getProperty("p4left");
			if(p == null) player.setProperty("p4left", String.valueOf(KeyEvent.VK_4)); // default
			
			p = player.getProperty("p4right");
			if(p == null) player.setProperty("p4right", String.valueOf(KeyEvent.VK_6)); // default
			
			p = player.getProperty("p4active");
			if(p == null) player.setProperty("p4active", String.valueOf(KeyEvent.VK_9)); // default
			
			// names not empty?
			p = player.getProperty("p1name");
			if(p == null) player.setProperty("p1name", "Jaxman"); // default
			
			p = player.getProperty("p2name");
			if( p == null) player.setProperty("p2name", "Maxman"); // default
			
			p = player.getProperty("p3name");
			if( p == null) player.setProperty("p3name", "Marioman"); // default
			
			p = player.getProperty("p4name");
			if(p == null) player.setProperty("p4name", "Sonicman"); // default
			
		} catch (IOException e) {
			
			// no properties file? -> set defaults
	
			player.setProperty("p1up", String.valueOf(KeyEvent.VK_W));
						
			player.setProperty("p1down", String.valueOf(KeyEvent.VK_S));
						
			player.setProperty("p1left", String.valueOf(KeyEvent.VK_A));
						
			player.setProperty("p1right", String.valueOf(KeyEvent.VK_D));
						
			player.setProperty("p1active", String.valueOf(KeyEvent.VK_F));
						
			player.setProperty("p2up", String.valueOf(KeyEvent.VK_I));
						
			player.setProperty("p2down", String.valueOf(KeyEvent.VK_K)); // default
						
			player.setProperty("p2left", String.valueOf(KeyEvent.VK_J)); // default
						
			player.setProperty("p1right", String.valueOf(KeyEvent.VK_L)); // default
						
			player.setProperty("p1active", String.valueOf(KeyEvent.VK_COLON)); // default
						
			player.setProperty("p3up", String.valueOf(KeyEvent.VK_UP)); // default
						
			player.setProperty("p3down", String.valueOf(KeyEvent.VK_DOWN)); // default
						
			player.setProperty("p3left", String.valueOf(KeyEvent.VK_LEFT)); // default
						
			player.setProperty("p3right", String.valueOf(KeyEvent.VK_RIGHT)); // default
						
			player.setProperty("p3active", String.valueOf(KeyEvent.VK_SHIFT)); // default
						
			player.setProperty("p4up", String.valueOf(KeyEvent.VK_8)); // default
						
			player.setProperty("p4down", String.valueOf(KeyEvent.VK_5)); // default
						
			player.setProperty("p4left", String.valueOf(KeyEvent.VK_4)); // default
						
			player.setProperty("p4right", String.valueOf(KeyEvent.VK_6)); // default
						
			player.setProperty("p4active", String.valueOf(KeyEvent.VK_9)); // default
						
			player.setProperty("p1name", "Jaxman");
						
			player.setProperty("p2name", "Maxman");
						
			player.setProperty("p3name", "Marioman");
						
			player.setProperty("p4name", "Sonicman");			
			
		}
		return player;
		
	}
	
	/**
	 * Saves the game customizations in the 'options.properties' file. <br/>
	 * Does nothing if unable to write at location.
	 * @param options - The Properties to be saved onto hard disk
	 */
	public void saveChangedOptions(Properties options){
		
		try {
			
			Writer optionwriter = new FileWriter( relativepath + optionfile);
			options.store(optionwriter, "Optionfile");
			
		} catch (IOException e) {
			
			// hand the debug console the error code
			// don't write file
			e.printStackTrace();
			
		}
		
	}
	
	/**
	 * Saves the player customizations in the 'players.properties' file. <br/>
	 * Does nothing if unable to write at location.
	 * @param player - The Properties to be saved onto hard disk
	 */
	public void saveChangedPlayerProperties(Properties player){
		
		try {
			
			Writer playerwriter = new FileWriter( relativepath + playerfile);
			player.store(playerwriter, "Playerfile");
			
		} catch (IOException e) {
			
			// hand the debug console the error code
			// don't write file
			e.printStackTrace();
			
		}
		
	}
	
}
