package game.network.client;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;

/**
 * 
 * A class representing the client side connection of the game.<br/>
 * A client can connect towards a server ( (ip,port)-Tupel ). <br/>
 * A client can close his current connection, as well as looking if the connection is still up. <br/>
 * <br/>
 * The client uses the following default ports: <br/>
 * <ul>
 * <li> 50333 - incoming information from the server UDP
 * </ul>
 * 
 * @author Iorgreths
 * @version 2.0
 *
 */

public class NetworkManager {

	private Socket server;
	//private DatagramSocket outgoinginformation; // the port at which the server expects information
	private DatagramSocket incominginformation; // the port at which this client expects information
	private static NetworkManager instance;
	
	/*
	 *  creates a new NetworkManager
	 */
	private NetworkManager(){
	
		try {
			
			incominginformation = new DatagramSocket(50333);
			
		} catch (SocketException e) {
		
			e.printStackTrace();
			
		}
		
	}
	
	/**
	 * Returns the current instance of the NetworkManager
	 * 
	 * @return NetworkManager
	 */
	public static NetworkManager getInstance(){
		
		if(instance == null) instance = new NetworkManager();
		
		return instance;
		
	}
	
	/**
	 * Connects to a server with the specified (serveraddress,tcpport). <br/>
	 * Be sure that the server has a ServerSocket open at the specified port. <br/>
	 * @param serveraddress - The IPv4/IPv6 address of the server
	 * @param tcpport - The port at which the server waits for incoming connections
	 * @throws IOException - Unable to connect to the specified information (serveraddress,tcpport)
	 */
	public void connect(InetAddress serveraddress, int tcpport) throws IOException{
		
		server = new Socket(serveraddress, tcpport);
		
	}
	
	/**
	 * Binds the port at which incoming information shall arrive. <br/>
	 * UDP packages should be sent towards this port. <br/>
	 * Default port = 50333;
	 * @param portnr
	 * @throws SocketException
	 */
	public void bindIncomingInformationPort(int portnr) throws SocketException{
		
		incominginformation = new DatagramSocket(portnr);
		
	}
	
	/**
	 * Removes the server, if the connection has been closed by the server
	 */
	public void removeServer(){
		
		if(server.isClosed()) server = null;
		
	}
	
}
