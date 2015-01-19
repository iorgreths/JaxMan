package game.network.server;

import game.network.message.Message;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.DatagramPacket;
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
	
	private int packetlength; // the length of a udp packet (in byte)
	
	/*
	 * Creates a new NetworkManager without any current connected clients
	 */
	private NetworkManager(){
		
		clients = new ArrayList<Socket>();
		listenport = 50333;
		packetlength = 1024;
		
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
	
	/**
	 * Sets a new length for the udp packet. <br/>
	 * NOTE that this length has to be ident on server and client side. <br/>
	 * Only use this method if the predefined 1024 byte are not enogh.
	 * @param packetlength - The new length for udp packets.
	 */
	public void setPacketLength(int packetlength){
		
		this.packetlength = packetlength;
		
	}
	
	/**
	 * Sends a MESSAGE to every connected client. <br/>
	 * Messages have to implement the interface game.network.Message.java<br/>
	 * <br/>
	 * Messages will be send to every client that is registered on this server. 
	 * Furthermore the server speaks to every client at a specific port <Listening Port>.
	 * <Listening Port> is predefined as 50333 if this is not usable use <setClientListeningPort(int)> to change the port.
	 * @param msg - The message, which shall be send to every client
	 * @throws IOException - Unable to open a new Socket to write information, or unable to connect to <Listening Port> of the client
	 */
	public void sendMessageToClients(Message msg) throws IOException{
		
		DatagramSocket write = new DatagramSocket();
		DatagramPacket information = new DatagramPacket(msg.getMessage(), packetlength);
		
		for(Socket client : clients){
			
			write.connect(client.getInetAddress(), listenport);
			write.send(information);
			write.close();
			
		}
		
		write = null;
		information = null;
		
	}
	
	/**
	 * Receives the next message from the clients. <br/>
	 * Messages have to implement the interface game.network.Message.java<br/>
	 * This method stalls until a message has been received. 
	 * @param msg - The Message object into which the information from the clients shall be written
	 * @throws IOException - Unable to receive information
	 */
	public void receiveMessageFromClients(Message msg) throws IOException{
		
		DatagramPacket information = new DatagramPacket(new byte[packetlength], packetlength);
		incominginformation.receive(information);
		msg.setMessage(information.getData());
		
	}
	
}
