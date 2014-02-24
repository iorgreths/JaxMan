package game.network.client;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

/**
 * 
 * Handles the connection on the client side of the game
 * 
 * @author Iorgreths
 * @version 1.0
 *
 */

public class NetworkManager {

	private Socket tcpsocket;
	private DatagramSocket watcher;
	private DatagramSocket[] player;
	private NetworkManager instance;
	
	/*
	 *  creates a new NetworkManager
	 */
	private NetworkManager(){
		
		player = new DatagramSocket[4];
	
	}
	
	/**
	 * Returns the current instance of the NetworkManager
	 * 
	 * @return NetworkManager
	 */
	public NetworkManager getInstance(){
		
		if(instance == null) instance = new NetworkManager();
		
		return instance;
		
	}
	
	/**
	 * 
	 * Connects the local machine with the Server. <br/>
	 * Hereby are two connections created: <br/>
	 * <ol>
	 * 	<li> a TCP connection (i.e. for pings) </li>
	 *  <li> a UDP connection (for watching the game -> Spectator mode) </li>
	 * </ol>
	 * An IOException is thrown, if the connections was unsuccessful.
	 * 
	 * @param adr - The IPv4-Address of the computer running the Server (Local: 127.0.0.1)
	 * @param port - The port number, at which the server listens for TCP connections
	 * @param udpport - The port number, at which the server listens for UDP connections
	 * @throws IOException - Unable to connect to specified address/port combination
	 */
	public void connect(InetAddress adr, int port, int udpport) throws IOException{
		
		tcpsocket = new Socket(adr, port);
		watcher = new DatagramSocket();
		watcher.connect(adr, udpport);
		
	}
	
	/**
	 * Connects a player to the server. <br/>
	 * Hereby a UDP connections toward the server is established. <br/>
	 * AnIOException is thrown, if the connections was unsuccessful.
	 * 
	 * @param adr - The IPv4-Address of the computer running the Server (Local: 127.0.0.1)
	 * @param port - The port number, at which the server listens for UDP connections
	 * @throws IOException - Unable to connect to specified address/port combination
	 */
	public void connectPlayer(InetAddress adr, int port) throws IOException{
		
		if(player[0] == null) player[0] = watcher;
		
		for(DatagramSocket sock : player){
			
			if(sock == null){
				
				sock = new DatagramSocket();
				sock.connect(adr, port);
				
			}
			
		}
		
	}
	
	/**
	 * Closes all active connections
	 * 
	 * @throws IOException - Connection has already been closed (most probably from the server)
	 */
	public void killConnection() throws IOException{
		
		if( tcpsocket != null ) tcpsocket.close();
		if( watcher != null ) watcher.close();
		if( player[1] != null ) player[1].close();
		if( player[2] != null ) player[2].close();
		if( player[3] != null ) player[3].close();
		
		instance = new NetworkManager();
		
	}
	
	/**
	 * Disconnects the local player, which is represented by pid, from the server. <br/>
	 * This is if the player was connected in the first place.
	 * @param pid - The local representation of the player, which shall be disconnected (i.e. 1 : local player 1)
	 */
	public void disconnectLocalPlayer( int pid ){
		
		if( player[pid-1] != null ){
			
			player[pid-1].close();
			player[pid-1] = null;
			
		}
		
	}
	
	
}
