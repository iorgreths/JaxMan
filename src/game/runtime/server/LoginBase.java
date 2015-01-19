package game.runtime.server;

import java.net.InetAddress;

/**
 * The "mind" behind the login-screen. <br/>
 * The server knows what players have connected, what their ip is and the username behind that player.
 * 
 * 
 * @author Iorgreths
 * @version 1.0
 *
 */
public class LoginBase {

	private InetAddress[] player;
	private boolean[] ready;
	private String[] usernames;
	
	/**
	 * Creates a new LoginBase with the default player-amount of four.
	 */
	public LoginBase(){
		
		this(4);
		
	}
	
	/**
	 * Creates a new LoginBase with the specified amount of players.
	 * @param playeranz - The amount of players, which can play
	 */
	public LoginBase(int playeranz){
		
		player = new InetAddress[playeranz];
		ready = new boolean[playeranz];
		usernames = new String[playeranz];
		
	}
	
	/**
	 * Adds a new player to the game.
	 * @param address - The IP-address of the host behind the player
	 * @param user - The username of the player
	 */
	public void addPlayer(InetAddress address, String user){
		
		int i = 0;
		while(usernames[i] != null){
			
			if(usernames[i].equals(user)) return; // it is not possible that 2 players have the same name
			i++;
			
		}
		
		player[i] = address;
		usernames[i] = user;
		
	}
	
	/**
	 * Sets the player (user) ready, or not ready (specified through mind) <br/>
	 * Does nothing if the user is not a current player.
	 * @param user - The username of the player, which sets a new ready state
	 * @param mind - The new ready state of the player (true/false)
	 */
	public void setReady(String user, boolean mind){
		
		for(int i=0; i< usernames.length; i++){
			
			if(usernames[i].equals(user)){
				
				ready[i] = mind;
				break;
				
			}
			
		}
		
	}
	
	/**
	 * Checks whether or not all players are ready for the game.
	 * @return - True then and only then, when all players are ready
	 */
	public boolean allReady(){
		
		for(boolean b : ready){
			
			if(b == false) return false;
			
		}
		
		return true;
	}
	
	/**
	 * Removes a player, specified through his IP, from the game
	 * @param address - The ip-address of the player, which shall be removed
	 */
	public void removePlayer(InetAddress address){
		
		for(int i=0; i<player.length; i++){
			
			if(player[i].equals(address)){
				
				player[i] = null;
				ready[i] = false;
				usernames[i] = null;
				sortOutNull(i);
				
			}
			
		}
		
	}
	
	/**
	 * Removes a player, specified through his username, from the game
	 * @param user - The username of the player, which shall be removed
	 */
	public void removePlayer(String user){
		
		for(int i=0; i<usernames.length; i++){
			
			if(usernames[i].equals(user)){
				
				player[i] = null;
				ready[i] = false;
				usernames[i] = null;
				sortOutNull(i);
				
			}
			
		}
		
	}
	
	/*
	 * Sorts the null values to the end of the arrays
	 */
	private void sortOutNull(int nullpos){
		int i = nullpos +1;
		
		while(i < player.length){
			
			player[i-1] = player[i];
			usernames[i-1] = usernames[i];
			ready[i-1] = ready[i];
			
		}
		
	}
	
}
