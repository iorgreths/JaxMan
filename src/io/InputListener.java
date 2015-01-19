package io;

import game.runtime.Game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class InputListener implements KeyListener, MouseListener, MouseMotionListener {

	protected Game listeningGame = null;

	public synchronized void setGame(Game g){
		this.listeningGame = g;
	}

	@Override
	public synchronized void mouseClicked(MouseEvent e){
		if(this.listeningGame != null){
			this.listeningGame.mouseClicked(e);
		}
	}

	@Override
	public synchronized void mouseEntered(MouseEvent e){
	}

	@Override
	public synchronized void mouseExited(MouseEvent e){
	}

	@Override
	public synchronized void mousePressed(MouseEvent e){
		if(this.listeningGame != null){
			this.listeningGame.mousePressed(e);

		}
	}

	@Override
	public synchronized void mouseReleased(MouseEvent e){
		if(this.listeningGame != null){
			this.listeningGame.mouseReleased(e);

		}
	}

	@Override
	public synchronized void keyPressed(KeyEvent e){
		if(this.listeningGame != null){
			this.listeningGame.keyPressed(e);

		}
	}

	@Override
	public synchronized void keyReleased(KeyEvent e){
		if(this.listeningGame != null){
			this.listeningGame.keyReleased(e);

		}
	}

	@Override
	public synchronized void keyTyped(KeyEvent e){
		if(this.listeningGame != null){
			this.listeningGame.keyTyped(e);

		}
	}

	@Override
	public synchronized void mouseDragged(MouseEvent e){
		if(this.listeningGame != null){
			this.listeningGame.mouseDragged(e);

		}
	}

	@Override
	public synchronized void mouseMoved(MouseEvent e){
		if(this.listeningGame != null){
			this.listeningGame.mouseMoved(e);

		}
	}

}
