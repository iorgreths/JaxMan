import game.runtime.Game;
import game.runtime.Observable;
import io.InputListener;
import io.graphics.DeadInstanceException;
import io.graphics.GameFrame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.List;

public class Test {

	public static List<Shape> shapeList;
	public static int offsetX=0, offsetY=0;

	public static void main(String[] args) throws InterruptedException, DeadInstanceException{

		List<Shape> tmp;
		Test.shapeList = new ArrayList<Shape>();
		Game game = new TestGame();
		GameFrame gf = game.getFrame();
		gf.setVisible(true);

		BufferStrategy strat = gf.getBufferStrategy();
		Dimension d = gf.getCanvasDimension();
		Graphics2D g;

		Thread.sleep(500);

		for(int i=255; i >= 0; i--){
			synchronized(Test.class){
				tmp = new ArrayList<>(shapeList);
			}
			g = (Graphics2D)strat.getDrawGraphics();
			g.setColor(new Color(i,i,i));
			g.fillRect(0, 0, d.width, d.height);
			g.setColor(Color.black);
			g.drawString("Good Day Sir", d.width/2 + Test.offsetX, d.height/2 + Test.offsetY);
			for(Shape s: tmp){
				g.draw(s);
				g.fill(s);
			}
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
			synchronized(Test.class){
				tmp = new ArrayList<>(shapeList);
			}
			g = (Graphics2D)strat.getDrawGraphics();
			g.setColor(new Color(i, i, i));
			g.fillRect(0, 0, d.width, d.height);
			g.setColor(Color.black);
			g.drawString("I said GOOD DAY SIR", d.width/2 + Test.offsetX, d.height/2 + Test.offsetY);

			for(Shape s: tmp){
				g.draw(s);
				g.fill(s);
			}
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
	}

	@Override
	public synchronized void mousePressed(MouseEvent e) {
		Test.shapeList.add(new Ellipse2D.Double(e.getX(), e.getY(), 10, 10));

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
		}else if(e.getKeyCode() == KeyEvent.VK_UP){
			Test.offsetY -= 5;
			
		}else if(e.getKeyCode() == KeyEvent.VK_LEFT){
			Test.offsetX -= 5;
			
		}else if(e.getKeyCode() == KeyEvent.VK_RIGHT){
			Test.offsetX += 5;
			
		}else if(e.getKeyCode() == KeyEvent.VK_DOWN){
			Test.offsetY += 5;
			
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
