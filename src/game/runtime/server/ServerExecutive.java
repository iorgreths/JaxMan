package game.runtime.server;

import game.network.message.Message;

/**
 * This class represents the logic behind the server. <br/>
 * The server reads messages and sends this new messages towards an ServerExecutive,
 * who is supposed to manipulate the message. <br/>
 * Afterwards the server takes the result, which has been provided by the executive.
 * 
 * @author Iorgreths
 * @version 1.0
 *
 */
public interface ServerExecutive {
	
	/**
	 * Executes a set of commands.
	 * @param msg - The <Message> which manipulates the executing commands
	 */
	public void execute(Message msg);
	
	/**
	 * Returns a new message which is the result of the commands
	 * @return - <Message>
	 */
	public Message result();

}
