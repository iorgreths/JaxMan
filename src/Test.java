import game.runtime.Game;
import game.runtime.Observable;
import io.InputListener;
import io.graphics.DeadInstanceException;
import io.graphics.GameFrame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;


public class Test {
	public static void main(String[] args) throws InterruptedException, DeadInstanceException{

		Game game = new TestGame();
		GameFrame gf = game.getFrame();
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

		Thread.sleep(500);
		System.exit(0);
	}
}

class TestGame extends Game{

	public void update(Observable object) {

	}

	public TestGame(){
		super(new InputListener());
	}

	public static Game getInstance(){
		if(Game.instance==null){
			Game.instance=new TestGame();
		}
		return Game.instance;
	}

	public void gameLoop() {
		

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		BufferStrategy b;
		try {
			b = GameFrame.getInstance().getBufferStrategy();
			Graphics2D g = (Graphics2D) b.getDrawGraphics();
			g.setColor(Color.blue);
			g.drawOval(e.getX(), e.getY(), 50, 50);
			g.dispose();
			b.show();
		} catch (DeadInstanceException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
			System.exit(0);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

}
