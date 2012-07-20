package companion;

import interfaces.GameWindow;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
A custom key listener that registers the keys that the game needs
Reads the arrow keys for one player, WASD for the other, and ESC / Q for
quitting the game.
@author Chris
*/
public class ArrowKeyListener implements KeyListener
{

	private GameWindow window0, window1;

	/**
	Constructor
	Lets this know where to send key presses.
	@param win0 Player 1
	@param win1 Player 2
	*/
	public ArrowKeyListener (GameWindow win0, GameWindow win1)
	{
		this.window0 = win0;
		this.window1 = win1;
		System.out.println ("ArrowKeyListener Initialized");
	}

	/**
	Signals when each of the keys is pressed
	@param evt The KeyEvent sent from the system
	*/
	@Override
	public void keyPressed (KeyEvent evt)
	{
		try
		{
			int key = evt.getKeyCode ();

			if (key == KeyEvent.VK_LEFT)
			{
				this.window0.startMoveLeft ();
			}
			if (key == KeyEvent.VK_RIGHT)
			{
				this.window0.startMoveRight ();
			}
			if (key == KeyEvent.VK_UP)
			{
				this.window0.startMoveUp ();
			}
			if (key == KeyEvent.VK_DOWN)
			{
				this.window0.startMoveDown ();
			}
			if (key == KeyEvent.VK_CONTROL)
			{
				this.window0.fireWeapon ();
			}

			if (key == KeyEvent.VK_A)
			{
				this.window1.startMoveLeft ();
			}
			if (key == KeyEvent.VK_D)
			{
				this.window1.startMoveRight ();
			}
			if (key == KeyEvent.VK_W)
			{
				this.window1.startMoveUp ();
			}
			if (key == KeyEvent.VK_S)
			{
				this.window1.startMoveDown ();
			}
			if (key == KeyEvent.VK_SPACE)
			{
				this.window1.fireWeapon ();
			}

			if (key == KeyEvent.VK_Q || key == KeyEvent.VK_ESCAPE)
			{
				this.window0.onClose ();
				this.window1.onClose ();
			}
		}
		catch (NullPointerException e) {}
	}

	/**
	Signals when each of the keys has been released.
	@param evt The KeyEvent sent from the system
	*/
	@Override
	public void keyReleased (KeyEvent evt)
	{
		try
		{
			int key = evt.getKeyCode ();

			if (key == KeyEvent.VK_LEFT)
			{
				this.window0.endMoveLeft ();
			}
			if (key == KeyEvent.VK_RIGHT)
			{
				this.window0.endMoveRight ();
			}
			if (key == KeyEvent.VK_UP)
			{
				this.window0.endMoveUp ();
			}
			if (key == KeyEvent.VK_DOWN)
			{
				this.window0.endMoveDown ();
			}

			if (key == KeyEvent.VK_A)
			{
				this.window1.endMoveLeft ();
			}
			if (key == KeyEvent.VK_D)
			{
				this.window1.endMoveRight ();
			}
			if (key == KeyEvent.VK_W)
			{
				this.window1.endMoveUp ();
			}
			if (key == KeyEvent.VK_S)
			{
				this.window1.endMoveDown ();
			}
		}
		catch (NullPointerException e) {}
	}

	/**
	Not Used
	@param evt 
	*/
	@Override
	public void keyTyped (KeyEvent evt) {}
}
