package pkg;

import home.Tanks;

import java.awt.Graphics;

/**
Represents a simple field obstacle
@author Chris
*/
public class Boulder extends ViewPort
{

	private static final long serialVersionUID = 1L;

	/**
	Creates a new Boulder
	Randomly placed on the screen
	Randomly sized at 75-175 by 75-175
	*/
	public Boulder ()
	{
		super ("Boulder");

		int width = (int) (75 + (Math.random () * 100));
		int height = (int) (75 + (Math.random () * 100));

		int x = (int) (Math.random () * (Tanks.SWIDTH - width));
		int y = (int) (Math.random () * (Tanks.SHEIGHT - height));

		this.setSize (width, height);
		this.setLocation (x, y);

		this.setVisible (true);
		
		System.out.println ("Boulder Initialized");
	}

	/**
	Updates the locations of all window parts
	Called by the main game loop
	*/
	@Override
	public void update () {}

	/**
	Draws the background
	*/
	@Override
	public void paint (Graphics g)
	{
		g.drawImage (Tanks.buffer, -this.getX (), -this.getY (), null);
	}

	/**
	Disposes this's window
	TODO Change to disposing all created windows?
	*/
	@Override
	public void onEnd ()
	{
		this.dispose ();
	}

	/**
	Not Used
	@param objs 
	*/
	@Override
	public void collide (ViewPort[] objs) {}

	/**
	Not Used
	*/
	@Override
	public void drawToBuffer () {}
}