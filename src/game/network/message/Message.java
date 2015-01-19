package game.network.message;

/**
 * Messages are sent between server and clients
 * 
 * @author Iorgreths
 * @version 1.0
 */
public interface Message {
	
	/**
	 * Returns the information of the message in form of a byte-array. <br/>
	 * The byte array is filed into udp packages.
	 * @return The information contained in the message in byte format
	 */
	public byte[] getMessage();
	
	/**
	 * Sets a message based on the input. <br/>
	 * How the input is decoded is to be specified by the message creater
	 * @param input - The message in byte format
	 */
	public void setMessage(byte[] input);

}
