package io.graphics;

public class InvalidAnimationException extends Exception{

	private static final long serialVersionUID = 3924656120866995892L;

	public InvalidAnimationException(){
		super();
	}
	
	public InvalidAnimationException(String msg){
		super(msg);
	}
}
