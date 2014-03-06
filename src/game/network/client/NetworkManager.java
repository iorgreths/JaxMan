package game.network.client;

import game.network.message.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
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
 * <li> 50333 - incoming information from the server UDP </li>
 * <li> 53001 - Server incoming information</li>
 * </ul>
 * 
 * @author Iorgreths
 * @version 2.1
 *
 */

public class NetworkManager {

	private Socket server;
	private int listenport; // the port at which the server listens for information
	private DatagramSocket incominginformation; // the port at which this client expects information
	private static NetworkManager instance;
	
	private int packetlength;
	
	/*
	 *  creates a new NetworkManager
	 */
	private NetworkManager(){
	
		packetlength = 1024;
		listenport = 53001;
		
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
		
		// now the server tells us at which port he wants the information
		BufferedReader reader = new BufferedReader(new InputStreamReader(server.getInputStream()));
		String serverport = reader.readLine();
		if(! (serverport == null || serverport.equals("") )) listenport = Integer.valueOf(serverport);
		reader.close();
		
	}
	
	/**
	 * Binds the port at which incoming information shall arrive. <br/>
	 * UDP packages should be sent towards this port. <br/>
	 * Default port = 50333;
	 * @param portnr - The port at which port this client waits for information
	 * @throws SocketException - Unable to use this port (already occupied?)
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
	 * Sets the port at which the server expects to hear information about the game. <br/>
	 * UDP packages will be sent towards that port.
	 * @param portnr - The port at which the server listens for information (i.e. 50346)
	 */
	public void setServerListeningPort(int portnr){
		
		listenport = portnr;
		
	}
	
	/**
	 * Sends a MESSAGE to the connected server. <br/>
	 * Messages have to implement the interface game.network.Message.java<br/>
	 * <br/>
	 * The client speaks to the server at a specific port <Listening Port>.
	 * <Listening Port> is predefined as 50333 if this is not usable use <setServerListeningPort(int)> to change the port.
	 * @param msg - The message, which shall be send to every client
	 * @throws IOException - Unable to open a new socket to write information, or unable to connect to <Listening Port> of the server
	 */
	public void sendMessageToClients(Message msg) throws IOException{
		
		DatagramSocket write = new DatagramSocket();
		DatagramPacket information = new DatagramPacket(msg.getMessage(), packetlength);
		
		write.connect(server.getInetAddress(), listenport);
		write.send(information);
		write.close();
		
		write = null;
		information = null;
		
	}
	
	/**
	 * Receives the next message from the server. <br/>
	 * Messages have to implement the interface game.network.Message.java<br/>
	 * This method stalls until a message has been received. 
	 * @param msg - The Message object into which the information from the server shall be written
	 * @throws IOException - Unable to receive information
	 */
	public void receiveMessageFromClient(Message msg) throws IOException{
		
		DatagramPacket information = new DatagramPacket(new byte[packetlength], packetlength);
		incominginformation.receive(information);
		msg.setMessage(information.getData());
		
	}
	
}
