package io.graphics;

public class DeadInstanceException extends Exception {

	private static final long serialVersionUID = 8391116274073182121L;
	
	
	public DeadInstanceException(){
		super();
	}
	
	public DeadInstanceException(String msg){
		super(msg);
	}
}
