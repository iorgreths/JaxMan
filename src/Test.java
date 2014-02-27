import game.runtime.Game;
import game.runtime.Observable;
import io.graphics.DeadInstanceException;
import io.graphics.GameFrame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;


public class Test {
	public static void main(String[] args) throws InterruptedException, DeadInstanceException{

		GameFrame gf = GameFrame.getInstance();
		gf.setVisible(true);

		BufferStrategy strat = gf.getBufferStrategy();
		Dimension d = gf.getCanvasDimension();
		Graphics g;

		Thread.sleep(500);

		for(int i=255; i >= 0; i--){
			g = strat.getDrawGraphics();
			g.setColor(new Color(i,i,i));
			g.fillRect(0, 0, d.width, d.height);
			g.setColor(Color.black);
			g.drawString("Good Day Sir", d.width/2, d.height/2);
			g.dispose();
			strat.show();
			Thread.sleep(5);
		}

		Thread.sleep(500);

		//
		gf.setFullscreen(true);
		gf = GameFrame.getInstance();

		strat = gf.getBufferStrategy();
		d = gf.getCanvasDimension();

		for(int i = 0; i < 256; i++){
			g = strat.getDrawGraphics();
			g.setColor(new Color(i, i, i));
			g.fillRect(0, 0, d.width, d.height);
			g.setColor(Color.black);
			g.drawString("I said GOOD DAY SIR", d.width/2, d.height/2);
			g.dispose();
			strat.show();
			Thread.sleep(5);
		}

		//
//		gf.setFullscreen(false);
//		gf = GameFrame.getInstance();
//		gf.setVisible(true);
//		d = gf.getCanvasDimension();
//
//		strat = gf.getBufferStrategy();
//		g = strat.getDrawGraphics();
//
//		for(int i=0; i < 1000; i++){
//			g.setColor(Color.gray);
//			g.fillRect(0, 0, d.width, d.height);
//			g.setColor(Color.black);
//			g.drawString("GOOD BYE", d.width/2, d.height/2);
//			g.dispose();
//			strat.show();
//			Thread.sleep(1);
//		}

		Thread.sleep(500);
		System.exit(0);
	}
}

class TestGame extends Game{

	public void update(Observable object) {

	}

	public TestGame(){
		super();
	}

	public static Game getInstance(){
		if(Game.instance==null){
			Game.instance=new TestGame();
		}
		return Game.instance;
	}

	public void gameLoop() {


	}

}
