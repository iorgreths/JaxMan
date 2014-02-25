package game.network.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * A class representing the server side connection of the game. <br/>
 * <br/>
 * The server is able to add new clients to his list of clients, furthermore he can remove
 * all clients that closed the connection. In addition there is the possibility to close all 
 * connections from the server side. <br/>
 * <br/>
 * If used by default configuration this Manager uses the following ports:
 * <ul>
 * <li> 53001 - Player incoming information</li>
 * <li> 50333 - Outgoing information towards clients</li>
 * </ul>
 * 
 * @author Iorgreths
 * @version 1.0
 */

public class NetworkManager {

	private static NetworkManager instance;
	private List<Socket> clients;  // The server sends every client the info about the game
	private int listenport; // at which ports the client expect the information
	
	private ServerSocket incomingconnections; // ServerSocket for incoming TCP requests
	private DatagramSocket incominginformation; // at which port the server expects information
	
	/*
	 * Creates a new NetworkManager without any current connected clients
	 */
	private NetworkManager(){
		
		clients = new ArrayList<Socket>();
		listenport = 50333;
		
		try {
			
			incominginformation = new DatagramSocket(53001);
			
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
		
		if( instance == null ) instance = new NetworkManager();
		
		return instance;
		
	}
	
	/**
	 * Removes all client-side closed connections. <br/>
	 * This results in removing every Spectator who no longer listens,
	 * as well as removing disconnected players.
	 */
	public void removeClient(){
		
		for(int i=0; i < clients.size(); i++){
			
			if(clients.get(i).isClosed()){
				
				clients.remove(i);
				
			}
			
		}
		
	}
	
	/**
	 * Closes all open connections. <br/>
	 * Closes the connection towards all spectators and players.
	 * @throws IOException - Unable to close connection.
	 */
	public void closeConnections() throws IOException{
		
		for(Socket client : clients){
			
			if(!client.isClosed())client.close();
			
		}
		clients = new ArrayList<Socket>();
		
	}
	
	/**
	 * Sets the port at which the clients expects to hear information about the game. <br/>
	 * UDP packages will be sent towards that port.
	 * @param portnr - The port at which the clients listens for information (i.e. 50346)
	 */
	public void setClientListeningPort(int portnr){
		
		listenport = portnr;
		
	}
	
	/**
	 * Sets the port at which this server expects to hear information about the game. <br/>
	 * UDP packages should be sent towards this port. <br/>
	 * The default port is 53001
	 * @param portnr1 - The port at which this server listens for information
	 * @throws SocketException - Unable to open a Socket at this port (portnr).
	 */
	public void bindIncomingInformationPort(int portnr1) throws SocketException{
		
		incominginformation = new DatagramSocket(portnr1);
		
		
	}
	
	/**
	 * Opens a new ServerSocket at which the server awaits new client connections. <br/<
	 * TCP connections will be established this way.
	 * @param waitport - The port at which the server expects clients to connect to.
	 * @throws IOException - Unable to open a new ServerSocket with this port number (maybe port already occupied?)
	 */
	public void openServerSocket(int waitport) throws IOException{
		
		incomingconnections = new ServerSocket(waitport);
		
	}
	
	/**
	 * Closes the ServerSocket. 
	 * @throws IOException - Unable to close ServerSocket
	 */
	public void closeServerSocket() throws IOException{
		
		incomingconnections.close();
		incomingconnections = null;
		
	}
	
	/**
	 * Waits for a client to connect to the server. <br/>
	 * Tells every client at which port this server waits for UDP packages
	 * @throws IOException - Unable to add another client (all ports occupied?)
	 */
	public void nextClient() throws IOException{
		
		clients.add(incomingconnections.accept());
		
		PrintWriter serverport= new PrintWriter(clients.get(clients.size()-1).getOutputStream());
		String message = "";
		if(incominginformation.getLocalPort() != 53001) message = String.valueOf(incominginformation.getLocalPort());
		serverport.write(message);
		serverport.close();
		
	}
	
}
