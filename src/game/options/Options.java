package game.options;

public class Options {
	
	private static Options instance;
	
	private Options(){
		
	}

	public static Options getInstance(){
		
		if(instance==null){
		
			instance=new Options();
		
		}
		
		return instance;
		
	}
	
}
